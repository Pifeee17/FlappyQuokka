package com.pife.juego;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PantallaGameOver implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Texture fondoGameOver;
    private Texture txtBoton;
    private FitViewport viewport;
    private Sprite btnReintentar, btnMenu;
    public PantallaGameOver(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        fondoGameOver = new Texture("PantallaGameOver.png");

        txtBoton = new Texture("Boton.png");

        // Mismo viewport que Main (5x8 unidades)
        viewport = new FitViewport(5, 8);

        btnMenu = new Sprite(txtBoton);
        btnReintentar = new Sprite(txtBoton);

        // Tamaño de botones
        float w = 3f;
        float h = 1f; // botón más pequeño para que quepan juntos

        float x = 1f;
        float yBase = 8f - 3.2f;

        btnReintentar.setBounds(x, yBase, w, h);
        btnMenu.setBounds(x, yBase - 0.85f, w, h);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        // Dibuja la pantalla completa igual que la portada
        batch.draw(fondoGameOver, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        btnReintentar.draw(batch);
        btnMenu.draw(batch);


        batch.end();

        // Reiniciar juego si se toca la pantalla
        // Detectar clicks
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnReintentar.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "REINTENTAR");
                game.setScreen(new PantallaNivelInfinito(game)); // Cambia de pantalla
            } else if (btnMenu.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "MENÚ");
                game.setScreen(new PantallaNivelInfinito(game));
            }
        }
    }

    @Override public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        fondoGameOver.dispose();
        btnMenu.getTexture().dispose();
        btnReintentar.getTexture().dispose();
    }
}
