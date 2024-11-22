package cz.ales17.test;

import cz.ales17.test.entity.Company;
import cz.ales17.test.entity.Role;
import cz.ales17.test.entity.Task;
import cz.ales17.test.entity.UserEntity;
import cz.ales17.test.repository.TaskRepository;
import cz.ales17.test.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldFindAllTasks() {
        List<Task> tasks = List.of(new Task(1L, "Task 1", "Description", true, new UserEntity()));
        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> result = taskService.findAll();
        assertEquals(1, result.size());
        assertEquals("Task 1", result.get(0).getName());
    }

    @Test
    void shouldFindTaskByUser() {
        UserEntity user = new UserEntity();
        List<Task> tasks = List.of(new Task(1L, "Task 1", "Description", true, user));
        when(taskRepository.findAllByCreatedByIs(user)).thenReturn(tasks);
        List<Task> result = taskService.findTaskByUser(user);
        assertEquals(1, result.size());
        assertEquals("Task 1", result.get(0).getName());
    }

    @Test
    void shouldFindTasksByCompany() {
        Company company = new Company(1L, "Company");
        UserEntity user = new UserEntity(1L, "User", Role.COMPANY_ADMIN, new Company());
        List<Task> tasks = List.of(new Task(1L, "Task 1", "Description", true, user));
        when(taskRepository.findAllByCreatedBy_Company(company)).thenReturn(tasks);
        List<Task> result = taskService.findTasksByCompany(company);
        assertEquals(1, result.size());
        assertEquals("Task 1", result.get(0).getName());
    }

    @Test
    void shouldFindTasksByUser() {
        UserEntity user = new UserEntity(1L, "User", Role.USER, new Company());
        List<Task> tasks = List.of(new Task(1L, "Task 1", "Description", true, user));
        when(taskRepository.findAllByCreatedByIs(user)).thenReturn(tasks);
        List<Task> result = taskService.findTasksByUser(user);
        assertEquals(1, result.size());
        assertEquals("Task 1", result.get(0).getName());
    }



}
