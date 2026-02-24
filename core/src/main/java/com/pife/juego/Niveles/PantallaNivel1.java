package com.pife.juego.Niveles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pife.juego.Idiomas.Idiomas;
import com.pife.juego.Main;
import com.pife.juego.Menus.MenuSeleccionarNivel;
import com.pife.juego.Obstaculos.TroncosEnrredaderas;
import com.pife.juego.Pantallas.PantallaGameOver;
import com.pife.juego.Pantallas.PantallaVictoria;
import com.pife.juego.Personajes.Quokky;
import com.badlogic.gdx.math.Vector3;
import com.pife.juego.Menus.MenuPausa;
import com.pife.juego.Pantallas.Pausable;

public class PantallaNivel1 implements Screen, Pausable {

    private Main game;
    private SpriteBatch batch;
    private FitViewport viewport;
    // Botón pausa
    private Texture botonPausa;
    private float botonPausaSize = 0.8f;
    private float botonPausaX;
    private float botonPausaY;
    private boolean pausado = false;
    private com.badlogic.gdx.graphics.Texture fondoTex1;
    private com.badlogic.gdx.graphics.Texture fondoTex2;
    private float fondo1X;
    private float fondo2X;
    private float velocidadFondo = 1.5f;
    private boolean inicializado = false;
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

        if (inicializado == true) {
            return;
        }
        inicializado = true;

        batch = new SpriteBatch();

        String idioma = game.getPrefs().getString("idioma", "ES");
        Idiomas.cargar(idioma);

        fondoTex1 = new com.badlogic.gdx.graphics.Texture("Pantallas/Fondo_Nieve.png");
        fondoTex2 = new com.badlogic.gdx.graphics.Texture("Pantallas/Fondo_Nieve.png");

        fondo1X = 0f;
        fondo2X = viewport.getWorldWidth();

        // Botón pausa
        botonPausa = new com.badlogic.gdx.graphics.Texture("Botones/Boton_Pausa.png");
        botonPausaX = viewport.getWorldWidth() - botonPausaSize - 0.2f;
        botonPausaY = viewport.getWorldHeight() - botonPausaSize - 0.2f;

        pausado = false;           // importante al reiniciar

        game.reproducirMusica("MusicaDeFondo/sonido-nieve.mp3");

        quokky = new Quokky(viewport, Quokky.Skin.NIEVE);
        troncos = new TroncosEnrredaderas(viewport, TroncosEnrredaderas.Skin.NIEVE, 5);

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

        //Solo generamos obstáculos si NO se ha completado el nivel
        if (nivelCompletado == false) {
            troncos.update(delta, velocidadFondo, quokky.getX());

            int superados = troncos.getPuntos();
            if (superados >= 5) {

                // Desbloquear nivel 2
                game.getPrefs().putBoolean("nivel2_desbloqueado", true);
                game.getPrefs().flush();

                nivelCompletado = true;

                //IR A PANTALLA VICTORIA Y DECIRLE CUÁL ES EL SIGUIENTE NIVEL
                game.setScreen(
                    new PantallaVictoria(game, new PantallaNivel2(game)));
                    return;
            }


            // Colisión (solo mientras se juega)
            if (troncos.colisiona(quokky.getHitbox())) {
                Gdx.input.vibrate(300);
                game.setScreen(new PantallaGameOver(game, new PantallaNivel1(game), false));
            }
        }
    }

    @Override
    public void render(float delta) {

        // Detectar pausa
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (touch.x >= botonPausaX && touch.x <= botonPausaX + botonPausaSize
                && touch.y >= botonPausaY && touch.y <= botonPausaY + botonPausaSize) {

                pausado = true;
                game.setScreen(new MenuPausa(game, this));
                return;
            }
        }

        // Solo actualiza si NO está pausado
        if (!pausado) {
            update(delta);
        }

        ScreenUtils.clear(0f, 0f, 0f, 1f);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        drawFondo();

        if (!nivelCompletado) {
            troncos.draw(batch);
        }

        quokky.draw(batch);

        float textoX = 0.1f;
        float textoY = viewport.getWorldHeight() - 0.1f;

        int superados = troncos.getPuntos();
        int cuentaAtras = 5 - superados;
        if (cuentaAtras < 0) cuentaAtras = 0;

        String texto = Idiomas.t("obstacles") + ": " + cuentaAtras;
        font.draw(batch, texto, textoX, textoY);

        // DIBUJA el botón pausa
        batch.draw(botonPausa, botonPausaX, botonPausaY, botonPausaSize, botonPausaSize);

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
        botonPausa.dispose();
    }

    @Override
    public void reanudarJuego() {
        pausado = false;
        font.setColor(Color.BLACK);
    }

    @Override
    public Screen reiniciar() {
        return new PantallaNivel1(game);
    }
}
