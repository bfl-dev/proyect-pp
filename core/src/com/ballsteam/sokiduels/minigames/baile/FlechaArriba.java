package com.ballsteam.sokiduels.minigames.baile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class FlechaArriba{
    Sprite flechaArriba;
    Vector2 posicionArriba;
    public FlechaArriba() {
        flechaArriba = new Sprite(new Texture("flechaArriba.png"));
        posicionArriba = new Vector2(128, 480);
    }
    public void motion() {
        posicionArriba.y -= 200 * Gdx.graphics.getDeltaTime();
    }
    public void draw(SpriteBatch batch) {
        motion();
        flechaArriba.setPosition(posicionArriba.x, posicionArriba.y);
        flechaArriba.draw(batch);
    }
    public void dispose() {
        flechaArriba.getTexture().dispose();
    }
}
