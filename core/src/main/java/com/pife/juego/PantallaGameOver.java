package com.pife.juego;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaGameOver implements Screen {

    private Game game;
    private SpriteBatch batch;
    private Texture botonReintentarTex;

    public PantallaGameOver(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        botonReintentarTex = new Texture("PantallaGameOver.png");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        batch.draw(botonReintentarTex, 100, 100); // Ajusta posición y tamaño
        batch.end();

        // Reiniciar juego si se toca la pantalla
        if (Gdx.input.justTouched()) {
            game.setScreen(new PantallaNivelInfinito(game));
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        botonReintentarTex.dispose();
    }
}
