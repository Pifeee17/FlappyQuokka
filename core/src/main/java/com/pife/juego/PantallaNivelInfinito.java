package com.pife.juego;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PantallaNivelInfinito implements Screen {

    private Game game;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture fondoTex;
    private Sprite fondo;

    private Texture personajeTex;
    private Sprite personaje;
    private float velocidad = 3f; // velocidad de movimiento vertical

    public PantallaNivelInfinito(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        // Fondo
        fondoTex = new Texture("FondoNivelesQuokky.png");
        fondo = new Sprite(fondoTex);

        // Escalar el fondo para llenar el viewport sin deformarse
        float scaleX = viewport.getWorldWidth() / fondo.getWidth();
        float scaleY = viewport.getWorldHeight() / fondo.getHeight();
        float scale = Math.max(scaleX, scaleY); // máximo para cubrir
        fondo.setSize(fondo.getWidth() * scale, fondo.getHeight() * scale);

        // Centrar el fondo
        fondo.setPosition(
            (viewport.getWorldWidth() - fondo.getWidth()) / 2f,
            (viewport.getWorldHeight() - fondo.getHeight()) / 2f
        );

        // Personaje
        personajeTex = new Texture("Personaje-Quokky.png");
        personaje = new Sprite(personajeTex);

        float personajeAncho = 1.5f;
        float personajeAlto = 2f;

        personaje.setSize(personajeAncho, personajeAlto);

        // Izquierda y centrado vertical
        float x = 0.2f;
        float y = (5f - personajeAlto) / 2f;

        personaje.setPosition(x, y);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // MOVIMIENTO del personaje
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
            personaje.setY(personaje.getY() + velocidad * delta);
                Gdx.app.log("INPUT", "ARRIBA");

        }

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            personaje.setY(personaje.getY() - velocidad * delta);
            Gdx.app.log("INPUT", "ABAJO");
        }

        // Limites para el movimiento
        if (personaje.getY() < 0) {
            personaje.setY(0);
        }

        if (personaje.getY() + personaje.getHeight() > viewport.getWorldHeight()) {
            personaje.setY(viewport.getWorldHeight() - personaje.getHeight());
        }

        // DIBUJO
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float texRatio = (float) fondoTex.getHeight() / fondoTex.getWidth();
        float worldRatio = worldHeight / worldWidth;

        float drawWidth, drawHeight;

        if (texRatio > worldRatio) {
            drawWidth = worldWidth;
            drawHeight = worldWidth * texRatio;
        } else {
            drawHeight = worldHeight;
            drawWidth = worldHeight / texRatio;
        }

        float x = (worldWidth - drawWidth) / 2f;
        float y = (worldHeight - drawHeight) / 2f;

        batch.draw(fondoTex, x, y, drawWidth, drawHeight);
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
        fondoTex.dispose();
        personajeTex.dispose();
    }
}
