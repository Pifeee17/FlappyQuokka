package com.pife.juego;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PantallaNivelInfinito implements Screen {

    private Game game;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture fondoTex1;
    private Texture fondoTex2;

    private Texture personajeTex;
    private Sprite personaje;

    private float velocidad = 3f;        // velocidad personaje
    private float fondo1X;               // posición horizontal del primer fondo
    private float fondo2X;               // posición horizontal del segundo fondo
    private float velocidadFondo = 1.5f; // velocidad del fondo

    public PantallaNivelInfinito(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        // Fondos
        fondoTex1 = new Texture("FondoNivelesQuokky.png");
        fondoTex2 = new Texture("Fondo-Reves.png");

        // Inicializar posiciones
        fondo1X = 0;
        fondo2X = viewport.getWorldWidth();

        // Personaje
        personajeTex = new Texture("Personaje-Quokky.png");
        personaje = new Sprite(personajeTex);

        personaje.setSize(1.5f, 2f);
        personaje.setPosition(0.2f, (5f - 2f) / 2f);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // MOVIMIENTO PERSONAJE

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            personaje.setY(personaje.getY() + velocidad * delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            personaje.setY(personaje.getY() - velocidad * delta);
        }

        // Límites
        if (personaje.getY() < 0) {
            personaje.setY(0);
        }
        if (personaje.getY() + personaje.getHeight() > viewport.getWorldHeight()) {
            personaje.setY(viewport.getWorldHeight() - personaje.getHeight());
        }

        /* MOVIMIENTO FONDO */

        fondo1X = fondo1X - velocidadFondo * delta;
        fondo2X = fondo2X - velocidadFondo * delta;

        if (fondo1X + viewport.getWorldWidth() <= 0) {
            fondo1X = fondo2X + viewport.getWorldWidth();
        }

        if (fondo2X + viewport.getWorldWidth() <= 0) {
            fondo2X = fondo1X + viewport.getWorldWidth();
        }

        /*DIBUJO */

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float texRatio = (float) fondoTex1.getHeight() / fondoTex1.getWidth();
        float worldRatio = worldHeight / worldWidth;

        float drawWidth, drawHeight;

        if (texRatio > worldRatio) {
            drawWidth = worldWidth;
            drawHeight = worldWidth * texRatio;
        } else {
            drawHeight = worldHeight;
            drawWidth = worldHeight / texRatio;
        }

        float y = (worldHeight - drawHeight) / 2f;

        // Dibujamos los dos fondos en cadena
        batch.draw(fondoTex1, fondo1X, y, drawWidth, drawHeight);
        batch.draw(fondoTex2, fondo2X, y, drawWidth, drawHeight);

        // Personaje
        personaje.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        fondoTex1.dispose();
        fondoTex2.dispose();
        personajeTex.dispose();
    }
}
