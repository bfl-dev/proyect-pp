package com.ballsteam.sokiduels.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.ballsteam.sokiduels.minigames.Cachipun.CachipunScreen;
import com.ballsteam.sokiduels.player.ControllerInput;
import com.ballsteam.sokiduels.player.KeyboardInput;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.player.Player;
import com.ballsteam.sokiduels.player.PlayerInput;

import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;


import java.util.List;


public class PlayerSetScreen extends AbstractScreen {
    private final Label text = new Label("J1", new Skin(Gdx.files.internal("ui/uiskin.json")));
    Player player1;
    Player player2;
    ControllerInput device1;
    ControllerInput device2;
    KeyboardInput keyboardInput;
    KeyboardInput keyboardInput2;
    List<PlayerInput> inputs;

    public PlayerSetScreen(SokiDuels main) throws XInputNotLoadedException {
        super(main);

        player1 = new Player(true);
        player2 = new Player(false);

        device1 = new ControllerInput(0);
        device2 = new ControllerInput(1);

        keyboardInput = new KeyboardInput(true);
        keyboardInput2 = new KeyboardInput(false);
        inputs = List.of(new PlayerInput[]{keyboardInput,keyboardInput2,device1,device2});

    }

    @Override
    public void buildStage() {
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        text.setPosition(getWidth() / 2f, 440, Align.center);
        addActor(text);
        inputs.forEach(PlayerInput::update);
        if (player1.Input.getClass()!=ControllerInput.class
            && player1.Input.getClass()!=KeyboardInput.class){
            if (inputs.stream().anyMatch(PlayerInput::interacted)){
                player1.setInput(inputs.stream().filter(PlayerInput::interacted).findFirst().get());
                text.setText("J2");
            }
        } else if (player2.Input.getClass()!=ControllerInput.class
            && player2.Input.getClass()!=KeyboardInput.class){
            if (inputs.stream().anyMatch(PlayerInput::interacted)){
                player2.setInput(inputs.stream().filter(PlayerInput::interacted).findFirst().get());
                main.setScreen(new MenuScreen(player1, player2, main));
            }
        }
    }

}
