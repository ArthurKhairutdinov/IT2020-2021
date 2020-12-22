package ru.itis.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Actor {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
}
