package io.github.thedxns.todo.task;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping(params = {"!sort", "!page", "!size"})
    public ResponseEntity<?> getTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> getTasksFromList(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTasksByList(id));
    }

    @GetMapping("/user/{creatorId}")
    public ResponseEntity<?> getTasksFromUser(@PathVariable String creatorId) {
        return ResponseEntity.ok(taskService.getAllByCreator(creatorId));
    }

    @GetMapping("/unfinished/user/{creatorId}")
    public ResponseEntity<?> getUnfinishedTasksFromUser(@PathVariable String creatorId) {
        return ResponseEntity.ok(taskService.getUnfinishedByCreator(creatorId));
    }
    
    @GetMapping("/done/user/{creatorId}")
    public ResponseEntity<?> getDoneTasksFromUser(@PathVariable String creatorId) {
        return ResponseEntity.ok(taskService.getDoneByCreator(creatorId));
    }

    @GetMapping("/important/user/{creatorId}")
    public ResponseEntity<?> getImportantTasksFromUser(@PathVariable String creatorId) {
        return ResponseEntity.ok(taskService.getImportantByCreator(creatorId));
    }

    @GetMapping("/unfinished/custom/{listId}")
    public ResponseEntity<?> getCustomTasks(@PathVariable Long listId) {
        return ResponseEntity.ok(taskService.getCustom(listId));
    }

    @GetMapping("search/{keyword}")
    public ResponseEntity<?> getTasksByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok(taskService.getTasksByKeyword(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(taskService.getTask(id));
        }
    }

    @PostMapping
    public ResponseEntity<?> saveTask(@Valid @RequestBody TaskRequest task) {
        if (taskService.saveTask(task)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> saveCustomListTask(@PathVariable Long id, @Valid @RequestBody TaskRequest task) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (taskService.saveCustomListTask(id, task)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            if (taskService.deleteTask(id)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequest task) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            if (taskService.updateTask(id, task)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.internalServerError().build(); 
            }
        }
    }

    @PatchMapping("/finish/{id}")
    public ResponseEntity<?> finishTask(@PathVariable Long id) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            if (taskService.finishTask(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.internalServerError().build(); 
            }
        }
    }

    @PatchMapping("/prioritize/{id}")
    public ResponseEntity<?> prioritizeTask(@PathVariable Long id) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            if (taskService.switchPriority(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.internalServerError().build(); 
            }
        }
    }
}
