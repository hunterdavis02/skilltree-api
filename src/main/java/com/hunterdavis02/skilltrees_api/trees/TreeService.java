package com.hunterdavis02.skilltrees_api.trees;

import com.hunterdavis02.skilltrees_api.skills.Skill;
import com.hunterdavis02.skilltrees_api.skills.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreeService {
    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private SkillRepository skillRepository;

    public List<Tree> getAllTrees() {
        return treeRepository.findAll();
    }

    public Optional<Tree> getTreeById(Long id) {
        return treeRepository.findById(id);
    }

    public Optional<Tree> getTreeByName(String name) {
        return treeRepository.findByNameIgnoreCase(name);
    }

    public Tree createTree(Tree tree, Skill rootSkill) {
        // Verify root skill exists and has no parent
        if (rootSkill.getParentSkill() != null) {
            throw new RuntimeException("Root skill cannot have a parent skill!");
        }

        // Check if tree name already exists
        if (treeRepository.existsByNameIgnoreCase(tree.getName())) {
            throw new RuntimeException("Tree with name '" + tree.getName() + "' already exists!");
        }

        // Save the root skill first to the database
        Skill savedRootSkill = skillRepository.save(rootSkill);
        
        tree.setRootSkill(savedRootSkill);
        return treeRepository.save(tree);
    }

    public Tree updateTree(Long id, Tree updatedTree) {
        Optional<Tree> existingTree = treeRepository.findById(id);
        if (existingTree.isPresent()) {
            Tree tree = existingTree.get();
            tree.setName(updatedTree.getName());
            tree.setDescription(updatedTree.getDescription());
            tree.setIconUrl(updatedTree.getIconUrl());
            return treeRepository.save(tree);
        }
        throw new RuntimeException("Tree not found!");
    }

    public void deleteTree(Long id) {
        if (treeRepository.existsById(id)) {
            treeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Tree not found!");
        }
    }

    public Tree getTreeWithHierarchy(Long id) {
        Optional<Tree> tree = treeRepository.findById(id);
        return tree.orElse(null);
    }
}
