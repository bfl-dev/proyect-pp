package com.ballsteam.sokiduels.Screens;


import com.ballsteam.sokiduels.SokiDuels;

public class ConfigScreen extends AbstractScreen{
    public ConfigScreen(SokiDuels main) {
        super(main);
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        main.batch.end();
    }


}

