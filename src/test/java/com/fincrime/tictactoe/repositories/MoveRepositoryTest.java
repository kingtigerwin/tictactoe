package com.fincrime.tictactoe.repositories;

import com.fincrime.tictactoe.TictactoeApplication;
import com.fincrime.tictactoe.entities.Game;
import com.fincrime.tictactoe.entities.Move;
import com.fincrime.tictactoe.enums.Player;
import com.fincrime.tictactoe.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TictactoeApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MoveRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MoveRepository moveRepository;

    @Test
    public void givenMoveRepository_whenSaveAndRetrieveMove_thenOK() {
        Game returnedGame = gameRepository.save(buildGameObject("game_name", Status.READY));
        Move returnedMove = moveRepository.save(buildMoveObject("1","2", returnedGame));

        Assertions.assertNotNull(returnedMove);
        Assertions.assertNotNull(returnedMove.getId());
        Assertions.assertEquals(returnedMove.getHorizontalAxis(), "1");
        Assertions.assertEquals(returnedMove.getVerticalAxis(), "2");
    }

    @Test
    public void givenMoveRepository_whenSaveAndUpdateGame_thenOK() {
        Game returnedGame = gameRepository.save(buildGameObject("game_name", Status.READY));
        Move returnedMove = moveRepository.save(buildMoveObject("1","2", returnedGame));
        returnedMove.setPlayer(Player.NOUGHT);
        Move updatedMove = moveRepository.save(returnedMove);

        Assertions.assertNotNull(updatedMove);
        Assertions.assertEquals(updatedMove.getHorizontalAxis(), "1");
        Assertions.assertEquals(updatedMove.getVerticalAxis(), "2");
        Assertions.assertEquals(updatedMove.getPlayer(), Player.NOUGHT);
    }

    @Test
    public void givenMoveRepository_whenSaveAndRetrieveMoveById_thenOK() {
        Game returnedGame = gameRepository.save(buildGameObject("game_name", Status.READY));
        Move returnedMove = moveRepository.save(buildMoveObject("1","2", returnedGame));
        Optional<Move> moveOptional = moveRepository.findById(returnedMove.getId());
        moveOptional.ifPresent(move -> {
            Assertions.assertNotNull(move);
            Assertions.assertEquals(move.getHorizontalAxis(), "1");
            Assertions.assertEquals(move.getVerticalAxis(), "2");
        });
    }

    @Test
    public void givenGameRepository_whenSaveMultiMovesAndFindAllByGameId_thenOK() {
        String gameName_1="game Name 1";
        Game returnedGame = gameRepository.save(buildGameObject(gameName_1, Status.READY));
        moveRepository.save(buildMoveObject("1","2", returnedGame));
        moveRepository.save(buildMoveObject("1","3", returnedGame));
        Set<Move> moves = moveRepository.findAllByGameId(returnedGame.getId());
        Assertions.assertEquals(2, moves.size());
    }

    private Game buildGameObject(String name, Status status) {
        Game game = new Game();
        game.setName(name);
        game.setStatus(status);
        return game;
    }

    private Move buildMoveObject(String horizontalAxis, String verticalAxis, Game game) {
       Move move = new Move();
       move.setHorizontalAxis(horizontalAxis);
       move.setVerticalAxis(verticalAxis);
       move.setGame(game);
       return move;
    }
}
