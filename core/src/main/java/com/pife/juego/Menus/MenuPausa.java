package com.pife.juego.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.pife.juego.Pantallas.PantallaNivelInfinito;

public class MenuPausa implements Screen {

    private Main game;
    private Texture fondo;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture texturaBoton;

    private PantallaNivelInfinito pantallaAnterior;

    private Sprite btnReanudar;
    private Sprite btnReiniciar;
    private Sprite btnMenu;

    private GlyphLayout layout;
    private BitmapFont font;

    public MenuPausa(Main game, PantallaNivelInfinito pantallaAnterior) {
        this.game = game;
        this.pantallaAnterior = pantallaAnterior;
        this.font = game.fuente;
        viewport = new FitViewport(5, 8);
    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        layout = new GlyphLayout();

        fondo = new Texture("Pantallas/FondoNivelesQuokky.png");
        texturaBoton = new Texture("Botones/Boton.png");

        btnReanudar = new Sprite(texturaBoton);
        btnReiniciar = new Sprite(texturaBoton);
        btnMenu = new Sprite(texturaBoton);

        float w = 3f;
        float h = 1f;

        float x = viewport.getWorldWidth() / 2f - w / 2f;
        float centroY = viewport.getWorldHeight() / 2f;

        btnReanudar.setBounds(x, centroY + 0.8f, w, h);
        btnReiniciar.setBounds(x, centroY - 0.4f, w, h);
        btnMenu.setBounds(x, centroY - 1.6f, w, h);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        // Fondo
        batch.draw(fondo, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Titulo
        font.setColor(Color.WHITE);
        layout.setText(font, Idiomas.t("pausa"));
        float tituloX = viewport.getWorldWidth() / 2f - layout.width / 2f;
        font.draw(batch, layout, tituloX, viewport.getWorldHeight() - 0.5f);

        // Botones
        btnReanudar.draw(batch);
        btnReiniciar.draw(batch);
        btnMenu.draw(batch);

        font.setColor(new Color(0.1f, 0.4f, 0.1f, 1f));

        dibujarTextoCentrado(btnReanudar, Idiomas.t("reanudar"));
        dibujarTextoCentrado(btnReiniciar, Idiomas.t("reiniciar"));
        dibujarTextoCentrado(btnMenu, Idiomas.t("menu"));

        batch.end();

        detectarToque();
    }

    private void detectarToque() {

        if (Gdx.input.justTouched()) {

            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnReanudar.getBoundingRectangle().contains(touch.x, touch.y)) {

                pantallaAnterior.reanudarJuego();
                game.setScreen(pantallaAnterior);

            } else if (btnReiniciar.getBoundingRectangle().contains(touch.x, touch.y)) {

                game.setScreen(new PantallaNivelInfinito(game));

            } else if (btnMenu.getBoundingRectangle().contains(touch.x, touch.y)) {

                game.setScreen(new MenuPrincipal(game));
            }
        }
    }

    private void dibujarTextoCentrado(Sprite boton, String texto) {

        layout.setText(font, texto);

        float x = boton.getX() + (boton.getWidth() - layout.width) / 2f;

        float y = boton.getY() + (boton.getHeight() - layout.height) / 2f + layout.height + 0.05f;

        font.draw(batch, layout, x, y);
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
        fondo.dispose();
        texturaBoton.dispose();
    }
}
