package com.UnitTest.springUnitTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.UnitTest.springUnitTest.domains.ToDo;
import com.UnitTest.springUnitTest.domains.ToDoRepository;
import com.UnitTest.springUnitTest.domains.ToDoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
public class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoServiceImpl toDoService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllToDo(){
        List<ToDo> toDoList = new ArrayList<ToDo>();
        toDoList.add(new ToDo(1,"Todo Sample 1",true));
        toDoList.add(new ToDo(2,"Todo Sample 2",true));
        toDoList.add(new ToDo(3,"Todo Sample 3",false));
        //toDoList.add(new ToDo(4,"Todo Sample 4",false));
        when(toDoRepository.findAll()).thenReturn(toDoList);

        List<ToDo> result = toDoService.getAllToDo();
        assertEquals(3, result.size());
    }

    @Test
    public void testGetToDoById(){
        ToDo toDo = new ToDo(1,"Todo Sample 1",true);
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(toDo));
        ToDo result = toDoService.getToDoById(1).get();
        assertEquals(1, result.getId());
        assertEquals("Todo Sample 1", result.getText());
        assertEquals(true, result.isCompleted());
    }

    @Test
    public void saveToDo(){
        ToDo toDo = new ToDo(8,"Todo Sample 8",true);
        when(toDoRepository.save(toDo)).thenReturn(toDo);
        ToDo result = toDoService.saveToDo(toDo);
        assertEquals(8, result.getId());
        assertEquals("Todo Sample 8", result.getText());
        assertEquals(true, result.isCompleted());
    }

    @Test
    public void removeToDo(){
        ToDo toDo = new ToDo(8,"Todo Sample 8",true);
        toDoService.removeToDo(toDo);
        verify(toDoRepository, times(1)).delete(toDo);
    }

    @Spy
    List<String> spiedList = new ArrayList<String>();

    @Test
    public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        spiedList.add("one");
        spiedList.add("two");

        verify(spiedList).add("one");
        verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        //doReturn(100).when(spiedList).size();
        when(spiedList.size()).thenReturn(100);
        assertEquals(100, spiedList.size());
    }



}