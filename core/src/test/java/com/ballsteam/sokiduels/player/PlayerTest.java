package com.ballsteam.sokiduels.player;

import com.ballsteam.sokiduels.exceptions.NoPlayerOneException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player player;
    Player player2;
    KeyboardInput playerInput;
    @BeforeEach
    void setUp(){
        player = new Player(true);
    }
    @Test
    void onePlayerOne(){
        player2 = new Player(false);
        assertTrue(player.onePlayerOne(player2));
    }
    @Test
    void fuse(){
        playerInput = new KeyboardInput(true);
        player.setInput(playerInput);
        player.Input.UP = 1;
        player.danceFuses();
        assertFalse(player.upFuse);
    }
    @Test
    void exceptionThrow(){
        player2 = new Player(true);
        RuntimeException exception = assertThrows(NoPlayerOneException.class, ()->{
            player.onePlayerOne(player2);
            });
        String expected = "No or Multiple player 1 detected";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }
}
