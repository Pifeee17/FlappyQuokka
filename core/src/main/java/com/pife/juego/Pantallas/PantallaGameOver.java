package com.pife.juego.Pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pife.juego.Idiomas.Idiomas;
import com.pife.juego.Main;
import com.pife.juego.Menus.MenuPrincipal;

public class PantallaGameOver implements Screen {

    private Main game;
    private Screen pantallaReintento; //pantalla a la que volver al reintentar
    private boolean vieneDeInfinito;
    private SpriteBatch batch;
    private Texture fondoGameOver;
    private GlyphLayout layout;
    private Texture txtBoton;
    private FitViewport viewport;
    private Sprite btnReintentar, btnMenu;
    private BitmapFont font;

    public PantallaGameOver(Main game, Screen pantallaReintento, boolean vieneDeInfinito) {
        this.game = game;
        this.pantallaReintento = pantallaReintento;
        this.vieneDeInfinito = vieneDeInfinito;
        this.font = game.fuente;
    }

    @Override
    public void show() {

        game.reproducirMusica("MusicaDeFondo/sonido-menu.mp3");

        batch = new SpriteBatch();
        fondoGameOver = new Texture("Pantallas/PantallaGameOver.png");
        txtBoton = new Texture("Botones/Boton.png");

        layout = new GlyphLayout();

        viewport = new FitViewport(5, 8);

        btnMenu = new Sprite(txtBoton);
        btnReintentar = new Sprite(txtBoton);

        float w = 3f;
        float h = 1f;

        float x = 1f;
        float yBase = 8f - 3.2f;

        btnReintentar.setBounds(x, yBase, w, h);
        btnMenu.setBounds(x, yBase - 0.85f, w, h);


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        batch.draw(fondoGameOver, 0f, 0f, viewport.getWorldWidth(), viewport.getWorldHeight());

        if (vieneDeInfinito) {
            int record = game.getPrefs().getInteger("record_infinito", 0);
            font.setColor(Color.WHITE);
            font.draw(batch, "RECORD: " + record,
                btnReintentar.getX() + 0.65f,
                viewport.getWorldHeight() - 1f);
        }
        else {
            font.setColor(Color.WHITE);
            font.draw(batch, Idiomas.t("perdiste"),
                btnReintentar.getX() + 0.7f,
                viewport.getWorldHeight() - 1f);
        }
        font.setColor(new Color(0.1f, 0.4f, 0.1f, 1f));
        btnReintentar.draw(batch);
        btnMenu.draw(batch);

        dibujarTextoCentrado(btnReintentar, Idiomas.t("reintentar"));
        dibujarTextoCentrado(btnMenu, Idiomas.t("menu"));

        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f);
            viewport.unproject(touch);

            if (btnReintentar.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "REINTENTAR");
                //Volver al modo que estabas jugando
                game.setScreen(pantallaReintento);
            } else if (btnMenu.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "MENÚ");
                game.setScreen(new MenuPrincipal(game));
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
        fondoGameOver.dispose();
        txtBoton.dispose();
    }
    private void dibujarTextoCentrado(Sprite boton, String texto) {

        layout.setText(font, texto);

        float x = boton.getX() + (boton.getWidth() - layout.width) / 2f;

        // Centramos usando la altura total del botón
        float y = boton.getY() + (boton.getHeight() - layout.height) / 2f + layout.height + 0.05f;;

        font.draw(batch, layout, x, y);
    }
}
