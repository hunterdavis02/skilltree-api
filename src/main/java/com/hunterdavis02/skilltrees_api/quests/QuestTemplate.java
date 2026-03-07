package com.hunterdavis02.skilltrees_api.quests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hunterdavis02.skilltrees_api.skills.Skill;
import jakarta.persistence.*;

@Entity
@Table(name = "quest_template")
public class QuestTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private int xpValue;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    @JsonIgnore
    private Skill skill;

    protected QuestTemplate() {
    }

    public QuestTemplate(String name, String description, int xpValue, Skill skill) {
        this.name = name;
        this.description = description;
        this.xpValue = xpValue;
        this.skill = skill;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getXpValue() { return xpValue; }
    public void setXpValue(int xpValue) { this.xpValue = xpValue; }

    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }
}
