package ru.itis.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class User {
    private Long id;
    private String email;
    private String hashPassword;
    private String firstName;
    private String lastName;
    private Integer age;
}
