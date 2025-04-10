package de.mydev.app.service;


import de.mydev.app.model.*;

import java.util.List;


public interface ITodoService
{
    TodoItem save(TodoItem todoItem, String username);

    TodoItem completeTask(Long itemId);

    List<TodoItem> findWaitingList(Long userId);
}
