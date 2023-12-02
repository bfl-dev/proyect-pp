package com.ballsteam.sokiduels.minigames.sokiInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.ballsteam.sokiduels.Screens.Screens;
import com.ballsteam.sokiduels.player.Player;
import com.ballsteam.sokiduels.SokiDuels;

import java.util.stream.IntStream;


public class sokiInvadersScreen extends AbstractScreen {
    Spaceship spaceShip;
    Bullet bullet;
    Sprite fondo;
    Array<Alien> aliens;
    Player J1;
    int direccion;
    public sokiInvadersScreen(SokiDuels main, Player J1) {
        super(main);
        this.J1 = J1;
        spaceShip = new Spaceship();
        bullet = new Bullet();
        aliens = new Array<>();
        direccion = 1;
        fondo = new Sprite(new Texture("fondo.png"));
        crearMatriz(aliens);
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        J1.update();
        updateSpaceship();
        main.batch.begin();
        fondo.draw(main.batch);
        spaceShip.draw(main.batch);
        aliens.forEach(alien -> {
            alien.draw(main.batch);
            alien.posAlien.x += 100 * Gdx.graphics.getDeltaTime() * direccion;
            if (alien.posAlien.x >= Gdx.graphics.getWidth() - alien.alienSprite.getWidth() || alien.posAlien.x <= 0) {
                direccion = (alien.posAlien.x >= Gdx.graphics.getWidth() - alien.alienSprite.getWidth()) ? -1 : 1;
                IntStream.range(0, aliens.size).forEach(j -> aliens.get(j).posAlien.y -= 10);
            }

            if (spaceShip.bullet.bulletSprite.getBoundingRectangle().overlaps(alien.alienSprite.getBoundingRectangle())) {
                spaceShip.bullet.bullet.y = 10000;
                aliens.removeValue(alien, true);
            }

            if (alien.alienSprite.getBoundingRectangle().overlaps(spaceShip.shipSprite.getBoundingRectangle())) {
                main.setScreen(Screens.MENUSCREEN); // Aquí debería ir un GameOver
            }

            if (aliens.isEmpty()) {
                main.setScreen(Screens.MENUSCREEN);
            }
        });
        main.batch.end();
    }

    public static void crearMatriz(Array<Alien> aliens){
        IntStream.range(0, 5).forEach(i -> IntStream.range(0, 10).forEach(j -> aliens.add(new Alien(new Vector2(120 + (j * 57), 180 + (i * 57))))));
    }

    private void updateSpaceship(){
        spaceShip.RIGHT = J1.Input.LEFT_RIGHT==1;
        spaceShip.LEFT = J1.Input.LEFT_RIGHT==-1;
        spaceShip.SHOOT = J1.Input.A;
    }
}
