package com.hunterdavis02.skilltrees_api.skills.dto;

import java.time.Instant;

public class SkillResponse {
    private Long id;
    private String name;
    private String description;
    private Instant createdAt;

    public SkillResponse(Long id, String name, String description, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Instant getCreatedAt() { return createdAt; }
}
