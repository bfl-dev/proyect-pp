package com.ballsteam.sokiduels.minigames.Cachipun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.HashMap;
import java.util.stream.IntStream;

public class Duelist {
    public int score;
    public int health;
    public boolean winner;
    private final Sprite bar;
    private HashMap<String,Sprite> textures;
    public Sprite playerAction;
    private final boolean isPlayerOne;
    public Duelist(boolean isPlayerOne){
        this.score = 0;
        this.isPlayerOne = isPlayerOne;
        this.textures = new HashMap<>();
        this.health = 300;
        poblarHashmap();
        this.bar = new Sprite(new Texture("green.png"));
        this.playerAction = new Sprite(new Texture("ControllerCachipun.png"));
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
    public void damage(int damage){
        IntStream.range(0, damage).forEach(i -> {
            health--;
        });
    }
    private void poblarHashmap(){
        textures.put("KeyboardCachipun",new Sprite(new Texture("KeyboardCachipun.png")));
        textures.put("ControllerCachipun",new Sprite(new Texture("ControllerCachipun.png")));
        textures.put("Sword",new Sprite(new Texture("sword.png")));
        textures.put("Shield",new Sprite(new Texture("shield.png")));
        textures.put("Dance",new Sprite(new Texture("jojo.png")));
        textures.put("Ready",new Sprite(new Texture("ready.png")));
    }
    public void setDuelistAction(String texture){
        playerAction = textures.get(texture);
    }
}
