package com.ballsteam.sokiduels.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class KeyboardInput extends PlayerInput {
    public KeyboardInput(){}
    @Override
    public void update(){
        setInputValues();
    }
    @Override
    public boolean interacted(){
        return key(Keys.ANY_KEY);
    }
    private void setInputValues(){
        LEFT_RIGHT = valueDeltaX();
        UP_DOWN = valueDeltaY();
        A = key(Keys.SPACE);
        B = key(Keys.B);
    }
    private boolean key(int keyValue){
        return Gdx.input.isKeyPressed(keyValue);
    }
    private int valueDeltaX(){
        return Boolean.compare((key(Keys.RIGHT)||key(Keys.D)),key(Keys.LEFT)||key(Keys.A));
    }
    private int valueDeltaY(){return Boolean.compare((key(Keys.UP)||key(Keys.W)),key(Keys.DOWN)||key(Keys.S));}
}
