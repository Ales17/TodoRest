package cz.ales17.test.service;

import cz.ales17.test.entity.UserEntity;
import cz.ales17.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
