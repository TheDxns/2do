package io.github.thedxns.todo.tasklist;

import javax.validation.Valid;

import io.github.thedxns.todo.user.KeycloakId;
import io.github.thedxns.todo.user.UserService;

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
@RequestMapping("/api/lists")
class TaskListController {

    private final TaskListService taskListService;
    private final UserService userService;

    TaskListController(final TaskListService taskListService, final UserService userService) {
        this.taskListService = taskListService;
        this.userService = userService;
    }
    
    @GetMapping()
    public ResponseEntity<?> getTaskLists() {
        return ResponseEntity.ok(taskListService.getAllTaskLists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskListById(@PathVariable Long id) {
        if (!taskListService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(taskListService.getTaskList(id));
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getTaskListsByUser(@PathVariable String username) {
        return ResponseEntity.ok(taskListService.getAllByUser(username));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getTaskListsByOwnerId(@PathVariable String ownerId) {
        return ResponseEntity.ok(taskListService.getAllByOwnerId(ownerId));
    }

    @PostMapping
    public ResponseEntity<?> saveTaskList(@Valid @RequestBody TaskListRequest taskList) {
        if (taskListService.saveTaskList(taskList)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskList(@PathVariable Long id) {
        if (!taskListService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            if (taskListService.deleteTaskList(id)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskList(@PathVariable Long id, @RequestBody @Valid TaskListRequest taskList) {
        if (!taskListService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            if (taskListService.updateTaskList(id, taskList)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.internalServerError().build(); 
            }
        }
    }

    @PatchMapping("/access/{taskListId}/{username}")
    public ResponseEntity<?> grantAccessToUser(@PathVariable Long taskListId, @PathVariable String username) {
        final KeycloakId userId = userService.getUserId(username);
        if (!taskListService.existsById(taskListId)) {
            return ResponseEntity.notFound().build();
        } else {
            if (taskListService.grantAccessToUser(taskListId, userId)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @PatchMapping("/access/remove/{taskListId}/{username}")
    public ResponseEntity<?> removeAccessOfUser(@PathVariable Long taskListId, @PathVariable String username) {
        final KeycloakId userId = userService.getUserId(username);
        if (!taskListService.existsById(taskListId)) {
            return ResponseEntity.notFound().build();
        } else {
            if (taskListService.removeAccessOfUser(taskListId, userId)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @GetMapping("/access/{id}")
    public ResponseEntity<?> getPermittedUsers(@PathVariable Long id) {
        if (taskListService.existsById(id)) {
            return ResponseEntity.ok(taskListService.getPermittedUsers(id));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
