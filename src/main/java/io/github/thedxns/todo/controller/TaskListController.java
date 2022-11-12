package io.github.thedxns.todo.controller;

import java.util.ArrayList;
import java.util.List;
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
import io.github.thedxns.todo.logic.TaskListService;
import io.github.thedxns.todo.model.TaskList;

@RestController
@RequestMapping("/api/lists")
public class TaskListController {

    private TaskListService taskListService;
    private UserController userController;

    @Autowired
    public TaskListController(TaskListService taskListService, UserController userController) {
        this.taskListService = taskListService;
        this.userController = userController;
    }
    
    @GetMapping()
    public ResponseEntity<List<TaskList>> getTaskLists() {
        return ResponseEntity.ok(taskListService.getAllTaskLists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        if(!taskListService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(taskListService.getTaskList(id));
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<TaskList>> getTaskListsByUser(@PathVariable String username) {
        return ResponseEntity.ok(taskListService.getAllByUser(username));
    }

    @PostMapping
    public ResponseEntity<?> saveTaskList(@Valid @RequestBody TaskList taskList) throws Exception {
        if (taskListService.saveTaskList(taskList)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskList(@PathVariable Long id) {
        if(!taskListService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            if(taskListService.deleteTaskList(id)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskList(@PathVariable Long id, @RequestBody @Valid TaskList taskList) {
        if(!taskListService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            TaskList originalTaskList = taskListService.getTaskList(id);
            taskList.setUsers(originalTaskList.getUsers());
            if(taskListService.updateTaskList(id, taskList)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.internalServerError().build(); 
            }
        }
    }

    @PatchMapping("/access/{id}/{username}")
    public ResponseEntity<?> grantAccessToUser(@PathVariable Long id, @PathVariable String username) {
        String userId = userController.getUserByUsername(username);
        TaskList taskList = taskListService.getTaskList(id);
        List<String> users = taskList.getUsers();
        users.add(userId);
        if(taskListService.updateTaskList(id, taskList)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.internalServerError().build(); 
        }
    }

    @PatchMapping("/access/remove/{id}/{username}")
    public ResponseEntity<?> removeAccessOfUser(@PathVariable Long id, @PathVariable String username) {
        String userId = userController.getUserByUsername(username);
        TaskList taskList = taskListService.getTaskList(id);
        List<String> users = taskList.getUsers();
        users.remove(userId);
        if(taskListService.updateTaskList(id, taskList)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.internalServerError().build(); 
        }
    }

    @GetMapping("/access/{id}")
    public ResponseEntity<List<String>> getPermittedUsers(@PathVariable Long id) {
        TaskList taskList = taskListService.getTaskList(id);
        List<String> users = taskList.getUsers();
        List<String> usernames = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            usernames.add(userController.getUsername(users.get(i)));
        }
        return ResponseEntity.ok(usernames);
    }
}
