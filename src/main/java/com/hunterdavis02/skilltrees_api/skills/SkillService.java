package com.hunterdavis02.skilltrees_api.skills;

import com.hunterdavis02.skilltrees_api.skills.dto.CreateSkillRequest;
import com.hunterdavis02.skilltrees_api.skills.dto.SkillResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public SkillResponse create(CreateSkillRequest req) {
        String name = req.getName().trim();

        if(skillRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Skill name already exists: " + name);
        }

        Skill saved = skillRepository.save(new Skill(name, req.getDescription()));
        return new SkillResponse(saved.getId(), saved.getName(), saved.getDescription(), saved.getCreatedAt());
    }

    public List<SkillResponse> list() {
        return skillRepository.findAll().stream()
                .map(s -> new SkillResponse(s.getId(), s.getName(), s.getDescription(), s.getCreatedAt()))
                .toList();
    }

    public SkillResponse getById(Long id) {
        Skill s = skillRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Skill not found: " + id
                ));

        return new SkillResponse(s.getId(), s.getName(), s.getDescription(), s.getCreatedAt());
    }

    public void delete(Long id) {
        if(!skillRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill Not Found: " + id);
        skillRepository.deleteById(id);
    }

    public SkillResponse update(@Valid CreateSkillRequest request, Long id) {
        // Get the skill's ref from id
        Skill temp = skillRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Skill Not Found: " + id));

        // Set name/desc
        temp.setName(request.getName());
        temp.setDescription(request.getDescription());

        // Overwrite skill ref
        Skill saved = skillRepository.save(temp);

        return new SkillResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getCreatedAt()
        );
    }
}
