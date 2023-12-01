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
        crearMatriz(aliens,10,5);
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
        for (Alien alien : aliens) {
            alien.draw(main.batch);
            alien.posAlien.x += 100* Gdx.graphics.getDeltaTime()*direccion;
            if (alien.posAlien.x >= Gdx.graphics.getWidth()-alien.alienSprite.getWidth()){
                direccion = -1;
                for (int j = 0; j < aliens.size; j++) {
                    aliens.get(j).posAlien.y -= 10;
                }
            }
            if (alien.posAlien.x <= 0){
                direccion = 1;
                for (int j = 0; j < aliens.size; j++) {
                    aliens.get(j).posAlien.y -= 10;
                }
            }
            if(spaceShip.bullet.bulletSprite.getBoundingRectangle().overlaps(alien.alienSprite.getBoundingRectangle())){
                spaceShip.bullet.bullet.y = 10000;
                aliens.removeValue(alien,true);
            }

            if (alien.alienSprite.getBoundingRectangle().overlaps(spaceShip.shipSprite.getBoundingRectangle())){
                main.setScreen(Screens.MENUSCREEN); // Aqui deberia ir un GameOver
            }

            if (aliens.isEmpty()) main.setScreen(Screens.MENUSCREEN);
        }
        main.batch.end();
    }

    public static void crearMatriz(Array<Alien> aliens, int ancho, int alto){
        for (int j = 0; j < ancho; j++) {
            for (int k = 0; k < alto; k++) {
                Vector2 posicion = new Vector2((j*57)+120,(k*57)+180);
                aliens.add(new Alien(posicion));
            }
        }
    }

    private void updateSpaceship(){
        spaceShip.RIGHT = J1.Input.LEFT_RIGHT==1;
        spaceShip.LEFT = J1.Input.LEFT_RIGHT==-1;
        spaceShip.SHOOT = J1.Input.A;
    }
}
