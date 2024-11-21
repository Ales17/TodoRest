package cz.ales17.test.controller;

import cz.ales17.test.entity.Task;
import cz.ales17.test.entity.UserEntity;
import cz.ales17.test.service.TaskService;
import cz.ales17.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    @GetMapping("/tasks")
    public List<Task> tasks(@RequestHeader("X-User-Id") Long userId) {
        UserEntity user = userService.findOne(userId);
        return taskService.findTasksByUser(user);
    }

    @GetMapping("/tasks/{taskId}")
    public Task showTask(@RequestHeader("X-User-Id") Long userId, @PathVariable("taskId") Long taskId) {
        return taskService.findTask(userId, taskId);
    }

    @PutMapping("/tasks/new")
    public ResponseEntity<Task> createTask(@RequestHeader("X-User-Id") Long userId, @RequestBody Task task) {
        Task newTask = taskService.createTask(userId, task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@RequestHeader("X-User-Id") Long userId, @PathVariable("taskId") Long taskId) {
        taskService.deleteTask(userId, taskId);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<String> updateTask(@RequestHeader("X-User-Id") Long userId, @PathVariable("taskId") Long taskId,@RequestBody Task task) {
        task.setId(taskId);
        taskService.updateTask(userId, task);
        return new ResponseEntity<>("Updated", HttpStatus.NO_CONTENT);
    }
}
