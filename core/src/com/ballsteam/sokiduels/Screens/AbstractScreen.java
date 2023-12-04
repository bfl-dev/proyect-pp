package com.ballsteam.sokiduels.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ballsteam.sokiduels.SokiDuels;

public abstract class AbstractScreen extends Stage implements Screen { //TODO: REVIEW THIS SHIT
    protected SokiDuels main;
    public AbstractScreen(SokiDuels main) {
        this.main = main;
    }
    public abstract void buildStage();
    @Override
    public void show() {
        buildStage();
        Gdx.input.setInputProcessor(this);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        super.act(delta);
        super.draw();
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
