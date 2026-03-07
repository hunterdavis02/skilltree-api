package com.hunterdavis02.skilltrees_api.skills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> getRootSkills() {
        return skillRepository.findByParentSkillIsNull();
    }

    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }

    public List<Skill> getChildSkills(Long parentId) {
        return skillRepository.findByParentSkillId(parentId);
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found"));
        skillRepository.delete(skill);
    }

    public Skill updateSkill(Long id, Skill updates) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found"));
        if (updates.getName() != null) skill.setName(updates.getName());
        if (updates.getDescription() != null) skill.setDescription(updates.getDescription());
        if (updates.getIconUrl() != null) skill.setIconUrl(updates.getIconUrl());
        return skillRepository.save(skill);
    }

    public Skill addChildSkill(Long parentId, Skill childSkill) {
        Skill parent = skillRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent skill not found"));
        int unlockedSlots = parent.getUnlockedChildSlots();
        if (parent.getChildSkills().size() >= unlockedSlots) {
            throw new IllegalArgumentException(
                "No child slots available. Skill is level " + parent.getCurrentLevel() +
                " with " + unlockedSlots + " slot(s) unlocked. Reach levels 3, 6, or 10 to unlock more.");
        }
        childSkill.setParentSkill(parent);
        return skillRepository.save(childSkill);
    }

    // Return Skill Tree branching from a skill
    public Skill getSkillTree(Long skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        return skill.orElse(null);
    }
}
