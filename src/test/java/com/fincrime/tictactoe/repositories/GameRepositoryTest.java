package com.fincrime.tictactoe.repositories;

import com.fincrime.tictactoe.TictactoeApplication;
import com.fincrime.tictactoe.entities.Game;
import com.fincrime.tictactoe.constants.Player;
import com.fincrime.tictactoe.constants.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TictactoeApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void givenGameRepository_whenSaveAndRetrieveGame_thenOK() {
        String gameName = "game name for insert";
        Game returnedGame = gameRepository.save(buildGameObject(gameName, Status.READY));
        Assertions.assertNotNull(returnedGame);
        Assertions.assertNotNull(returnedGame.getId());
        Assertions.assertEquals(returnedGame.getName(), gameName);
        Assertions.assertEquals(returnedGame.getStatus(), Status.READY);
    }

    @Test
    public void givenGameRepository_whenSaveAndUpdateGame_thenOK() {
        String gameName = "game name for update";
        Game returnedGame = gameRepository.save(buildGameObject(gameName, Status.READY));
        returnedGame.setLastPlayer(Player.CROSS);
        Assertions.assertNotNull(returnedGame);
        Assertions.assertEquals(returnedGame.getName(), gameName);
        Assertions.assertEquals(returnedGame.getStatus(), Status.READY);
        Assertions.assertEquals(returnedGame.getLastPlayer(), Player.CROSS);
    }

    @Test
    public void givenGameRepository_whenSaveAndRetrieveGameById_thenOK() {
        String gameName = "game name for retrieve game by id";
        Game returnedGame = gameRepository.save(buildGameObject(gameName, Status.READY));
        Optional<Game> gameOptional = gameRepository.findById(returnedGame.getId());
        gameOptional.ifPresent(game -> {
            Assertions.assertNotNull(game);
            Assertions.assertEquals(game.getName(), gameName);
            Assertions.assertEquals(game.getStatus(), Status.READY);
        });
    }

    @Test
    public void givenGameRepository_whenSaveAndRetrieveGameByName_thenOK() {
        String gameName = "game name for retrieve game by name";
        gameRepository.save(buildGameObject(gameName, Status.READY));
        Game game = gameRepository.findByName(gameName);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(game.getName(), gameName);
        Assertions.assertEquals(game.getStatus(), Status.READY);
    }

    @Test
    public void givenGameRepository_whenSaveAndDeleteGameById_thenOK() {
        String gameName = "game name for deletion";
        Game game = gameRepository.save(buildGameObject(gameName, Status.READY));
        gameRepository.delete(game);
        Optional<Game> gameOptional = gameRepository.findById(game.getId());
        Assertions.assertTrue(!gameOptional.isPresent());
    }

    @Test
    public void givenGameRepository_whenSaveMultiGamesAndFindAll_thenOK() {
        String gameName_1="game Name 1";
        gameRepository.save(buildGameObject(gameName_1, Status.READY));
        String gameName_2 = "game Name 2";
        gameRepository.save(buildGameObject(gameName_2, Status.READY));
        List<Game> games = gameRepository.findAll();
        Assertions.assertEquals(2, games.size());
    }

    private Game buildGameObject(String name, Status status) {
        Game game = new Game();
        game.setName(name);
        game.setStatus(status);
        return game;
    }
}
