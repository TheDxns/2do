package io.github.thedxns.todo.tasklist;

import io.github.thedxns.todo.user.KeycloakId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskListService {
    private final TaskListRepository taskListRepository;

    TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    List<TaskListDto> getAllTaskLists() {
        return taskListRepository.findAll().stream().map(TaskListDto::from).collect(Collectors.toList());
    }
    
    public TaskListDto getTaskList(final Long id) {
        final Optional<TaskList> taskList = taskListRepository.findById(id);
        return taskList.map(TaskListDto::from).orElse(null);
    }

    List<TaskListDto> getAllByUser(final String username) {
        return taskListRepository.findByUsers(username).stream().map(TaskListDto::from).collect(Collectors.toList());
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

    boolean updateTaskList(final Long id, final TaskListRequest taskListRequest) {
        final TaskListDto updatedTaskList = new TaskListDto(id, taskListRequest.getTitle(),
                new KeycloakId(taskListRequest.getOwnerId()), null);
        taskListRepository.save(new TaskList(updatedTaskList));
        return true;
    }

    public boolean grantAccessToUser(final Long taskListId, final KeycloakId userId) {
        final TaskListDto taskListData = getTaskList(taskListId);
        final TaskList taskList = new TaskList(taskListData);
        final List<String> users = taskList.getUsers();
        users.add(userId.getId());
        taskList.setUsers(users);
        taskListRepository.save(taskList);
        return true;
    }

    public boolean removeAccessOfUser(final Long taskListId, final KeycloakId userId) {
        final TaskListDto taskListData = getTaskList(taskListId);
        final TaskList taskList = new TaskList(taskListData);
        final List<String> users = taskList.getUsers();
        users.remove(userId.getId());
        taskList.setUsers(users);
        taskListRepository.save(taskList);
        return true;
    }
}