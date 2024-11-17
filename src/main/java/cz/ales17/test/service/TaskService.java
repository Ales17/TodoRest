package cz.ales17.test.service;

import cz.ales17.test.entity.Company;
import cz.ales17.test.entity.Role;
import cz.ales17.test.entity.Task;
import cz.ales17.test.entity.UserEntity;
import cz.ales17.test.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findByUser(UserEntity user) {
        return taskRepository.findAllByCreatedByIs(user);
    }

    public List<Task> findByCompany(Company company) {
        return taskRepository.findAllByCreatedBy_Company(company);
    }

    public List<Task> findTasksByUserRole(Long userId) {
        UserEntity user = userService.findOne(userId);
        Role userRole = user.getRole();

        return switch (userRole) {
            case USER -> findByUser(user);
            case COMPANY_ADMIN -> findByCompany(user.getCompany());
            case SUPER_USER -> findAll();
        };
    }
}
