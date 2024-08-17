package com.example.backend.repository;

import com.example.backend.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    @Query("SELECT t FROM TodoList t WHERE t.user.email = :email")
    List<TodoList> findAllByUserEmail(@Param("email") String email);

    @Query("SELECT t FROM TodoList t WHERE t.title = :title AND t.user.id = :id")
    Optional<TodoList> findByTitleAndUserId(@Param("title") String title, @Param("id") Long id);
}
