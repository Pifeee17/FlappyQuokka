package com.pife.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PantallaSeleccionModos implements Screen {

    private Main game;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture fondo;
    private Texture txtBoton;
    private Sprite btnArcade, btnNiveles;
    private BitmapFont font;

    public PantallaSeleccionModos(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(5, 8);

        fondo = new Texture("Fondo-Select-Modos.png");
        txtBoton = new Texture("Boton.png");

        btnArcade = new Sprite(txtBoton);
        btnNiveles = new Sprite(txtBoton);

        btnArcade.setBounds(1.5f, 5f, 2f, 1f);
        btnNiveles.setBounds(1.5f, 4f, 2f, 1f);

        font = new BitmapFont();
        font.getData().setScale(0.03f);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(fondo, 0, 0, 5, 8);
        btnArcade.draw(batch);
        btnNiveles.draw(batch);

        font.draw(batch, "MODO ARCADE", btnArcade.getX() + 0.25f, btnArcade.getY() + 0.65f);
        font.draw(batch, "MODO POR NIVELES", btnNiveles.getX() + 0.05f, btnNiveles.getY() + 0.65f);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnArcade.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("MODO", "ARCADE");
                game.setScreen(new PantallaNivelInfinito(game));
            }

            if (btnNiveles.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("MODO", "POR NIVELES");
                // game.setScreen(new PantallaNiveles(game));
            }
        }
    }

    @Override public void resize(int width, int height) { viewport.update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        fondo.dispose();
        txtBoton.dispose();
        font.dispose();
    }
}
