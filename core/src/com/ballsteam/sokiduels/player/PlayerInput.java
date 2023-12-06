package com.ballsteam.sokiduels.player;

public abstract class PlayerInput {
    public boolean hasPlayer = false;
    public float LEFT;
    public float RIGHT;
    public float UP;
    public float DOWN;
    @SuppressWarnings("unused")
    public boolean A;
    public boolean B;
    public void update(){}
    public boolean interacted(){return false;}

}
