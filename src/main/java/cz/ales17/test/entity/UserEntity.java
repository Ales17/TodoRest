package cz.ales17.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserEntity {
    @Id
    private Long id;

    private String username;

    @Enumerated(EnumType.ORDINAL)
    private Role role;
}
