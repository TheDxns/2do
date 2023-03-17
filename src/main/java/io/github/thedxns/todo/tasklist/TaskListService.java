package io.github.thedxns.todo.tasklist;

import io.github.thedxns.todo.user.KeycloakId;
import io.github.thedxns.todo.user.UserResponse;
import io.github.thedxns.todo.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskListService {

    private final UserService userService;
    private final TaskListRepository taskListRepository;

    TaskListService(TaskListRepository taskListRepository, UserService userService) {
        this.taskListRepository = taskListRepository;
        this.userService = userService;
    }

    List<TaskListDto> getAllTaskLists() {
        return taskListRepository.findAll().stream().map(TaskListDto::from).collect(Collectors.toList());
    }
    
    public TaskListDto getTaskList(final Long id) {
        final Optional<TaskList> taskList = taskListRepository.findById(id);
        return taskList.map(TaskListDto::from).orElse(null);
    }

    List<TaskListDto> getAllByUserId(final String userId) {
        return taskListRepository.findByUsers(userId).stream().map(TaskListDto::from).collect(Collectors.toList());
    }

    List<TaskListDto> getAllByOwnerId(final String ownerId) {
        return taskListRepository.findByOwnerId(ownerId).stream().map(TaskListDto::from).collect(Collectors.toList());
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

    public boolean existsById(final Long id) {
        return taskListRepository.existsById(id);
    }

    boolean updateTaskList(final Long id, final TaskListRequest taskListRequest) {
        final TaskListDto updatedTaskList = new TaskListDto(id, taskListRequest.getTitle(),
                new KeycloakId(taskListRequest.getOwnerId()), null);
        taskListRepository.save(new TaskList(updatedTaskList));
        return true;
    }

    public boolean grantAccessToUser(final Long taskListId, final KeycloakId userId) {
        final TaskList taskList = new TaskList(getTaskList(taskListId));
        if (taskList.getOwnerId().equals(userId.getId())) {
            throw new RuntimeException("Given user is the owner of the list");
        }
        final List<String> users = taskList.getUsers();
        users.add(userId.getId());
        taskList.setUsers(users);
        taskListRepository.save(taskList);
        return true;
    }

    public boolean removeAccessOfUser(final long taskListId, final KeycloakId userId) {
        final TaskList taskList = new TaskList(getTaskList(taskListId));
        final List<String> users = taskList.getUsers();
        users.remove(userId.getId());
        taskList.setUsers(users);
        taskListRepository.save(taskList);
        return true;
    }

    public List<UserResponse> getPermittedUsers(final Long id) {
        final TaskListDto taskList = getTaskList(id);
        final List<KeycloakId> userIds = taskList.getUsers();
        final List<UserResponse> permittedUsers = new ArrayList<>();
        for (KeycloakId userId : userIds) {
            permittedUsers.add(UserResponse.from(userService.getUserById(userId)));
        }
        return permittedUsers;
    }

    public List<TaskListDto> getAllPermittedForUser(String userId) {
        final List<TaskListDto> allByOwner = getAllByOwnerId(userId);
        final List<TaskListDto> allByUser = getAllByUserId(userId);
        final List<TaskListDto> result = new ArrayList<>(allByOwner);
        result.addAll(allByUser);
        return result;
    }
}