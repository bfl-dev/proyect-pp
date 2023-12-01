package com.ballsteam.sokiduels.minigames.sokiInvaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Alien {
    Sprite alienSprite;
    Vector2 posAlien;
    public Alien (Vector2 vector2){
        alienSprite = new Sprite(new Texture("soki.png"));
        alienSprite.setScale(0.5f);
        posAlien = vector2;
    }
    public void draw (SpriteBatch a){
        alienSprite.setPosition(posAlien.x, posAlien.y);
        alienSprite.draw(a);
    }
}
