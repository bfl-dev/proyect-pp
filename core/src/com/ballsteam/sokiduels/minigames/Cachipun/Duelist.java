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
    private final HashMap<String,Sprite> textures;
    public Sprite playerAction;
    private final boolean isPlayerOne;
    public boolean random;
    public int [] loads;
    public Duelist(boolean isPlayerOne){
        this.score = 0;
        this.isPlayerOne = isPlayerOne;
        this.textures = new HashMap<>();
        this.health = 300;
        this.random = false;
        loads = new int[]{1,1,1};
        poblarHashmap();
        this.bar = new Sprite(new Texture("cachipun/green.png"));
        this.playerAction = new Sprite(new Texture("cachipun/ControllerCachipun.png"));
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
            bar.setTexture(new Texture("cachipun/red.png"));
        }else if(health < 50) {
            bar.setTexture(new Texture("cachipun/yellow.png"));
        }
        batch.draw(bar, bar.getX(), bar.getY(), health, 20);
        batch.draw(playerAction, playerAction.getX(), playerAction.getY());
    }
    public void damage(int damage){
        IntStream.range(0, damage).forEach(i -> health--);
    }
    private void poblarHashmap(){
        textures.put("KeyboardCachipun",new Sprite(new Texture("cachipun/KeyboardCachipun.png")));
        textures.put("ControllerCachipun",new Sprite(new Texture("cachipun/ControllerCachipun.png")));
        textures.put("Sword",new Sprite(new Texture("cachipun/sword.png")));
        textures.put("Shield",new Sprite(new Texture("cachipun/shield.png")));
        textures.put("Dance",new Sprite(new Texture("cachipun/jojo.png")));
        textures.put("Ready",new Sprite(new Texture("cachipun/ready.png")));
    }
    public void setDuelistAction(String texture){
        playerAction = textures.get(texture);
    }
    public void addLoad(int i){
        loads[i]++;
    }
    public void subtractLoad(int i){
        if (loads[i] > 1) loads[i]--;
    }
}
