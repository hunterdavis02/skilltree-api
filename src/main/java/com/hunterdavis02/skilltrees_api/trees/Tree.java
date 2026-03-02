package com.hunterdavis02.skilltrees_api.trees;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hunterdavis02.skilltrees_api.skills.Skill;
import jakarta.persistence.*;

@Entity
@Table(name = "skill_tree")
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 2000)
    private String description;

    private String iconUrl;

    @OneToOne
    @JoinColumn(name = "root_skill_id", nullable = false, unique = true)
    @JsonManagedReference("tree-rootSkill")
    private Skill rootSkill;

    protected Tree() {
    }

    public Tree(String name, String description, String iconUrl, Skill rootSkill) {
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.rootSkill = rootSkill;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Skill getRootSkill() {
        return rootSkill;
    }

    public void setRootSkill(Skill rootSkill) {
        this.rootSkill = rootSkill;
    }
}
