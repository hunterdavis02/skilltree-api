package com.hunterdavis02.skilltrees_api.quests;

import com.hunterdavis02.skilltrees_api.skills.Skill;
import com.hunterdavis02.skilltrees_api.skills.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestService {
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private QuestTemplateRepository questTemplateRepository;
    @Autowired
    private SkillRepository skillRepository;

    private final Random random = new Random();

    // --- Quest Templates ---

    public List<QuestTemplate> getTemplatesForSkill(Long skillId) {
        requireSkill(skillId);
        return questTemplateRepository.findBySkillId(skillId);
    }

    public QuestTemplate addTemplate(Long skillId, QuestTemplate template) {
        Skill skill = requireSkill(skillId);
        template.setSkill(skill);
        return questTemplateRepository.save(template);
    }

    public void deleteTemplate(Long skillId, Long templateId) {
        QuestTemplate template = questTemplateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("Quest template not found"));
        if (!template.getSkill().getId().equals(skillId)) {
            throw new IllegalArgumentException("Template does not belong to skill " + skillId);
        }
        questTemplateRepository.delete(template);
    }

    // --- Quests ---

    public List<Quest> getQuestsForSkill(Long skillId) {
        requireSkill(skillId);
        return questRepository.findBySkillId(skillId);
    }

    public Quest createQuest(Long skillId, Quest quest) {
        Skill skill = requireSkill(skillId);
        if (questRepository.findBySkillId(skillId).size() >= 3) {
            throw new IllegalArgumentException("Skill already has 3 active quests. Complete one first.");
        }
        quest.setSkill(skill);
        return questRepository.save(quest);
    }

    @Transactional
    public Quest completeQuest(Long skillId, Long questId) {
        Skill skill = requireSkillWithLock(skillId);

        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new IllegalArgumentException("Quest not found"));
        if (!quest.getSkill().getId().equals(skillId)) {
            throw new IllegalArgumentException("Quest does not belong to skill " + skillId);
        }

        skill.addXp(quest.getXpValue());
        skillRepository.save(skill);

        questRepository.delete(quest);
        questRepository.flush();

        return reroll(skill);
    }

    @Transactional
    public Quest replaceQuest(Long skillId, Long questId) {
        Skill skill = requireSkillWithLock(skillId);

        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new IllegalArgumentException("Quest not found"));
        if (!quest.getSkill().getId().equals(skillId)) {
            throw new IllegalArgumentException("Quest does not belong to skill " + skillId);
        }
        questRepository.delete(quest);
        questRepository.flush();
        return reroll(skill);
    }

    @Transactional
    public Quest reroll(Long skillId) {
        Skill skill = requireSkillWithLock(skillId);
        return reroll(skill);
    }

    private Quest reroll(Skill skill) {
        List<Quest> active = questRepository.findBySkillId(skill.getId());
        if (active.size() >= 3) {
            return null;
        }

        List<QuestTemplate> templates = questTemplateRepository.findBySkillId(skill.getId());
        if (templates.isEmpty()) {
            return null;
        }

        Set<String> activeNames = active.stream()
                .map(Quest::getName)
                .collect(Collectors.toSet());

        List<QuestTemplate> available = templates.stream()
                .filter(t -> !activeNames.contains(t.getName()))
                .collect(Collectors.toList());

        if (available.isEmpty()) {
            available = templates;
        }

        QuestTemplate chosen = available.get(random.nextInt(available.size()));
        Quest newQuest = new Quest(chosen.getName(), chosen.getDescription(), chosen.getXpValue(), skill);
        return questRepository.save(newQuest);
    }

    private Skill requireSkill(Long skillId) {
        return skillRepository.findById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found: " + skillId));
    }

    private Skill requireSkillWithLock(Long skillId) {
        return skillRepository.findByIdWithLock(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found: " + skillId));
    }
}
