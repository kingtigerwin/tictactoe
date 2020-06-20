package com.fincrime.tictactoe.entities;

import com.fincrime.tictactoe.enums.Player;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "move")
@Getter
@Setter
@ToString(exclude = "game")
@RequiredArgsConstructor
public class Move {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "vertical_axis", nullable = false)
    private String verticalAxis;

    @Column(name = "horizontal_axis", nullable = false)
    private String horizontalAxis;

    @Enumerated(EnumType.STRING)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
}
