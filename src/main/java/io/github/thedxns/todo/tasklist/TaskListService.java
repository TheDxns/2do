package io.github.thedxns.todo.tasklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public
class TaskListService {
    private final TaskListRepository taskListRepository;

    @Autowired
    TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }
    
    public TaskList getTaskList(Long id) {
        return taskListRepository.findById(id).get();
    }

    List<TaskList> getAllByUser(String username) {
        return taskListRepository.findByUsers(username);
    }

    boolean saveTaskList(TaskList taskList) {
        taskListRepository.save(taskList);
        return true;
    }

    boolean deleteTaskList(Long id) {
        taskListRepository.deleteById(id);
        return true;
    }

    boolean existsById(Long id) {
        return taskListRepository.existsById(id);
    }

    boolean updateTaskList(Long id, TaskList taskList) {
        taskList.setId(id);
        taskList.updateFrom(taskList);
        taskListRepository.save(taskList);
        return true;
    }
}