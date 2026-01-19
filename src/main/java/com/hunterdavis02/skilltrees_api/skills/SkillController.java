package com.hunterdavis02.skilltrees_api.skills;


import com.hunterdavis02.skilltrees_api.skills.dto.CreateSkillRequest;
import com.hunterdavis02.skilltrees_api.skills.dto.SkillResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SkillResponse create(@Valid @RequestBody CreateSkillRequest request) {
        return skillService.create(request);
    }

    @GetMapping
    public List<SkillResponse> list() {
        return skillService.list();
    }

    @GetMapping("/{id}")
    public SkillResponse getById(@PathVariable Long id) {
        return skillService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public SkillResponse update(@Valid @RequestBody CreateSkillRequest request, Long id) {
        return skillService.update(request, id);
    }
}
