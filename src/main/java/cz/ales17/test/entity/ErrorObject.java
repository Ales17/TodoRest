package cz.ales17.test.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ErrorObject {
    private Integer code;
    private String message;
    private Date timestamp;
}
