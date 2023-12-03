package com.ballsteam.sokiduels.minigames.sokiDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Escudo {
    Sprite escudoSprite;
    Vector2 posEscudo;
    Texture texture;
    Texture texture2;
    Texture texture3;
    protected boolean UP;
    protected boolean DOWN;
    protected boolean LEFT;
    protected boolean RIGHT;
    int damage;
    int score;
    public Escudo (Texture Texture,Texture Texture2,Texture Texture3){
        this.texture = Texture;
        this.texture2 = Texture2;
        this.texture3 = Texture3;
        escudoSprite = new Sprite(texture);
        escudoSprite.setSize(32,32);
        posEscudo = new Vector2(0,0);
        damage = 1;
    }
    public void motion(){
        if (UP) posEscudo.y += 300 * Gdx.graphics.getDeltaTime();
        if (DOWN) posEscudo.y -= 300 * Gdx.graphics.getDeltaTime();
        if (RIGHT) posEscudo.x += 300 * Gdx.graphics.getDeltaTime();
        if (LEFT) posEscudo.x -= 300 * Gdx.graphics.getDeltaTime();

        if (posEscudo.x > 800 - escudoSprite.getWidth()) posEscudo.x = 800 - escudoSprite.getWidth();
        if (posEscudo.x < 0) posEscudo.x = 0;
        if (posEscudo.y > 480 - escudoSprite.getWidth()) posEscudo.y = 480 - escudoSprite.getWidth();
        if (posEscudo.y < 0) posEscudo.y = 0;
    }
    public void draw (SpriteBatch batch){
        motion();
        status();
        escudoSprite.setPosition(posEscudo.x,posEscudo.y);
        escudoSprite.draw(batch);
    }
    public void status(){
        switch (damage) {
            case 1 -> escudoSprite.setTexture(texture);
            case 2 -> escudoSprite.setTexture(texture2);
            case 3 -> escudoSprite.setTexture(texture3);
        }
    }
    public void addDamage(){
        damage++;
        if(damage > 3){
            damage = 1;
            score -= 10;
        }
    }
    public void dispose(){
        escudoSprite.getTexture().dispose();
    }
}
