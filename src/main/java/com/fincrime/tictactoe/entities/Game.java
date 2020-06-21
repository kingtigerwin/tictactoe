package com.fincrime.tictactoe.entities;

import com.fincrime.tictactoe.constants.Player;
import com.fincrime.tictactoe.constants.Status;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "game")
@Getter
@Setter
@RequiredArgsConstructor
public class Game {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.READY;

    @Column(name = "last_player", nullable = true)
    @Enumerated(EnumType.STRING)
    private Player lastPlayer;

    @OneToMany(mappedBy = "game")
    private Set<Move> moves;

}
