package com.ballsteam.sokiduels.minigames.sokiDefense;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Flag {
    final Sprite flagSprite;
    final Vector2 vector2;
    public Flag (Vector2 vector2,String texture){
        flagSprite = new Sprite(new Texture(texture));
        flagSprite.setSize(35,35);
        this.vector2 = vector2;
    }
    public void draw (SpriteBatch batch){
        flagSprite.setPosition(vector2.x,vector2.y);
        flagSprite.draw(batch);
    }
    public void dispose(){
        flagSprite.getTexture().dispose();
    }
}
