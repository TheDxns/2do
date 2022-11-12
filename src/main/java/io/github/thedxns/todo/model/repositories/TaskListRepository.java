package io.github.thedxns.todo.model.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.thedxns.todo.model.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findByUsers(String username);
    void deleteByUsers(String id);
}