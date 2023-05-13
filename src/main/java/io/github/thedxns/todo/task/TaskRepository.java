package io.github.thedxns.todo.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByCreatorId(long creatorId);
    List<Task> findByTaskListId(long id);
    List<Task> findByTitleContainingIgnoreCase(String keyword);
    List<Task> findByContentContainingIgnoreCase(String keyword);
    List<Task> findByDeadline(LocalDateTime deadline);
}
