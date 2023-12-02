package com.ballsteam.sokiduels.minigames.baile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class FlechaAbajo {
    private final Sprite flechaAbajo;
    private final Vector2 posicionAbajo;
    public FlechaAbajo() {
        flechaAbajo = new Sprite(new Texture("flechaAbajo.png"));
        posicionAbajo = new Vector2(64, 480);
    }
    public void motion() {
        posicionAbajo.y -= 200 * Gdx.graphics.getDeltaTime();
    }
    public void draw(SpriteBatch batch){
        motion();
        flechaAbajo.setPosition(posicionAbajo.x, posicionAbajo.y);
        flechaAbajo.draw(batch);
    }
    public void dispose() {
        flechaAbajo.getTexture().dispose();
    }

    public Vector2 getPosicionAbajo() {
        return posicionAbajo;
    }
}
