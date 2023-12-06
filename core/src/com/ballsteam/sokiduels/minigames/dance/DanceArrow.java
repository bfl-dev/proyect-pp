package com.ballsteam.sokiduels.minigames.dance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class DanceArrow {
    private final Sprite arrow;
    private final Vector2 position;
    public DanceArrow(Sprite sprite, Vector2 vector2) {
        arrow = sprite;
        position = vector2;
    }
    public void motion() {
        position.y -= 200 * Gdx.graphics.getDeltaTime();
    }
    public void draw(SpriteBatch batch){
        motion();
        arrow.setPosition(position.x, position.y);
        arrow.draw(batch);
    }
    public void dispose() {
        arrow.getTexture().dispose();
    }

    public Vector2 getPosition() {
        return position;
    }
}
