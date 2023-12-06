package com.ballsteam.sokiduels.player;

import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerInputTest {
    ControllerInput device;

    @BeforeEach
    void setDevice() throws XInputNotLoadedException {
        device = new ControllerInput(0);
        device.LT = true;
        device.RIGHT = 1;

    }
    @Test
    void update() {
        device.update();
        assertEquals(0, device.RIGHT);
    }
    @Test
    void trigger(){
        device.update();
        assertEquals(false, device.LT);
    }
}
