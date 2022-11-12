package io.github.thedxns.todo.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import io.github.thedxns.todo.model.Task;
import io.github.thedxns.todo.model.repositories.TaskRepository;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllTasks(Pageable page) {
        return taskRepository.findAll(page).getContent();
    }

    public List<Task> getAllPrioritized() {
        return taskRepository.findByPrioritized(true);
    }

    public List<Task> getTasksByKeyword(String keyword) {
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(keyword);
        tasks.addAll(taskRepository.findByContentContainingIgnoreCase(keyword));
        Set<Task> set = new LinkedHashSet<>(tasks);
        tasks.clear();
        tasks.addAll(set);
        return tasks;
    }
    
    public Task getTask(Long id) {
        return taskRepository.findById(id).get();
    }

    public List<Task> getTasksByList(Long id) {
        return taskRepository.findByTaskListId(id);
    } 

    public List<Task> getAllByCreator(String creatorId) {
        return taskRepository.findByCreatorId(creatorId);
    }

    public List<Task> getUnfinishedByCreator(String creatorId) {
        List<Task> allTasks = taskRepository.findByCreatorId(creatorId);
        return allTasks.stream()
            .filter(t -> t.isDone() == false && t.getTaskList() == null).collect(Collectors.toList());
    }

    public List<Task> getDoneByCreator(String creatorId) {
        List<Task> allTasks = taskRepository.findByCreatorId(creatorId);
        return allTasks.stream()
            .filter(t -> t.isDone() == true && t.getTaskList() == null).collect(Collectors.toList());
    }

    public List<Task> getImportantByCreator(String creatorId) {
        List<Task> allTasks = taskRepository.findByCreatorId(creatorId);
        return allTasks.stream()
            .filter(t -> t.isDone() == false && t.isPrioritized() == true).collect(Collectors.toList());
    }

    public List<Task> getCustom(Long listId) {
        List<Task> allTasks = taskRepository.findByTaskListId(listId);
        return allTasks.stream()
            .filter(t -> t.isDone() == false && t.getTaskList() != null).collect(Collectors.toList());
    }

    public boolean saveTask(Task task) {
        taskRepository.save(task);
        return true;
    }

    public boolean deleteTask(Long id) {
        taskRepository.deleteById(id);
        return true;
    }

    public boolean existsById(Long id) {
        return taskRepository.existsById(id);
    }

    public boolean updateTask(Long id, Task task) {
        task.setId(id);
        task.updateFrom(task);
        taskRepository.save(task);
        return true;
    }

    public boolean deleteAllByUser(String id) {
        taskRepository.deleteByCreatorId(id);
        return true;
    }
}