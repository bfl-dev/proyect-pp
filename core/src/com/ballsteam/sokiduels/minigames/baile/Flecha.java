package com.ballsteam.sokiduels.minigames.baile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Flecha { //TODO: RENAME ALL THE VARIABLES AND METHODS TO ENGLISH
    private final Sprite flecha;
    private final Vector2 position;
    public Flecha(Sprite sprite, Vector2 vector2) {
        flecha = sprite;
        position = vector2;
    }
    public void motion() {
        position.y -= 200 * Gdx.graphics.getDeltaTime();
    }
    public void draw(SpriteBatch batch){
        motion();
        flecha.setPosition(position.x, position.y);
        flecha.draw(batch);
    }
    public void dispose() {
        flecha.getTexture().dispose();
    }

    public Vector2 getPosition() {
        return position;
    }
}
