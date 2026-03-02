package com.hunterdavis02.skilltrees_api.trees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
    Optional<Tree> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
