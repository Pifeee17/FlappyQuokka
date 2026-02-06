package com.pife.juego.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pife.juego.Idiomas.Idiomas;
import com.pife.juego.Main;
import com.pife.juego.Menus.MenuPrincipal;

public class PantallaOpciones implements Screen {

    private Main game;

    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture fondoTex;
    private Sprite fondo;

    private Texture txtBoton, txtBotonVolver;
    private Sprite btnMusica;
    private Sprite btnVibracion;
    private Sprite btnIdioma;
    private Sprite btnVolver;

    private BitmapFont font;
    private float factorEscaladoFuente;

    private Preferences prefs;
    private boolean musicaOn;
    private boolean vibracionOn;
    private String idioma;

    public PantallaOpciones(Main game) {
        this.game = game;
        this.font = game.fuente;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        viewport = new FitViewport(5, 8);

        fondoTex = new Texture("Pantallas/FondoNivelesQuokky.png");
        fondo = new Sprite(fondoTex);
        fondo.setSize(5f, 8f);
        fondo.setPosition(0, 0);

        txtBoton = new Texture("Botones/Boton.png");
        txtBotonVolver = new Texture("Botones/Boton_Volver.png");

        prefs = game.getPrefs();
        musicaOn = prefs.getBoolean("musicaOn", true);
        vibracionOn = prefs.getBoolean("vibracionOn", true);
        idioma = prefs.getString("idioma", "ES");

        // Cargar idioma actual (para traducir textos)
        Idiomas.cargar(idioma);

        btnMusica = new Sprite(txtBoton);
        btnVibracion = new Sprite(txtBoton);
        btnIdioma = new Sprite(txtBoton);
        btnVolver = new Sprite(txtBotonVolver);

        float w = 3.8f;
        float h = 1.5f;
        float x = (5f - w) / 2f;

        float yBase = 8f - 2.6f;

        btnMusica.setBounds(x, yBase, w, h);
        btnVibracion.setBounds(x, yBase - 1.2f, w, h);
        btnIdioma.setBounds(x, yBase - 2.4f, w, h);
        btnVolver.setBounds(x, yBase - 3.6f, w, h);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        fondo.draw(batch);
        btnMusica.draw(batch);
        btnVibracion.draw(batch);
        btnIdioma.draw(batch);
        btnVolver.draw(batch);

        // ===== Texto Botones (TRADUCIDO) =====

        // MUSICA
        if (musicaOn) {
            drawText(Idiomas.t("music") + ": " + Idiomas.t("on"), btnMusica);
        } else {
            drawText(Idiomas.t("music") + ": " + Idiomas.t("off"), btnMusica);
        }

        // VIBRACION
        if (vibracionOn) {
            drawText(Idiomas.t("vibration") + ": " + Idiomas.t("on"), btnVibracion);
        } else {
            drawText(Idiomas.t("vibration") + ": " + Idiomas.t("off"), btnVibracion);
        }

        // IDIOMA (muestras ES / EN porque es el valor del selector)
        drawText(Idiomas.t("language") + ": " + idioma, btnIdioma);

        // VOLVER
        // (mantengo tu alineación original del botón volver)
        font.draw(batch, Idiomas.t("back"), btnVolver.getX() + 1.6f, btnVolver.getY() + 0.85f);

        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(
                Gdx.input.getX(),
                Gdx.input.getY(),
                0
            );
            viewport.unproject(touch);

            if (btnMusica.getBoundingRectangle().contains(touch.x, touch.y)) {

                if (musicaOn) {
                    musicaOn = false;
                } else {
                    musicaOn = true;
                }

                prefs.putBoolean("musicaOn", musicaOn);
                prefs.flush();
                game.setMusicaActiva(musicaOn);

            } else if (btnVibracion.getBoundingRectangle().contains(touch.x, touch.y)) {

                if (vibracionOn) {
                    vibracionOn = false;
                } else {
                    vibracionOn = true;
                }

                prefs.putBoolean("vibracionOn", vibracionOn);
                prefs.flush();

                if (vibracionOn) {
                    Gdx.input.vibrate(40);
                }

            } else if (btnIdioma.getBoundingRectangle().contains(touch.x, touch.y)) {

                if (idioma.equals("ES")) {
                    idioma = "EN";
                } else {
                    idioma = "ES";
                }

                prefs.putString("idioma", idioma);
                prefs.flush();

                // RECARGAR TEXTOS al cambiar idioma (clave para que se vea al instante)
                Idiomas.cargar(idioma);

                if (vibracionOn) {
                    Gdx.input.vibrate(40);
                }

            } else if (btnVolver.getBoundingRectangle().contains(touch.x, touch.y)) {

                if (vibracionOn) {
                    Gdx.input.vibrate(40);
                }

                game.setScreen(new MenuPrincipal(game));
            }
        }
    }

    private void drawText(String texto, Sprite boton) {
        float x = boton.getX() + 0.8f;
        float y = boton.getY() + 0.85f;
        font.draw(batch, texto, x, y);
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
        txtBoton.dispose();
        // NO font.dispose()
    }
}
