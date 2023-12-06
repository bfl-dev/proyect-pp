package com.ballsteam.sokiduels.minigames.Cachipun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class Duelist {
    public int score;
    public int health;
    public int healthDistance;
    public boolean winner;
    private final Sprite bar;
    private final HashMap<String,Sprite> textures;
    public Sprite playerAction;
    private final boolean isPlayerOne;
    public boolean random;
    public final int [] loads;
    private final Sprite loadAttack;
    private final Sprite loadDance;
    private final Sprite loadDefend;
    public Duelist(boolean isPlayerOne){
        this.score = 0;
        this.isPlayerOne = isPlayerOne;
        this.textures = new HashMap<>();
        this.health = 300;
        this.healthDistance = 300;
        this.random = false;
        loads = new int[]{1,1,1};
        loadAttack = new Sprite(new Texture("cachipun/sword.png"));
        loadDance = new Sprite(new Texture("cachipun/jojo.png"));
        loadDefend = new Sprite(new Texture("cachipun/shield.png"));
        poblarHashmap();
        this.bar = new Sprite(new Texture("cachipun/green.png"));
        this.playerAction = new Sprite(new Texture("cachipun/ControllerCachipun.png"));
    }

    public void draw(SpriteBatch batch, float width, float height){
        if (isPlayerOne) {
            bar.setPosition((width/3)-125, (height-100));
            playerAction.setPosition((width/3)-170,height-700);
            loadAttack.setPosition((width/3)-130, (height-180));
            loadDance.setPosition((width/3)-110, (height-180));
            loadDefend.setPosition((width/3)-80, (height-180));
        } else {
            bar.setPosition((((width/3)*2)-125), (height-100));
            playerAction.setPosition(((width/3)*2)-170,height-700);
            loadAttack.setPosition(((width/3)*2)-130, (height-180));
            loadDance.setPosition(((width/3)*2)-110, (height-180));
            loadDefend.setPosition(((width/3)*2)-80, (height-180));
        }
        if(health < 75) {
            bar.setTexture(new Texture("cachipun/red.png"));
        }else if(health < 150) {
            bar.setTexture(new Texture("cachipun/yellow.png"));
        }
        batch.draw(bar, bar.getX(), bar.getY(), health, 20);
        batch.draw(playerAction, playerAction.getX(), playerAction.getY());
        batch.draw(loadAttack, loadAttack.getX(), loadAttack.getY(),32,32);
        batch.draw(loadDance, loadDance.getX(), loadDance.getY(),32,32);
        batch.draw(loadDefend, loadDefend.getX(), loadDefend.getY(),32,32);
    }
    public void addScore() {
        this.score += 10;
    }
    public void subtractScore(){
        if (this.score > 0){
            this.score -= 10;
        }
    }

    public boolean isPlayerOne() {
        return isPlayerOne;
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
