package com.ballsteam.sokiduels.minigames.sokiDefense;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ballsteam.sokiduels.minigames.Cachipun.Duelist;

public class Shield {
    Sprite shieldSprite;
    Vector2 posEscudo;
    Texture texture;
    Texture texture2;
    Texture texture3;
    protected int UP_DOWN;
    protected int LEFT_RIGHT;
    private final Duelist duelist;
    int damage;
    public Shield(Texture Texture, Texture Texture2, Texture Texture3, Duelist duelist){
        this.texture = Texture;
        this.texture2 = Texture2;
        this.texture3 = Texture3;
        this.duelist = duelist;
        shieldSprite = new Sprite(texture);
        shieldSprite.setSize(45,45);
        posEscudo = new Vector2(0,0);
        damage = 1;
    }
    public void motion(){
        posEscudo.add(new Vector2(LEFT_RIGHT*4,UP_DOWN*4).clamp(0,4));
        if (posEscudo.x > 1366 - shieldSprite.getWidth()) posEscudo.x = 1366 - shieldSprite.getWidth();
        if (posEscudo.x < 0) posEscudo.x = 0;
        if (posEscudo.y > 768 - shieldSprite.getWidth()) posEscudo.y = 768 - shieldSprite.getWidth();
        if (posEscudo.y < 0) posEscudo.y = 0;
    }
    public void draw (SpriteBatch batch){
        motion();
        status();
        shieldSprite.setPosition(posEscudo.x,posEscudo.y);
        shieldSprite.draw(batch);
    }
    public void status(){
        switch (damage) {
            case 1 -> shieldSprite.setTexture(texture);
            case 2 -> shieldSprite.setTexture(texture2);
            case 3 -> shieldSprite.setTexture(texture3);
        }
    }
    public void addDamage(){
        damage++;
        if(damage > 3){
            damage = 1;
            duelist.subtractScore();
        }
    }
    public void dispose(){
        shieldSprite.getTexture().dispose();
    }
}
