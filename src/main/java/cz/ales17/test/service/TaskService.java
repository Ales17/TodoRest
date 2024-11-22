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

/**
 * Service for task management
 */
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    /**
     * Find all tasks.
     *
     * @return List of all tasks
     */
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    /**
     * Find tasks created by a specific user.
     *
     * @param user The user entity
     * @return List of tasks created by the user
     */
    public List<Task> findTaskByUser(UserEntity user) {
        return taskRepository.findAllByCreatedByIs(user);
    }

    /**
     * Find tasks created by users in a specific company.
     *
     * @param company The company entity
     * @return List of tasks created by users in the company
     */
    public List<Task> findTasksByCompany(Company company) {
        return taskRepository.findAllByCreatedBy_Company(company);
    }

    /**
     * Find tasks based on the user's role.
     *
     * @param user The user entity
     * @return List of tasks accessible by the user
     */
    public List<Task> findTasksByUser(UserEntity user) {
        return switch (user.getRole()) {
            case USER -> findTaskByUser(user);
            case COMPANY_ADMIN -> findTasksByCompany(user.getCompany());
            case SUPER_USER -> findAll();
        };
    }

    /**
     * Find a specific task by its ID and the user's ID.
     *
     * @param userId The user ID
     * @param taskId The task ID
     * @return The task entity
     * @throws TaskNotFoundException if the task is not found
     * @throws AccessDeniedException if the user does not have access to the task
     */
    public Task findTask(Long userId, Long taskId) {
        UserEntity user = userService.findOne(userId);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Unable to find this task"));
        if (!hasAccess(task, user)) {
            throw new AccessDeniedException("You do not have permission to access this task.");
        }
        return task;
    }

    /**
     * Check if the user has access to the task.
     *
     * @param task The task entity
     * @param user The user entity
     * @return true if the user has access, false otherwise
     */
    private boolean hasAccess(Task task, UserEntity user) {
        return user.getRole() == Role.USER && task.getCreatedBy() == user || user.getRole() == Role.COMPANY_ADMIN && task.getCreatedBy().getCompany() == user.getCompany() || user.getRole() == Role.SUPER_USER;
    }

    /**
     * Create a new task.
     *
     * @param userId The user ID
     * @param task The task entity
     * @return The created task entity
     */
    public Task createTask(Long userId, Task task) {
        UserEntity user = userService.findOne(userId);
        task.setId(null);
        task.setCreatedBy(user);
        return taskRepository.save(task);
    }

    /**
     * Delete a specific task by its ID and the user's ID.
     *
     * @param userId The user ID
     * @param taskId The task ID
     * @throws TaskNotFoundException if the task is not found
     * @throws AccessDeniedException if the user does not have access to delete the task
     */
    public void deleteTask(Long userId, Long taskId) {
        UserEntity user = userService.findOne(userId);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Unable to find this task"));
        if (!hasAccess(task, user)) {
            throw new AccessDeniedException("You do not have permission to delete this task.");
        }
        taskRepository.delete(task);
    }

    /**
     * Update a specific task.
     *
     * @param userId The user ID
     * @param task The task entity
     * @throws TaskNotFoundException if the task is not found
     * @throws AccessDeniedException if the user does not have access to update the task
     */
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