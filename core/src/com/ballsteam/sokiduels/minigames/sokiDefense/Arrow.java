package com.ballsteam.sokiduels.minigames.sokiDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Arrow {
    final Sprite bulletSprite;
    final Vector2 posBullet;
    final int direction;

    public Arrow(Vector2 vector2, int direction) {
        // Inicialización de la bala y su sprite.
        bulletSprite = new Sprite(new Texture(Gdx.files.internal("sokidefense/arrow.png")));
        bulletSprite.setScale(0.5f);
        bulletSprite.rotate(90* direction);
        this.posBullet = vector2;
        this.direction = direction;
    }

    public void motion(int direction) {
        switch (direction) {
            case 0 -> posBullet.y += Gdx.graphics.getDeltaTime() * 200;
            case 1 -> posBullet.x -= Gdx.graphics.getDeltaTime() * 200;
            case 2 -> posBullet.y -= Gdx.graphics.getDeltaTime() * 200;
            case 3 -> posBullet.x += Gdx.graphics.getDeltaTime() * 200;
        }

    }

    public void draw(SpriteBatch batch) {
        // Método para dibujar la bala.
        motion(direction);
        bulletSprite.setPosition(posBullet.x, posBullet.y);
        bulletSprite.draw(batch);
    }
}
