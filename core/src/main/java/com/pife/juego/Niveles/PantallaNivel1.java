package com.pife.juego.Niveles;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pife.juego.Idiomas.Idiomas;
import com.pife.juego.Main;
import com.pife.juego.Menus.MenuSeleccionarNivel;
import com.pife.juego.Obstaculos.TroncosEnrredaderas;
import com.pife.juego.Pantallas.PantallaGameOver;
import com.pife.juego.Personajes.Quokky;

public class PantallaNivel1 implements Screen {

    private Main game;
    private SpriteBatch batch;
    private FitViewport viewport;

    private com.badlogic.gdx.graphics.Texture fondoTex1;
    private com.badlogic.gdx.graphics.Texture fondoTex2;
    private float fondo1X;
    private float fondo2X;
    private float velocidadFondo = 1.5f;

    private Quokky quokky;
    private TroncosEnrredaderas troncos;

    private BitmapFont font;

    //Para evitar que se dibujen obstáculos extra al completar el nivel
    private boolean nivelCompletado = false;

    public PantallaNivel1(Main game) {
        this.game = game;
        viewport = new FitViewport(8, 5);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        String idioma = game.getPrefs().getString("idioma", "ES");
        Idiomas.cargar(idioma);

        fondoTex1 = new com.badlogic.gdx.graphics.Texture("Pantallas/Fondo_Nieve.png");
        fondoTex2 = new com.badlogic.gdx.graphics.Texture("Pantallas/Fondo_Nieve.png");

        fondo1X = 0f;
        fondo2X = viewport.getWorldWidth();

        game.reproducirMusica("MusicaDeFondo/sonido-nieve.mp3");

        quokky = new Quokky(viewport, Quokky.Skin.NIEVE);
        troncos = new TroncosEnrredaderas(viewport, TroncosEnrredaderas.Skin.NIEVE, 10);

        font = game.fuente;
        font.setColor(Color.BLACK);

        nivelCompletado = false;
    }

    private void update(float delta) {

        float nuevoFondo1X = fondo1X - velocidadFondo * delta;
        float nuevoFondo2X = fondo2X - velocidadFondo * delta;

        fondo1X = nuevoFondo1X;
        fondo2X = nuevoFondo2X;

        float worldWidth = viewport.getWorldWidth();

        if (fondo1X + worldWidth <= 0f) {
            fondo1X = fondo2X + worldWidth;
        }

        if (fondo2X + worldWidth <= 0f) {
            fondo2X = fondo1X + worldWidth;
        }

        quokky.update(delta);

        //Solo actualizamos/generamos obstáculos si NO se ha completado el nivel
        if (nivelCompletado == false) {
            troncos.update(delta, velocidadFondo, quokky.getX());

            int superados = troncos.getPuntos();
            if (superados >= 10) {
                nivelCompletado = true;
                game.setScreen(new MenuSeleccionarNivel(game));
                return;
            }

            // Colisión (solo mientras se juega)
            if (troncos.colisiona(quokky.getHitbox())) {
                game.setScreen(new PantallaGameOver(game, new PantallaNivel1(game)));
            }
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        ScreenUtils.clear(0f, 0f, 0f, 1f);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        drawFondo();

        //No dibuja obstáculos extra si ya se completó el nivel
        if (nivelCompletado == false) {
            troncos.draw(batch);
        }

        quokky.draw(batch);

        float textoX = 0.1f;
        float textoY = viewport.getWorldHeight() - 0.1f;

        int superados = troncos.getPuntos();
        int cuentaAtras = 10 - superados;
        if (cuentaAtras < 0) {
            cuentaAtras = 0;
        }

        String texto = Idiomas.t("obstacles") + ": " + cuentaAtras;
        font.draw(batch, texto, textoX, textoY);

        batch.end();
    }

    private void drawFondo() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float texRatio = (float) fondoTex1.getHeight() / fondoTex1.getWidth();
        float drawHeight = worldWidth * texRatio;
        float y = (worldHeight - drawHeight) / 2f;

        batch.draw(fondoTex1, fondo1X, y, worldWidth, drawHeight);
        batch.draw(fondoTex2, fondo2X, y, worldWidth, drawHeight);
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
        quokky.dispose();
        troncos.dispose();
    }
}
