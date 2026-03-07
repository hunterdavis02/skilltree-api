package com.hunterdavis02.skilltrees_api.quests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findBySkillId(Long skillId);
}
