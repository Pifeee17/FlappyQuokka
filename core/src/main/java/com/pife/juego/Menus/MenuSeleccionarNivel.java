package com.pife.juego.Menus;

import com.badlogic.gdx.Gdx;
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
import com.pife.juego.Pantallas.PantallaNivel1;
import com.pife.juego.Pantallas.PantallaSeleccionModos;

public class MenuSeleccionarNivel implements Screen {

    private Sprite btnNivel1, btnVolver;
    private Texture botones, botonVolver;
    private BitmapFont font;
    private FitViewport viewport;
    private Main game;

    private SpriteBatch batch;
    private Texture image;
    private Sprite im;

    private int numero = 1;
    public MenuSeleccionarNivel(Main game) {
        this.game = game;
        font = game.fuente;
    }

    @Override
    public void show() {

        Gdx.app.log("SELECCIÓN", "Menu Selección Niveles");

        //Musica de fondo
        game.reproducirMusica("MusicaDeFondo/sonido-menu.mp3");

        //Idiomas
        String idioma = game.getPrefs().getString("idioma", "ES");
        Idiomas.cargar(idioma);

        batch = new SpriteBatch();

        image = new Texture("Pantallas/Fondo_Selec_Niveles.png");
        im = new Sprite(image);

        viewport = new FitViewport(5, 8);

        im.setSize(5f, 8f);
        im.setPosition(0, 0f);

        botones = new Texture("Botones/Boton_Nieve.png");
        botonVolver = new Texture("Botones/Boton_Volver.png");

        btnNivel1 = new Sprite(botones);
        btnVolver = new Sprite(botonVolver);
        float w = 3f;
        float h = 1f;

        float x = 1f;
        float yBase = 8f - 5f;

        btnNivel1.setBounds(x, yBase + 2.5F, w, h);
        btnVolver.setBounds(1.1f, 1f, w, h);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        im.draw(batch);
        btnNivel1.draw(batch);
        btnVolver.draw(batch);

        font.setColor(new Color(0.1f,0.4f,0.1f,1));

        font.draw(batch, Idiomas.t("pick"),  1f, 8f - 1f);
        font.draw(batch, Idiomas.t("level") + " " + numero, btnNivel1.getX() + 1.1f, btnNivel1.getY() + 0.57f);
        font.draw(batch, Idiomas.t("back"), btnVolver.getX() + 1.2f, btnVolver.getY() + 0.6f);

        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnNivel1.getBoundingRectangle().contains(touch.x, touch.y)) {
                game.setScreen(new PantallaNivel1(game));
                Gdx.app.log("NIVEL", "NIVEL 1");
            } else if (btnVolver.getBoundingRectangle().contains(touch.x, touch.y)) {
                game.setScreen(new PantallaSeleccionModos(game));
                Gdx.app.log("VOLVER", "VOLVER A LA SELECCION DEL MODO");
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        batch.dispose();
        image.dispose();
        font.dispose();
        botones.dispose();
    }
}
