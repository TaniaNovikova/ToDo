package de.mydev.app.repository;

import de.mydev.app.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ITodoItemRepository extends JpaRepository<TodoItem, Long>
{
    List<TodoItem> findByUserIdAndDoneFalse(Long userId);
}
