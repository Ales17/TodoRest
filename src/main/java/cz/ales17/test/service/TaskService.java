package cz.ales17.test.service;

import cz.ales17.test.entity.Company;
import cz.ales17.test.entity.Role;
import cz.ales17.test.entity.Task;
import cz.ales17.test.entity.UserEntity;
import cz.ales17.test.exception.AccessDeniedException;
import cz.ales17.test.exception.TaskNotFoundException;
import cz.ales17.test.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findTaskByUser(UserEntity user) {
        return taskRepository.findAllByCreatedByIs(user);
    }

    public List<Task> findTasksByCompany(Company company) {
        return taskRepository.findAllByCreatedBy_Company(company);
    }
    public List<Task> findTasksByUser(UserEntity user) {
        return switch (user.getRole()) {
            case USER -> findTaskByUser(user);
            case COMPANY_ADMIN -> findTasksByCompany(user.getCompany());
            case SUPER_USER -> findAll();
        };
    }

    public Task findTask(Long userId, Long taskId) {
        UserEntity user = userService.findOne(userId);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Unable to find this task"));
        if (!hasAccess(task, user)) {
            throw new AccessDeniedException("You do not have permission to access this task.");
        }
        return task;
    }

    private boolean hasAccess(Task task, UserEntity user) {
        return user.getRole() == Role.USER && task.getCreatedBy() == user || user.getRole() == Role.COMPANY_ADMIN && task.getCreatedBy().getCompany() == user.getCompany() || user.getRole() == Role.SUPER_USER;
    }

    public Task createTask(Long userId, Task task) {
        UserEntity user = userService.findOne(userId);
        task.setId(null);
        task.setCreatedBy(user);
        return taskRepository.save(task);
    }

    public void deleteTask(Long userId, Long taskId) {
        UserEntity user = userService.findOne(userId);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Unable to find this task"));
        if (!hasAccess(task, user)) {
            throw new AccessDeniedException("You do not have permission to delete this task.");
        }
        taskRepository.delete(task);
    }

    public void updateTask(Long userId, Task task) {
        UserEntity user = userService.findOne(userId);
        Task t = taskRepository.findById(task.getId()).orElseThrow(() -> new TaskNotFoundException("Unable to find this task"));
        if (!hasAccess(task, user)) {
            throw new AccessDeniedException("You do not have permission to update this task.");
        }
        t.setDescription(task.getDescription());
        t.setName(task.getName());
        t.setCompleted(task.isCompleted());
        taskRepository.save(t);
    }
}
