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
import com.pife.juego.Niveles.PantallaNivel1;
import com.pife.juego.Niveles.PantallaNivel2;
import com.pife.juego.Niveles.PantallaNivel3;
import com.pife.juego.Pantallas.PantallaSeleccionModos;

public class MenuSeleccionarNivel implements Screen {

    private Sprite btnNivel1, btnNivel2, btnNivel3, btnVolver;
    private Texture botonesNieve, botonesPiramide, botonesCueva, botonBloqueado, botonVolver;
    private BitmapFont font;
    private FitViewport viewport;
    private Main game;

    private SpriteBatch batch;
    private Texture image;
    private Sprite im;

    private boolean nivel2Desbloqueado, nivel3Desbloqueado;
    private int numero = 1;

    public MenuSeleccionarNivel(Main game) {
        this.game = game;
        font = game.fuente;
    }

    @Override
    public void show() {

        Gdx.app.log("SELECCIÓN", "Menu Selección Niveles");

        // Música
        game.reproducirMusica("MusicaDeFondo/sonido-menu.mp3");

        // Idiomas
        String idioma = game.getPrefs().getString("idioma", "ES");
        Idiomas.cargar(idioma);

        batch = new SpriteBatch();

        image = new Texture("Pantallas/Fondo_Selec_Niveles.png");
        im = new Sprite(image);

        viewport = new FitViewport(5, 8);

        im.setSize(5f, 8f);
        im.setPosition(0, 0f);

        botonesNieve = new Texture("Botones/Boton_Nieve.png");
        botonesCueva = new Texture("Botones/Boton_Cueva.png");
        botonesPiramide = new Texture("Botones/Boton_Piramide.png");
        botonBloqueado = new Texture("Botones/Boton_Bloqueado.png");
        botonVolver = new Texture("Botones/Boton_Volver.png");

        btnNivel1 = new Sprite(botonesNieve);
        btnNivel2 = new Sprite(botonesCueva);
        btnNivel3 = new Sprite(botonesPiramide);
        btnVolver = new Sprite(botonVolver);

        float w = 3f;
        float h = 1f;

        float x = 1f;
        float yBase = 8f - 5f;

        btnNivel1.setBounds(x, yBase + 2.5f, w, h);
        btnNivel2.setBounds(x, yBase + 1.3f, w, h);
        btnNivel3.setBounds(x, yBase + 0.1f, w, h);
        btnVolver.setBounds(1.1f, 1f, w, h);

        //Leer si el nivel 2 está desbloqueado
        nivel2Desbloqueado = game.getPrefs().getBoolean("nivel2_desbloqueado", false);

        //Leer si el nivel 3 esta desbloqueado
        nivel3Desbloqueado = game.getPrefs().getBoolean("nivel3_desbloqueado", false);;

        // Cambiar textura según estado
        if (!nivel2Desbloqueado) {
            btnNivel2.setTexture(botonBloqueado);
        } else {
            btnNivel2.setTexture(botonesCueva);
        }

        //Cambiar textura segun estado
        if (!nivel3Desbloqueado) {
            btnNivel3.setTexture(botonBloqueado);
        } else {
            btnNivel3.setTexture(botonesPiramide);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        im.draw(batch);
        btnNivel1.draw(batch);
        btnNivel2.draw(batch);
        btnNivel3.draw(batch);
        btnVolver.draw(batch);

        // Textos
        font.setColor(Color.WHITE);
        font.draw(batch, Idiomas.t("pick"), 1f, 8f - 1f);

        font.setColor(new Color(0.1f, 0.4f, 0.1f, 1f));
        font.draw(batch, Idiomas.t("level") + " " + numero,
            btnNivel1.getX() + 1.05f, btnNivel1.getY() + 0.57f);

        if (nivel2Desbloqueado) {
            font.draw(batch, Idiomas.t("level") + " " + (numero + 1),
                btnNivel2.getX() + 1f, btnNivel2.getY() + 0.57f);
        }

        if (nivel3Desbloqueado) {
            font.draw(batch, Idiomas.t("level") + " " + (numero + 2),
                btnNivel3.getX() + 1f, btnNivel3.getY() + 0.57f);
        }


        font.draw(batch, Idiomas.t("back"),
            btnVolver.getX() + 1.2f, btnVolver.getY() + 0.6f);

        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnNivel1.getBoundingRectangle().contains(touch.x, touch.y)) {
                game.setScreen(new PantallaNivel1(game));
                Gdx.app.log("NIVEL", "NIVEL 1");

            } else if (btnNivel2.getBoundingRectangle().contains(touch.x, touch.y)) {

                if (nivel2Desbloqueado) {
                    game.setScreen(new PantallaNivel2(game));
                    Gdx.app.log("NIVEL", "NIVEL 2");
                } else {
                    Gdx.app.log("BLOQUEADO", "Nivel 2 bloqueado");
                    Gdx.input.vibrate(200);
                }
            }else if (btnNivel3.getBoundingRectangle().contains(touch.x, touch.y)) {

                    if (nivel3Desbloqueado) {
                        game.setScreen(new PantallaNivel3(game));
                        Gdx.app.log("NIVEL", "NIVEL 3");
                    } else {
                        Gdx.app.log("BLOQUEADO", "Nivel 3 bloqueado");
                        Gdx.input.vibrate(200);
                    }
            } else if (btnVolver.getBoundingRectangle().contains(touch.x, touch.y)) {
                game.setScreen(new PantallaSeleccionModos(game));
                Gdx.app.log("VOLVER", "VOLVER A LA SELECCION DEL MODO");
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
        image.dispose();
        botonesNieve.dispose();
        botonesCueva.dispose();
        botonBloqueado.dispose();
        botonVolver.dispose();
    }
}
