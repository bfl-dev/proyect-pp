package com.ballsteam.sokiduels.minigames.baile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class FlechaIzquierda {
    Sprite flechaIzquierda;
    Vector2 posicionIzquierda;
    public FlechaIzquierda() {
        flechaIzquierda = new Sprite(new Texture("flechaIzquierda.png"));
        posicionIzquierda = new Vector2(0, 480);
    }
    public void motion() {
        posicionIzquierda.y -= 200 * Gdx.graphics.getDeltaTime();
    }
    public void draw(SpriteBatch batch) {
        motion();
        flechaIzquierda.setPosition(posicionIzquierda.x, posicionIzquierda.y);
        flechaIzquierda.draw(batch);
    }
    public void dispose() {
        flechaIzquierda.getTexture().dispose();
    }
}
