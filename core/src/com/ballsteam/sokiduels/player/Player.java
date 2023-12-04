package com.ballsteam.sokiduels.player;

public class Player {
    public boolean leftFuse = true;
    public boolean downFuse = true;
    public boolean upFuse = true;
    public boolean rightFuse = true;

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

    public void setInput(PlayerInput input) {

        Input = input;
        Input.hasPlayer=true;
    }

    public boolean isPlayerOne() {
        return isPlayerOne;
    }
    public void danceFuses(){
        leftFuse = Input.LEFT!=1;
        downFuse = Input.DOWN!=1;
        upFuse = Input.UP!=1;
        rightFuse = Input.RIGHT!=1;
    }
}
