package com.pife.juego.Menus;

import com.badlogic.gdx.Application;
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
import com.pife.juego.Constantes;
import com.pife.juego.Main;
import com.pife.juego.Pantallas.PantallaNivelInfinito;
import com.pife.juego.Pantallas.PantallaOpciones;
import com.pife.juego.Pantallas.PantallaSeleccionModos;
import com.pife.juego.Idiomas.Idiomas;


public class MenuPrincipal implements Screen {

    private Main game;

    private SpriteBatch batch;
    private Texture image;
    private Sprite im;
    private FitViewport viewport;
    private BitmapFont font;
    private Texture txtBotones;

    private Sprite btnJugar, btnModos, btnOpciones, btnCreditos;

    public MenuPrincipal(Main game) {
        this.game = game;
        font = game.fuente;
    }

    @Override
    public void show() {

        Gdx.app.log("MENU", "MenuPrincipal cargado");

        //Musica de Fondo
        game.reproducirMusica("MusicaDeFondo/sonido-menu.mp3");

        //Idiomas
        String idioma = game.getPrefs().getString("idioma", "ES");
        Idiomas.cargar(idioma);


        batch = new SpriteBatch();

        image = new Texture("Pantallas/Portada-Quokky.png");
        im = new Sprite(image);

        viewport = new FitViewport(5, 8);

        im.setSize(5f, 8f);
        im.setPosition(0, 0f);

        txtBotones = new Texture("Botones/Boton.png");

        btnJugar = new Sprite(txtBotones);
        btnModos = new Sprite(txtBotones);
        btnOpciones = new Sprite(txtBotones);
        btnCreditos = new Sprite(txtBotones);

        float w = 3f;
        float h = 1f;

        float x = 1f;
        float yBase = 8f - 5f;

        btnJugar.setBounds(x, yBase, w, h);
        btnModos.setBounds(x, yBase - 0.85f, w, h);
        btnOpciones.setBounds(x, yBase - 1.7f, w, h);
        btnCreditos.setBounds(x, yBase - 2.55f, w, h);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        im.draw(batch);
        btnJugar.draw(batch);
        btnModos.draw(batch);
        btnOpciones.draw(batch);
        btnCreditos.draw(batch);

        font.draw(batch, Idiomas.t("play"), btnJugar.getX() + 1f, btnJugar.getY() + 0.65f);
        font.draw(batch, Idiomas.t("modes"), btnModos.getX() + 1f, btnModos.getY() + 0.65f);
        font.draw(batch, Idiomas.t("options"), btnOpciones.getX() + 0.65f, btnOpciones.getY() + 0.65f);
        font.draw(batch, Idiomas.t("credits"), btnCreditos.getX() + 0.65f, btnCreditos.getY() + 0.65f);


        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnJugar.getBoundingRectangle().contains(touch.x, touch.y)) {
                game.setScreen(new PantallaNivelInfinito(game));
            } else if (btnModos.getBoundingRectangle().contains(touch.x, touch.y)) {
                game.setScreen(new PantallaSeleccionModos(game));
            } else if (btnOpciones.getBoundingRectangle().contains(touch.x, touch.y)) {
                game.setScreen(new PantallaOpciones(game));
            }
        }
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
        image.dispose();
        font.dispose();
        txtBotones.dispose();
    }
}
