package io.github.thedxns.todo.tasklist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findByUserIds(long username);

    List<TaskList> findByOwnerId(long ownerId);
}