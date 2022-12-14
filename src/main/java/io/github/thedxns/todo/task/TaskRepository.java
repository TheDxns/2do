package io.github.thedxns.todo.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.thedxns.todo.task.Task;

@Repository
interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByPrioritized(boolean b);
    List<Task> findByCreatorId(String creatorId);
    List<Task> findByTaskListId(Long id);
    List<Task> findByTitleContainingIgnoreCase(String keyword);
    List<Task> findByContentContainingIgnoreCase(String keyword);
    void deleteByCreatorId(String id);
}
