package com.ballsteam.sokiduels.minigames.baile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class FlechaDerecha {
    Sprite flechaDerecha;
    Vector2 posicionDerecha;
    public FlechaDerecha() {
        flechaDerecha = new Sprite(new Texture("flechaDerecha.png"));
        posicionDerecha = new Vector2(192, 480);
    }
    public void motion() {
        posicionDerecha.y -= 200 * Gdx.graphics.getDeltaTime();
    }
    public void draw(SpriteBatch batch) {
        motion();
        flechaDerecha.setPosition(posicionDerecha.x, posicionDerecha.y);
        flechaDerecha.draw(batch);
    }
    public void dispose() {
        flechaDerecha.getTexture().dispose();
    }
}
