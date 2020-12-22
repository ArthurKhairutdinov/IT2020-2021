package ru.itis.models;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NamedComment {
    private String firstName;
    private String lastName;
    private String text;

}
