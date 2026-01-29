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
import com.pife.juego.Main;
import com.pife.juego.Menus.MenuPrincipal;

public class PantallaGameOver implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Texture fondoGameOver;
    private Texture txtBoton;
    private FitViewport viewport;
    private Sprite btnReintentar, btnMenu;
    private BitmapFont font;
    private Float factorEscaladoFuente;
    public PantallaGameOver(Main game) {
        this.game = game;
        font = game.fuente;
    }

    @Override
    public void show() {

        //Musica de Fondo
        game.reproducirMusica("MusicaDeFondo/sonido-menu.mp3");

        batch = new SpriteBatch();
        fondoGameOver = new Texture("PantallaGameOver.png");

        txtBoton = new Texture("Boton.png");

        // Mismo viewport que Main (5x8 unidades)
        viewport = new FitViewport(5, 8);

        btnMenu = new Sprite(txtBoton);
        btnReintentar = new Sprite(txtBoton);

        // Tamaño de botones
        float w = 3f;
        float h = 1f; // botón más pequeño para que quepan juntos

        float x = 1f;
        float yBase = 8f - 3.2f;

        btnReintentar.setBounds(x, yBase, w, h);
        btnMenu.setBounds(x, yBase - 0.85f, w, h);

        float dpiScale = Gdx.graphics.getDensity();
        factorEscaladoFuente = (viewport.getWorldHeight() / Gdx.graphics.getHeight()) * dpiScale;
        font.getData().setScale(factorEscaladoFuente * 0.58f);
        font.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        // Dibuja la pantalla completa igual que la portada
        batch.draw(fondoGameOver, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        btnReintentar.draw(batch);
        btnMenu.draw(batch);

        font.draw(batch, "REINTENTAR", btnReintentar.getX() + 0.65f, btnReintentar.getY() + 0.6f );
        font.draw(batch, "MENU", btnMenu.getX() + 1.2f, btnMenu.getY() + 0.6f);

        batch.end();

        // Reiniciar juego si se toca la pantalla
        // Detectar clicks
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnReintentar.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "REINTENTAR");
                game.setScreen(new PantallaNivelInfinito(game)); // Cambia de pantalla
            } else if (btnMenu.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "MENÚ");
                game.setScreen(new MenuPrincipal(game));
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
        fondoGameOver.dispose();
        btnMenu.getTexture().dispose();
        btnReintentar.getTexture().dispose();
        font.dispose();
    }
}
