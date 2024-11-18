package cz.ales17.test.controller;

import cz.ales17.test.entity.Company;
import cz.ales17.test.entity.Task;
import cz.ales17.test.entity.UserEntity;
import cz.ales17.test.repository.CompanyRepository;
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
    private final CompanyRepository companyRepository;

    public TestController(UserService userService, TaskService taskService, CompanyRepository companyRepository) {
        this.userService = userService;
        this.taskService = taskService;
        this.companyRepository = companyRepository;
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

    @GetMapping("/companies")
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }
}
