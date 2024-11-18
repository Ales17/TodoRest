package cz.ales17.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Task {
    @Id
    private Long id;

    private String name;

    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity createdBy;
}
