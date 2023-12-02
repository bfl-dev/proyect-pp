package com.ballsteam.sokiduels.minigames.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    Vector2 bullet;
    Sprite bulletSprite;

    public Bullet() {
        // Inicialización de la bala y su sprite.
        bulletSprite = new Sprite(new Texture(Gdx.files.internal("laserBullet.png")));
        bulletSprite.setScale(0.5f);
        bullet = new Vector2();
        bullet.x = 0;
        bullet.y = 10000;
    }

    public void motion(float space) {
        bullet.y += Gdx.graphics.getDeltaTime() * 400;
    }

    public void draw(SpriteBatch batch) {
        // Método para dibujar la bala.
        bulletSprite.setPosition(bullet.x, bullet.y);
        bulletSprite.draw(batch);
        motion(bullet.y);
    }
}
