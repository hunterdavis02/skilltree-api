package com.hunterdavis02.skilltrees_api.quests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin
public class QuestController {
    @Autowired
    private QuestService questService;

    // GET /api/skills/{id}/quests
    @GetMapping("/{id}/quests")
    public List<Quest> getQuests(@PathVariable Long id) {
        return questService.getQuestsForSkill(id);
    }

    // POST /api/skills/{id}/quests
    @PostMapping("/{id}/quests")
    public Quest createQuest(@PathVariable Long id, @RequestBody Quest quest) {
        return questService.createQuest(id, quest);
    }

    // POST /api/skills/{id}/quests/{questId}/complete
    @PostMapping("/{id}/quests/{questId}/complete")
    public ResponseEntity<Quest> completeQuest(@PathVariable Long id, @PathVariable Long questId) {
        Quest newQuest = questService.completeQuest(id, questId);
        if (newQuest == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newQuest);
    }

    // POST /api/skills/{id}/quests/{questId}/replace
    @PostMapping("/{id}/quests/{questId}/replace")
    public ResponseEntity<Quest> replaceQuest(@PathVariable Long id, @PathVariable Long questId) {
        Quest newQuest = questService.replaceQuest(id, questId);
        if (newQuest == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newQuest);
    }

    // POST /api/skills/{id}/quests/reroll
    @PostMapping("/{id}/quests/reroll")
    public ResponseEntity<Quest> reroll(@PathVariable Long id) {
        Quest newQuest = questService.reroll(id);
        if (newQuest == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newQuest);
    }

    // GET /api/skills/{id}/quest-templates
    @GetMapping("/{id}/quest-templates")
    public List<QuestTemplate> getTemplates(@PathVariable Long id) {
        return questService.getTemplatesForSkill(id);
    }

    // POST /api/skills/{id}/quest-templates
    @PostMapping("/{id}/quest-templates")
    public QuestTemplate addTemplate(@PathVariable Long id, @RequestBody QuestTemplate template) {
        return questService.addTemplate(id, template);
    }

    // DELETE /api/skills/{id}/quest-templates/{templateId}
    @DeleteMapping("/{id}/quest-templates/{templateId}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id, @PathVariable Long templateId) {
        questService.deleteTemplate(id, templateId);
        return ResponseEntity.noContent().build();
    }
}
