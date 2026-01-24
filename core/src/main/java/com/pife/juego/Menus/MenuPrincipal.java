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
import com.pife.juego.Main;
import com.pife.juego.Pantallas.PantallaNivelInfinito;
import com.pife.juego.Pantallas.PantallaSeleccionModos;

public class MenuPrincipal implements Screen {

    private Main game;

    private SpriteBatch batch;
    private Texture image;
    private Sprite im;
    private FitViewport viewport;
    private BitmapFont font;
    private Float factorEscaladoFuente;
    private Texture txtBotones;

    private Sprite btnJugar, btnModos, btnOpciones, btnCreditos;

    public MenuPrincipal(Main game) {
        this.game = game;
    }

    @Override
    public void show() {


            Gdx.app.log("MENU", "MenuPrincipal cargado");


            batch = new SpriteBatch();

        image = new Texture("Portada-Quokky.png");
        im = new Sprite(image);

        viewport = new FitViewport(5, 8);

        im.setSize(5f, 8f);
        im.setPosition(0, 0f);

        // Texturas de botones
        txtBotones = new Texture("Boton.png");

        // Sprites de botones
        btnJugar = new Sprite(txtBotones);
        btnModos = new Sprite(txtBotones);
        btnOpciones = new Sprite(txtBotones);
        btnCreditos = new Sprite(txtBotones);

        // Tamaño de botones
        float w = 3f;
        float h = 1f;

        float x = 1f;
        float yBase = 8f - 5f;

        btnJugar.setBounds(x, yBase, w, h);
        btnModos.setBounds(x, yBase - 0.85f, w, h);
        btnOpciones.setBounds(x, yBase - 1.7f, w, h);
        btnCreditos.setBounds(x, yBase - 2.55f, w, h);

        // Fuente
        font = new BitmapFont();
        float dpiScale = Gdx.graphics.getDensity();
        factorEscaladoFuente = (viewport.getWorldHeight() / Gdx.graphics.getHeight()) * dpiScale;
        font.getData().setScale(factorEscaladoFuente * 2);
        font.setColor(Color.BLACK);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        im.draw(batch);
        btnJugar.draw(batch);
        btnModos.draw(batch);
        btnOpciones.draw(batch);
        btnCreditos.draw(batch);

        // Texto de botones
        font.draw(batch, "JUGAR", btnJugar.getX() + 0.35f, btnJugar.getY() + 0.65f);
        font.draw(batch, "MODOS", btnModos.getX() + 0.30f, btnModos.getY() + 0.65f);
        font.draw(batch, "OPCIONES", btnOpciones.getX() + 0.15f, btnOpciones.getY() + 0.65f);
        font.draw(batch, "CREDITOS", btnCreditos.getX() + 0.15f, btnCreditos.getY() + 0.65f);

        batch.end();

        // Detectar clicks
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnJugar.getBoundingRectangle().contains(touch.x, touch.y)) {
                game.setScreen(new PantallaNivelInfinito(game));
            } else if (btnModos.getBoundingRectangle().contains(touch.x, touch.y)) {
                game.setScreen(new PantallaSeleccionModos(game));
            } else if (btnOpciones.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "OPCIONES");
            } else if (btnCreditos.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "CREDITOS");
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
