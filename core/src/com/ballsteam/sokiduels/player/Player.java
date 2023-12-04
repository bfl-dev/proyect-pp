package com.ballsteam.sokiduels.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    public boolean leftFuse = true;
    public boolean downFuse = true;
    public boolean upFuse = true;
    public boolean rightFuse = true;
    public int score;
    private final boolean isPlayerOne;
    protected int health;
    private final Sprite bar;

    public PlayerInput Input = new PlayerInput() {
        @Override
        public void update() {
            super.update();
        }
    };
    public Sprite playerAction;
    public Player(boolean isPlayerOne){
        this.score = 0;
        this.isPlayerOne = isPlayerOne;
        this.health = 300;
        this.bar = new Sprite(new Texture("green.png"));
        this.playerAction = new Sprite(new Texture("ControllerCachipun.png"));
    }

    public void setInput(PlayerInput input) {

        Input = input;
        Input.hasPlayer=true;
    }

    public boolean isPlayerOne() {
        return isPlayerOne;
    }
    public void danceFuses(){
        leftFuse = Input.LEFT!=1;
        downFuse = Input.DOWN!=1;
        upFuse = Input.UP!=1;
        rightFuse = Input.RIGHT!=1;
    }

     public void draw(SpriteBatch batch, float width, float height){
        if (isPlayerOne) {
            bar.setPosition((width/3)-125, (height-100));
            playerAction.setPosition((width/3)-110,height-700);
        } else {
            bar.setPosition((((width/3)*2)-125), (height-100));
            playerAction.setPosition(((width/3)*2)-110,height-700);
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
