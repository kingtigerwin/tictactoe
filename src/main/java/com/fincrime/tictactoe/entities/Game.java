package com.fincrime.tictactoe.entities;

import com.fincrime.tictactoe.enums.Player;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "game")
@Data
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
    private String status;

    @Column(name="last_player", nullable = true)
    private Player lastPlayer;

    @OneToMany(mappedBy = "game")
    private Set<Move> moves;

}
