package cz.ales17.test.service;

import cz.ales17.test.entity.UserEntity;
import cz.ales17.test.exception.UserNotFoundException;
import cz.ales17.test.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findOne(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
