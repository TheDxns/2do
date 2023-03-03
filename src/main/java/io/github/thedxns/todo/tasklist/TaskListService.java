package io.github.thedxns.todo.tasklist;

import io.github.thedxns.todo.user.KeycloakId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskListService {
    private final TaskListRepository taskListRepository;

    TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }
    
    public TaskListDto getTaskList(final Long id) {
        final Optional<TaskList> taskList = taskListRepository.findById(id);
        return taskList.map(TaskListDto::from).orElse(null);
    }

    List<TaskList> getAllByUser(final String username) {
        return taskListRepository.findByUsers(username);
    }

    boolean saveTaskList(final TaskListRequest taskListRequest) {
        final TaskListDto taskList = new TaskListDto(null, taskListRequest.getTitle(),
                new KeycloakId(taskListRequest.getOwnerId()), null);
        taskListRepository.save(new TaskList(taskList));
        return true;
    }

    boolean deleteTaskList(final Long id) {
        taskListRepository.deleteById(id);
        return true;
    }

    boolean existsById(final Long id) {
        return taskListRepository.existsById(id);
    }

    boolean updateTaskList(final Long id, final TaskListDto taskList) {
        final TaskList updatedTaskList = new TaskList();
        updatedTaskList.setId(id);
        updatedTaskList.updateFromDto(taskList);
        taskListRepository.save(updatedTaskList);
        return true;
    }
}