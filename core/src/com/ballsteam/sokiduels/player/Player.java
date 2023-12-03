package com.ballsteam.sokiduels.player;

public class Player {
    private final boolean isPlayerOne;
    public PlayerInput Input = new PlayerInput() {
        @Override
        public void update() {
            super.update();
        }
    };
    public Player(boolean isPlayerOne){
        this.isPlayerOne = isPlayerOne;
    }
    public Player(boolean isPlayerOne, PlayerInput InputMethod){
        this.isPlayerOne = isPlayerOne;
        this.Input = InputMethod;
    }

    public void update(){Input.update();}

    public void setInput(PlayerInput input) {

        Input = input;
        Input.hasPlayer=true;
    }

    public boolean isPlayerOne() {
        return isPlayerOne;
    }
}
