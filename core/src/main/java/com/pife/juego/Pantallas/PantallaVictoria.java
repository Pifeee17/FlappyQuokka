package com.pife.juego.Pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
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
import com.pife.juego.Menus.MenuSeleccionarNivel;

public class PantallaVictoria implements Screen {

    private Main game;
    private Screen Siguiente_Nivel; //pantalla a la que volver al reintentar

    private SpriteBatch batch;
    private Texture fondoVictoria;
    private Texture txtBoton;
    private FitViewport viewport;
    private Sprite btnSiguiente, btnMenu;
    private BitmapFont font;

    public PantallaVictoria(Main game, Screen Siguiente_Nivel) {
        this.game = game;
        this.Siguiente_Nivel = Siguiente_Nivel;
        this.font = game.fuente;
    }

    @Override
    public void show() {

        game.reproducirMusica("MusicaDeFondo/sonido-victoria.mp3");

        batch = new SpriteBatch();
        fondoVictoria = new Texture("Pantallas/Fondo_Menu_Victoria.png");
        txtBoton = new Texture("Botones/Boton.png");

        viewport = new FitViewport(5, 8);

        btnMenu = new Sprite(txtBoton);
        btnSiguiente = new Sprite(txtBoton);

        float w = 3f;
        float h = 1f;

        float x = 1f;
        float yBase = 8f - 3.2f;

        btnSiguiente.setBounds(x, yBase, w, h);
        btnMenu.setBounds(x, yBase - 0.85f, w, h);

        font.setColor(new Color(0.1f, 0.4f, 0.1f, 1f));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        batch.draw(fondoVictoria, 0f, 0f, viewport.getWorldWidth(), viewport.getWorldHeight());
        btnSiguiente.draw(batch);
        btnMenu.draw(batch);

        font.draw(batch, Idiomas.t("congra"), 1f, 1f);

        font.draw(batch, "SIGUIENTE", btnSiguiente.getX() + 0.65f, btnSiguiente.getY() + 0.6f);
        font.draw(batch, "MENU", btnMenu.getX() + 1.2f, btnMenu.getY() + 0.6f);

        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f);
            viewport.unproject(touch);

            if (btnSiguiente.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "REINTENTAR");
                //Volver al modo que estabas jugando
                game.setScreen(Siguiente_Nivel);
            } else if (btnMenu.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "MENÚ");
                game.setScreen(new MenuSeleccionarNivel(game));
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
        fondoVictoria.dispose();
        txtBoton.dispose();
    }
}
