package cz.ales17.test.controller;

import cz.ales17.test.entity.Task;
import cz.ales17.test.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> tasks(@RequestHeader("X-User-Id") Long userId) {

        return taskService.findTasksByUserRole(userId);
    }
}
