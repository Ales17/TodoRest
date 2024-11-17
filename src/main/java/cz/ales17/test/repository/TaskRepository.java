package cz.ales17.test.repository;

import cz.ales17.test.entity.Company;
import cz.ales17.test.entity.Task;
import cz.ales17.test.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByCreatedByIs(UserEntity user);

    List<Task> findAllByCreatedBy_Company(Company company);
}
