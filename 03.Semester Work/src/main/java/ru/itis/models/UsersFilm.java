package ru.itis.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class UsersFilm {
    private long id;
    private long usersId;
    private String name;
    private int score;
}