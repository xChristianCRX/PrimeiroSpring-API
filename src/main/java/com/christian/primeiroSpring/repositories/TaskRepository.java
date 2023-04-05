package com.christian.primeiroSpring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.christian.primeiroSpring.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{

    List<Task>findByUser_Id(Integer id);

    /* @Query(value = "SELECT t FROM Task t where t.user.id = :user_id")
    List<Task>findByUserId(@Param("user_id")Integer user_id); */

    /* @Query(value = "SELECT * FROM task t where t.user_id = :id", nativeQuery = true)
    List<Task>findByUserId(@Param("id")Integer id); */
}
