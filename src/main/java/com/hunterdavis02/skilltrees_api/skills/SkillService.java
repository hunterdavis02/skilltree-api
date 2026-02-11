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

    public Skill addChildSkill(Long parentId, Skill childSkill) {
        Optional<Skill> parent = skillRepository.findById(parentId);
        if (parent.isPresent()) {
            childSkill.setParentSkill(parent.get());
            return skillRepository.save(childSkill);
        }
        throw new RuntimeException("Parent skill not found!");
    }

    // Return Skill Tree branching from a skill
    public Skill getSkillTree(Long skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        return skill.orElse(null);
    }
}
