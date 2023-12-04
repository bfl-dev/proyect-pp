package com.ballsteam.sokiduels.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private final boolean isPlayerOne;
    private int health;
    private Sprite bar;
    Sprite playerAction;
    public PlayerInput Input = new PlayerInput() {
        @Override
        public void update() {
            super.update();
        }
    };
    public Player(boolean isPlayerOne){
        this.isPlayerOne = isPlayerOne;
        this.health = 300;
        this.bar = new Sprite(new Texture("green.png"));
        playerAction = new Sprite(new Texture("KeyboardCachipun.png"));
    }
    public Player(boolean isPlayerOne, PlayerInput InputMethod){
        this.isPlayerOne = isPlayerOne;
        this.Input = InputMethod;
    }

    public void setInput(PlayerInput input) {

        Input = input;
        Input.hasPlayer=true;
    }

    public boolean isPlayerOne() {
        return isPlayerOne;
    }
    public void draw(SpriteBatch batch, float width, float height){
        if (isPlayerOne) {
            bar.setPosition((width/3)-125, (height-100));
            playerAction.setPosition((width/3)-175, height-700);
        } else {
            bar.setPosition((((width/3)*2)-125), (height-100));
            playerAction.setPosition(((width/3)*2)-175, height-700);
        }
        if(health < 25) {
            bar.setTexture(new Texture("red.png"));
        }else if(health < 50) {
            bar.setTexture(new Texture("yellow.png"));
        }
        batch.draw(bar, bar.getX(), bar.getY(), health, 20);
        batch.draw(playerAction, playerAction.getX(), playerAction.getY());
    }
    public void setPlayerAction(Texture texture){
        this.playerAction.setTexture(texture);
    }
}
