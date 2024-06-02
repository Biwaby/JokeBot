package com.biwaby.projects.jokebot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "jokesCallLog")
@Table (name = "jokesCallLog")
public class JokeCallLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "jokeCallLog_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "jokeCallLog_seq", name = "jokeCallLog_seq", allocationSize = 1)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "joke_id")
    private Joke joke;

    @Column(name = "user_id")
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Europe/Moscow")
    @Column(name = "call_date")
    private Date callDate;
}
