package com.ballsteam.sokiduels.minigames.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Spaceship {
    Sprite shipSprite;
    Vector2 spaceShip;
    Array<Bullet>  bullets;
    int score;
    long lastShot;

    public Spaceship() {
        // Inicialización de la nave espacial, su sprite y la bala asociada.
        shipSprite = new Sprite(new Texture("spaceship.png"));
        spaceShip = new Vector2();
        spaceShip.x = 800 / 2 - 64 / 2;
        spaceShip.y = 20;
        bullets = new Array<>();
        lastShot = TimeUtils.nanoTime();
        score = 0;
    }

    public void motion() {
        // Lógica de movimiento de la nave y disparo de la bala.
        if (Gdx.input.isKeyPressed(Keys.D)) spaceShip.x += 400 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.A)) spaceShip.x -= 400 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.W) && TimeUtils.nanoTime() - lastShot > 500000000) {
            shoot();
            lastShot = TimeUtils.nanoTime();
        }
        bullets.forEach(bullet -> {
            if (bullet.bullet.y > 480) bullets.removeValue(bullet, true);
        });
        if (spaceShip.x > 800 - 64) spaceShip.x = 800 - 64;
        if (spaceShip.x < 0) spaceShip.x = 0;
    }

    public void draw(SpriteBatch batch) {
        // Método para dibujar la nave y la bala.
        motion();
        shipSprite.setPosition(spaceShip.x, spaceShip.y);
        shipSprite.draw(batch);
        bullets.forEach(bullet -> bullet.draw(batch));
    }
    public void shoot() {
        Bullet bullet = new Bullet();
        bullet.bullet.x = spaceShip.x + 2;
        bullet.bullet.y = spaceShip.y + 32;
        bullets.add(bullet);
    }
    public void dispose() {
        shipSprite.getTexture().dispose();
        bullets.forEach(bullet -> bullet.bulletSprite.getTexture().dispose());
    }
}
