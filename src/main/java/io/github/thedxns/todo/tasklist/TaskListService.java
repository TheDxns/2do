package io.github.thedxns.todo.tasklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListService {
    private final TaskListRepository taskListRepository;

    @Autowired
    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    public List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }
    
    public TaskList getTaskList(Long id) {
        return taskListRepository.findById(id).get();
    }

    public List<TaskList> getAllByUser(String username) {
        return taskListRepository.findByUsers(username);
    }

    public boolean saveTaskList(TaskList taskList) {
        taskListRepository.save(taskList);
        return true;
    }

    public boolean deleteTaskList(Long id) {
        taskListRepository.deleteById(id);
        return true;
    }

    public boolean existsById(Long id) {
        return taskListRepository.existsById(id);
    }

    public boolean updateTaskList(Long id, TaskList taskList) {
        taskList.setId(id);
        taskList.updateFrom(taskList);
        taskListRepository.save(taskList);
        return true;
    }

    public boolean deleteAllByUser(String id) {
        taskListRepository.deleteByUsers(id);
        return true;
    }
}