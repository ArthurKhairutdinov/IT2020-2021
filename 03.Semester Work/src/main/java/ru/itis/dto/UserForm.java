package ru.itis.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
public class UserForm {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(min = 2, max = 20)
    private String first_name;

    @NotEmpty
    @Length(min = 3, max = 20)
    private String last_name;

    @NotEmpty
    @Length(min = 8, max = 20)
    private String password;
    
    @Min(value=14)
    @Max(value=150)
    private Integer age;
}

