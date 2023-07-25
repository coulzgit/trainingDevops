package com.UnitTest.springUnitTest.domains;

import java.util.List;
import java.util.Optional;

public interface ToDoService {
    public List<ToDo> getAllToDo();
    public Optional<ToDo> getToDoById(long id);
    public ToDo saveToDo(ToDo todo);
    public void removeToDo(ToDo todo);
}