package com.hunterdavis02.skilltrees_api.trees;

import com.hunterdavis02.skilltrees_api.skills.Skill;
import com.hunterdavis02.skilltrees_api.skills.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
@CrossOrigin
public class TreeController {
    @Autowired
    private TreeService treeService;

    @Autowired
    private SkillService skillService;

    // GET /api/trees - Get all Trees
    @GetMapping
    public List<Tree> getAllTrees() {
        return treeService.getAllTrees();
    }

    // GET /api/trees/{id} - Get Tree with ID and its skill hierarchy
    @GetMapping("/{id}")
    public ResponseEntity<Tree> getTree(@PathVariable Long id) {
        return treeService.getTreeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/trees - Create a new Tree with root Skill
    @PostMapping
    public Tree createTree(@RequestBody TreeCreateRequest request) {
        Skill rootSkill = new Skill(request.getRootSkillName(), request.getRootSkillDescription());
        rootSkill.setIconUrl(request.getRootSkillIconUrl());

        Tree tree = new Tree(
                request.getTreeName(),
                request.getTreeDescription(),
                request.getTreeIconUrl(),
                rootSkill
        );

        return treeService.createTree(tree, rootSkill);
    }

    // PUT /api/trees/{id} - Update Tree metadata
    @PutMapping("/{id}")
    public ResponseEntity<Tree> updateTree(@PathVariable Long id, @RequestBody Tree updatedTree) {
        try {
            Tree tree = treeService.updateTree(id, updatedTree);
            return ResponseEntity.ok(tree);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/trees/{id} - Delete a Tree
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTree(@PathVariable Long id) {
        try {
            treeService.deleteTree(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/trees/{treeId}/skills/{parentSkillId}/children - Add child Skill to Tree
    @PostMapping("/{treeId}/skills/{parentSkillId}/children")
    public ResponseEntity<Skill> addChildSkill(
            @PathVariable Long treeId,
            @PathVariable Long parentSkillId,
            @RequestBody Skill childSkill) {
        try {
            // Verify tree exists
            Tree tree = treeService.getTreeById(treeId)
                    .orElseThrow(() -> new RuntimeException("Tree not found!"));

            // Add child skill (this validates parent exists)
            Skill createdSkill = skillService.addChildSkill(parentSkillId, childSkill);
            return ResponseEntity.ok(createdSkill);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/trees/{treeId}/skills/{skillId} - Get specific skill within tree
    @GetMapping("/{treeId}/skills/{skillId}")
    public ResponseEntity<Skill> getSkillInTree(@PathVariable Long treeId, @PathVariable Long skillId) {
        try {
            // Verify tree exists
            treeService.getTreeById(treeId)
                    .orElseThrow(() -> new RuntimeException("Tree not found!"));

            // Get skill
            return skillService.getSkillById(skillId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/trees/{treeId}/skills/{skillId}/children - Get children of skill in tree
    @GetMapping("/{treeId}/skills/{skillId}/children")
    public ResponseEntity<List<Skill>> getSkillChildrenInTree(@PathVariable Long treeId, @PathVariable Long skillId) {
        try {
            // Verify tree exists
            treeService.getTreeById(treeId)
                    .orElseThrow(() -> new RuntimeException("Tree not found!"));

            // Get children
            List<Skill> children = skillService.getChildSkills(skillId);
            return ResponseEntity.ok(children);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
