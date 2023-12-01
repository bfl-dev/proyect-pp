package com.ballsteam.sokiduels.player;

import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    public PlayerInput Input = new PlayerInput() {
        @Override
        public void update() {
            super.update();
        }
    };
    public Player(){}
    public Player(PlayerInput InputMethod){
        this.Input = InputMethod;
    }
    public void update(){Input.update();}

    public void setInput(PlayerInput input) {
        Input = input;
    }
}
