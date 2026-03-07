package com.hunterdavis02.skilltrees_api.skills;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hunterdavis02.skilltrees_api.quests.Quest;
import com.hunterdavis02.skilltrees_api.quests.QuestTemplate;
import com.hunterdavis02.skilltrees_api.trees.Tree;
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

    @Column(nullable = false)
    private int currentXP = 0;

    @ManyToOne
    @JoinColumn(name = "parent_skill_id")
    @JsonBackReference
    private Skill parentSkill;

    @OneToMany(mappedBy = "parentSkill", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Skill> childSkills = new ArrayList<>();

    @OneToOne(mappedBy = "rootSkill")
    @JsonBackReference("tree-rootSkill")
    private Tree tree;

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quest> quests = new ArrayList<>();

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestTemplate> questTemplates = new ArrayList<>();

    protected Skill() {
    }

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // --- XP & Level ---

    public int getCurrentXP() { return currentXP; }
    public void setCurrentXP(int currentXP) { this.currentXP = currentXP; }

    public void addXp(int xp) { this.currentXP += xp; }

    /** Total XP required to reach level n (cumulative): 50 * (2^n - 1). */
    private static long xpForLevel(int n) {
        return 50L * ((1L << n) - 1);
    }

    /** Current level derived from accumulated XP, using exact integer arithmetic. */
    public int getCurrentLevel() {
        int level = 0;
        while (xpForLevel(level + 1) <= currentXP) {
            level++;
        }
        return level;
    }

    /** XP required to advance from the current level to the next: 50 * 2^currentLevel. */
    public int getXpToNextLevel() {
        return (int) (50L << getCurrentLevel());
    }

    /** XP earned so far within the current level (progress bar numerator). */
    public int getCurrentLevelXP() {
        return (int) (currentXP - xpForLevel(getCurrentLevel()));
    }

    /**
     * Number of child skill slots unlocked based on level milestones (3, 6, 10).
     */
    public int getUnlockedChildSlots() {
        int level = getCurrentLevel();
        int slots = 0;
        if (level >= 3) slots++;
        if (level >= 6) slots++;
        if (level >= 10) slots++;
        return slots;
    }

    // --- Standard getters/setters ---

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public Skill getParentSkill() { return parentSkill; }
    public void setParentSkill(Skill skill) { this.parentSkill = skill; }

    public List<Skill> getChildSkills() { return childSkills; }
    public void setChildSkills(List<Skill> childSkills) { this.childSkills = childSkills; }

    public Tree getTree() { return tree; }
    public void setTree(Tree tree) { this.tree = tree; }

    public List<Quest> getQuests() { return quests; }
    public void setQuests(List<Quest> quests) { this.quests = quests; }

    public List<QuestTemplate> getQuestTemplates() { return questTemplates; }
    public void setQuestTemplates(List<QuestTemplate> questTemplates) { this.questTemplates = questTemplates; }
}
