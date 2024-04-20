package com.biwaby.projects.jokebot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "jokes")
@Table(name = "jokes")
@EqualsAndHashCode
public class Joke {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "joke_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "joke_seq", name = "joke_seq", allocationSize = 1)
    private Long id;

    @Lob
    @Column(name = "joke", columnDefinition = "TEXT")
    private String joke;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "creation_date")
    private Date creationDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "update_date")
    private Date updatingDate;

    @OneToMany(mappedBy = "joke", cascade = CascadeType.ALL)
    private List<JokeCallLog> jokeCallHistory;
}
