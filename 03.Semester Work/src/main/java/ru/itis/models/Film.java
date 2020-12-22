package ru.itis.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Film {
    private long id;
    private String name;
    private String genre;
    private double criticScore;
    private double usersScore;
    private int year;
}
