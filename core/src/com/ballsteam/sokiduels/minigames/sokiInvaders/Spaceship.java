package com.ballsteam.sokiduels.minigames.sokiInvaders;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;



public class Spaceship {
    public Boolean LEFT;
    public Boolean RIGHT;
    public Boolean SHOOT;
    Sprite shipSprite;
    Vector2 spaceShip;
    Bullet bullet;

    public Spaceship() {
        // Inicialización de la nave espacial, su sprite y la bala asociada.
        shipSprite = new Sprite(new Texture("spaceship.png"));
        spaceShip = new Vector2();
        spaceShip.x = (float) 800 / 2 - (float) 64 / 2;
        spaceShip.y = 20;
        bullet = new Bullet();
    }

    public void motion() {
        // Lógica de movimiento de la nave y disparo de la bala.
        if (RIGHT) spaceShip.x += 300 * Gdx.graphics.getDeltaTime();
        if (LEFT) spaceShip.x -= 300 * Gdx.graphics.getDeltaTime();
        bullet.disparar(spaceShip.x, SHOOT);
        if (spaceShip.x > 800 - 64) spaceShip.x = 800 - 64;
        if (spaceShip.x < 0) spaceShip.x = 0;
    }

    public void draw(SpriteBatch batch) {
        // Método para dibujar la nave y la bala.
        motion();
        shipSprite.setPosition(spaceShip.x, spaceShip.y);
        shipSprite.draw(batch);
        bullet.draw(batch);
    }
}
