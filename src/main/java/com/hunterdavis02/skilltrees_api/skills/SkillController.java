package com.hunterdavis02.skilltrees_api.skills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin
public class SkillController {
    @Autowired
    private SkillService skillService;

    // GET /api/skills/roots - Get all root Skills
    @GetMapping("/roots")
    public List<Skill> getRootSkills() {
        return skillService.getRootSkills();
    }

    // GET /api/skills/1 - Get Skill with ID 1
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable Long id) {
        return skillService.getSkillById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/skills/children - Get children of Skill with ID 1
    @GetMapping("/{id}/children")
    public List<Skill> getChildren(@PathVariable Long id) {
        return skillService.getChildSkills(id);
    }

    // POST /api/skills - Create a new Skill
    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillService.createSkill(skill);
    }

    // POST /api/skills/1/children - Add child to Skill 1
    @PostMapping("/{parentId}/children")
    public Skill addChild(@PathVariable Long parentId, @RequestBody Skill skill) {
        return skillService.addChildSkill(parentId, skill);
    }

    // GET /api/skills/1/tree - Get full tree from Skill 1
    @GetMapping("/{id}/tree")
    public Skill getSkillTree(@PathVariable Long id) {
        return skillService.getSkillTree(id);
    }
}
