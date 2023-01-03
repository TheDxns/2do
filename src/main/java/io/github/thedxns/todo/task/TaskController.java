package io.github.thedxns.todo.task;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

import io.github.thedxns.todo.tasklist.TaskListService;

@RestController
@RequestMapping("/api/tasks")
class TaskController {

    private final TaskService taskService;
    private final TaskListService taskListService;

    @Autowired
    public TaskController(final TaskService taskService, final TaskListService taskListService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
    }
    
    @GetMapping(params = {"!sort", "!page", "!size"})
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping
    public ResponseEntity<?> getTasks(Pageable page) {
        return ResponseEntity.ok(taskService.getAllTasks(page));
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<Task>> getTasksFromList(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTasksByList(id));
    }

    @GetMapping("/user/{creatorId}")
    public ResponseEntity<List<Task>> getTasksFromUser(@PathVariable String creatorId) {
        return ResponseEntity.ok(taskService.getAllByCreator(creatorId));
    }

    @GetMapping("/unfinished/user/{creatorId}")
    public ResponseEntity<List<Task>> getUnfinishedTasksFromUser(@PathVariable String creatorId) {
        return ResponseEntity.ok(taskService.getUnfinishedByCreator(creatorId));
    }
    
    @GetMapping("/done/user/{creatorId}")
    public ResponseEntity<List<Task>> getDoneTasksFromUser(@PathVariable String creatorId) {
        return ResponseEntity.ok(taskService.getDoneByCreator(creatorId));
    }

    @GetMapping("/important/user/{creatorId}")
    public ResponseEntity<List<Task>> getImportantTasksFromUser(@PathVariable String creatorId) {
        return ResponseEntity.ok(taskService.getImportantByCreator(creatorId));
    }

    @GetMapping("/unfinished/custom/{listId}")
    public ResponseEntity<List<Task>> getCustomTasks(@PathVariable Long listId) {
        return ResponseEntity.ok(taskService.getCustom(listId));
    }

    @GetMapping("search/{keyword}")
    public ResponseEntity<?> getTaskById(@PathVariable String keyword) {
        return ResponseEntity.ok(taskService.getTasksByKeyword(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        if(!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(taskService.getTask(id));
        }
    }

    @PostMapping
    public ResponseEntity<?> saveTask(@Valid @RequestBody Task task) {
        if (taskService.saveTask(task)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> saveCustomListTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        task.setTaskList(taskListService.getTaskList(id));
        if (taskService.saveTask(task)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        if(!taskService.existsById(id)) {
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
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody @Valid Task task) {
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
        if(!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            final Task task = taskService.getTask(id);
            task.setTaskList(null);
            task.setDone(true);
            if (taskService.updateTask(id, task)) {
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
            final Task task = taskService.getTask(id);
            task.setPrioritized(!task.isPrioritized());
            if (taskService.updateTask(id, task)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.internalServerError().build(); 
            }
        }
    }
}
