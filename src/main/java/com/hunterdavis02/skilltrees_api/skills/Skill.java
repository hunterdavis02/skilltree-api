package com.hunterdavis02.skilltrees_api.skills;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(length = 2000)
    private String description;
    private String iconUrl;

    @ManyToOne  // Many skills can have ONE parent
    @JoinColumn(name = "parent_skill_id")
    @JsonBackReference
    private Skill parentSkill;

    @OneToMany(mappedBy = "parentSkill")  // One skill has MANY children
    @JsonManagedReference
    private List<Skill> childSkills = new ArrayList<>();

    protected Skill() {

    }

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {this.description = description; }

    public Skill getParentSkill() { return parentSkill; }
    public void setParentSkill(Skill skill) { this.parentSkill = parentSkill; }

    public List<Skill> getChildSkills() { return childSkills; }
    public void setChildSkills(List<Skill> childSkills) { this.childSkills = childSkills; }
}
