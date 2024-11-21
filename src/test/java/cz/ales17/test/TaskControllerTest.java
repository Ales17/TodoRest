package cz.ales17.test;

import cz.ales17.test.entity.Task;
import cz.ales17.test.entity.UserEntity;
import cz.ales17.test.service.TaskService;
import cz.ales17.test.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Test
    void shouldReturnTaskForValidUserId() throws Exception {
        UserEntity user = userService.findOne(1L);
        Task task = taskService.findTask(1L, 1L);

        mockMvc.perform(get("/api/tasks/1")
                        .header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.name").value(task.getName()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.completed").value(task.isCompleted()))
        ;
    }

    @Test
    void shouldReturnTasksForValidUserId() throws Exception {
        UserEntity user = userService.findOne(1L);
        List<Task> tasks = taskService.findTasksByUser(user);

        mockMvc.perform(get("/api/tasks")
                        .header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(tasks.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(tasks.get(0).getName()))
                .andExpect(jsonPath("$[0].description").value(tasks.get(0).getDescription()))
                .andExpect(jsonPath("$[0].completed").value(tasks.get(0).isCompleted())
                )
                .andExpect(jsonPath("$[1].id").value(tasks.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(tasks.get(1).getName()))
                .andExpect(jsonPath("$[1].description").value(tasks.get(1).getDescription()))
                .andExpect(jsonPath("$[1].completed").value(tasks.get(1).isCompleted())
                );
    }

    @Test
    void shouldReturn404ForNonexistentTask() throws Exception {

        mockMvc.perform(get("/api/tasks/999")
                        .header("X-User-Id", "1"))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldReturn403ForAccessDenied() throws Exception {
        mockMvc.perform(get("/api/tasks/1")
                        .header("X-User-Id", "2"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn400WhenXUserIdHeaderIsMissing() throws Exception {
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isBadRequest());
    }



    @Test
    void shouldDeleteItemWhenIdIsValid() throws Exception {
        mockMvc.perform(delete("/api/tasks/1").header("X-User-Id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenDeletingNonexistentItem() throws Exception {
        mockMvc.perform(delete("/api/tasks/999").header("X-User-Id", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn403WhenDeletingItemWithoutPermission() throws Exception {
        mockMvc.perform(delete("/api/tasks/1").header("X-User-Id", "2"))
                .andExpect(status().isForbidden());
    }
}
