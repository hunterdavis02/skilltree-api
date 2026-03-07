package com.hunterdavis02.skilltrees_api.skills;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByNameIgnoreCase(String name);

    List<Skill> findByParentSkillIsNull();

    List<Skill> findByParentSkillId(Long parentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Skill s WHERE s.id = :id")
    Optional<Skill> findByIdWithLock(@Param("id") Long id);
}