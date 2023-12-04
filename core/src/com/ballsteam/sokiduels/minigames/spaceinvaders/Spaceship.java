package com.ballsteam.sokiduels.minigames.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Spaceship {
    protected Sprite shipSprite;
    private final Vector2 spaceShip;
    Array<Bullet>  bullets;
    boolean isPlayerOne;
    protected boolean LEFT;
    protected boolean RIGHT;
    boolean SHOOT;
    int score;
    long lastShot;

    public Spaceship(boolean isPlayerOne) {
        // Inicialización de la nave espacial, su sprite y la bala asociada.
        shipSprite = new Sprite(new Texture("sokiInvaders/spaceship.png"));
        spaceShip = new Vector2();
        spaceShip.x = (float) 1366 / 2 - (float) 64 / 2;
        this.isPlayerOne = isPlayerOne;
        if (isPlayerOne) {
            spaceShip.y = 20;
        } else {
            spaceShip.y = 688;
            shipSprite.rotate(180);
        }
        bullets = new Array<>();
        lastShot = TimeUtils.nanoTime();
        score = 0;
    }

    public void motion() {
        // Lógica de movimiento de la nave y disparo de la bala.
        if (LEFT) spaceShip.x -= 400 * Gdx.graphics.getDeltaTime();
        if (RIGHT) spaceShip.x += 400 * Gdx.graphics.getDeltaTime();
        if (SHOOT && TimeUtils.nanoTime() - lastShot > 500000000) {
            shoot();
            lastShot = TimeUtils.nanoTime();
        }
        bullets.forEach(bullet -> {
            if (bullet.bullet.y > 1366) bullets.removeValue(bullet, true);
        });
        if (spaceShip.x > 1366 - 64) spaceShip.x = 1366 - 64;
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
        Bullet bullet = new Bullet(isPlayerOne);
        bullet.bullet.x = spaceShip.x + 2;
        if (isPlayerOne) bullet.bullet.y = spaceShip.y + 32;
        else bullet.bullet.y = spaceShip.y - 32;
        bullets.add(bullet);
    }
    public void dispose() {
        shipSprite.getTexture().dispose();
        bullets.forEach(bullet -> bullet.bulletSprite.getTexture().dispose());
    }
}
