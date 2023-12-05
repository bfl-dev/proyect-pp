package com.ballsteam.sokiduels.minigames;

public interface GameState {
    public void action(long timeEnd);
    public void result(long timeStart,Long timeEnd);
    public void closure(long timeEnd);
}
