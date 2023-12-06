package com.ballsteam.sokiduels.interfaces;

public interface GameState {
    void action(long timeEnd);
    void result(long timeStart, Long timeEnd);
    void closure(long timeEnd);
}
