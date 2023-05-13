package io.github.thedxns.todo.tasklist;

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

    List<TaskListDto> getAllByUserId(final long userId) {
        return taskListRepository.findByUserIds(userId).stream().map(TaskListDto::from).collect(Collectors.toList());
    }

    List<TaskListDto> getAllByOwnerId(final long ownerId) {
        return taskListRepository.findByOwnerId(ownerId).stream().map(TaskListDto::from).collect(Collectors.toList());
    }

    boolean saveTaskList(final TaskListRequest taskListRequest) {
        final TaskListDto taskList = new TaskListDto(null, taskListRequest.getTitle(),
                taskListRequest.getOwnerId(), null);
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
                taskListRequest.getOwnerId(), null);
        taskListRepository.save(new TaskList(updatedTaskList));
        return true;
    }

    public boolean grantAccessToUser(final long taskListId, final long userId) {
        final TaskList taskList = new TaskList(getTaskList(taskListId));
        if (taskList.getOwnerId() == userId) {
            throw new RuntimeException("Given user is the owner of the list");
        }
        final List<Long> userIds = taskList.getUserIds();
        userIds.add(userId);
        taskList.setUserIds(userIds);
        taskListRepository.save(taskList);
        return true;
    }

    public boolean removeAccessOfUser(final long taskListId, final long userId) {
        final TaskList taskList = new TaskList(getTaskList(taskListId));
        final List<Long> userIds = taskList.getUserIds();
        userIds.remove(userId);
        taskList.setUserIds(userIds);
        taskListRepository.save(taskList);
        return true;
    }

    public List<UserResponse> getPermittedUsers(final Long id) {
        final TaskListDto taskList = getTaskList(id);
        final List<Long> userIds = taskList.getUserIds();
        final List<UserResponse> permittedUsers = new ArrayList<>();
        for (long userId : userIds) {
            permittedUsers.add(UserResponse.from(userService.getUserById(userId)));
        }
        return permittedUsers;
    }

    public List<TaskListDto> getAllPermittedForUser(long userId) {
        final List<TaskListDto> allByOwner = getAllByOwnerId(userId);
        final List<TaskListDto> allByUser = getAllByUserId(userId);
        final List<TaskListDto> result = new ArrayList<>(allByOwner);
        result.addAll(allByUser);
        return result;
    }
}