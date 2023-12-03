package com.ballsteam.sokiduels.player;

public abstract class PlayerInput {
    public boolean hasPlayer = false;
    public float LEFT_RIGHT;
    public float UP_DOWN;
    public boolean A;
    public boolean B;
    public void update(){}
    public boolean interacted(){return false;}
}
