package com.ballsteam.sokiduels.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.player.ControllerInput;
import com.ballsteam.sokiduels.player.KeyboardInput;
import com.ballsteam.sokiduels.player.Player;
import com.ballsteam.sokiduels.player.PlayerInput;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;

import java.util.List;


@SuppressWarnings({"OptionalGetWithoutIsPresent", "FieldCanBeLocal"})
public class PlayerSetScreen extends AbstractScreen {
    private final Label text = new Label("Player 1", new Skin(Gdx.files.internal("ui/uiskin.json")));
    private final Label text2 = new Label("Press Any Button...", new Skin(Gdx.files.internal("ui/uiskin.json")));
    final Player player1;
    final Player player2;
    private final ControllerInput device1;
    private final ControllerInput device2;
    private final KeyboardInput keyboardInput;
    private final KeyboardInput keyboardInput2;
    private final List<PlayerInput> inputs;

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
        text.setPosition(getWidth() / 2f, (getHeight()/2)+5, Align.center);
        addActor(text);
        text2.setPosition(getWidth()/2f, text.getY()-10, Align.center);
        addActor(text2);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        inputs.forEach(PlayerInput::update);
        if (isPlayerInputDefined(player1) && (inputs.stream().anyMatch(PlayerInput::interacted))){
            player1.setInput(inputs.stream().filter(PlayerInput::interacted).findFirst().get());
            text.setText("Player 2");
        } else if (isPlayerInputDefined(player2) && (inputs.stream().anyMatch(PlayerInput::interacted))){
            player2.setInput(inputs.stream().filter(PlayerInput::interacted).findFirst().get());
            main.setScreen(new MenuScreen(player1, player2, main));
        }
    }

    private boolean isPlayerInputDefined(Player player) {
        return player.Input.getClass() != ControllerInput.class && player.Input.getClass() != KeyboardInput.class;
    }

}
