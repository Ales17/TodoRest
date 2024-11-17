package cz.ales17.test.controller;

import cz.ales17.test.entity.Task;
import cz.ales17.test.entity.UserEntity;
import cz.ales17.test.service.TaskService;
import cz.ales17.test.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private final UserService userService;
    private final TaskService taskService;

    public TestController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String test() {
        return "test";
    }

    @GetMapping("/users")
    public List<UserEntity> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserEntity getUser(@PathVariable Long id) {
        return userService.findOne(id);
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskService.findAll();
    }
}
