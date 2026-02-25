package com.pife.juego.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pife.juego.Idiomas.Idiomas;
import com.pife.juego.Main;
import com.pife.juego.Menus.MenuPrincipal;
import com.badlogic.gdx.utils.Align;

public class PantallaCreditos implements Screen {

    private Main game;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture fondo;
    private BitmapFont font;
    private GlyphLayout layout;

    public PantallaCreditos(Main game) {
        this.game = game;
        this.font = game.fuente;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        viewport = new FitViewport(5, 8);
        fondo = new Texture("Pantallas/FondoNivelesQuokky.png");
        layout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0f, 0f, 0f, 1f);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        // Dibujar fondo
        batch.draw(fondo, 0, 0,
            viewport.getWorldWidth(),
            viewport.getWorldHeight());

        float centerX = viewport.getWorldWidth() / 2f;
        font.setColor(Color.BLACK);

        float y1 = 7f;
        float y2 = 6.5f;
        float y3 = 6f;
        float y4 = 5.5f;
        float y5 = 5f;
        float y6 = 4.5f;
        float y7 = 4f;
        float y8 = 3.5f;
        float y9 = 3f;
        float y10 = 2.35f;
        float y11 = 1.5f;
        float y12 = 0.5f;


        // Título
        layout.setText(font, "FLAPPY QUOKKY");
        font.draw(batch, layout, centerX - layout.width / 2f, y1);

        // Desarrollado por
        layout.setText(font, Idiomas.t("desarrolladopor"));
        font.draw(batch, layout, centerX - layout.width / 2f, y2);

        layout.setText(font, "Pife");
        font.draw(batch, layout, centerX - layout.width / 2f, y3);

        // Desarrollado con
        layout.setText(font, Idiomas.t("desarrolladocon"));
        font.draw(batch, layout, centerX - layout.width / 2f, y4);

        layout.setText(font, "LibGDX");
        font.draw(batch, layout, centerX - layout.width / 2f, y5);

        layout.setText(font, "Android Studio");
        font.draw(batch, layout, centerX - layout.width / 2f, y6);

        layout.setText(font, "Java");
        font.draw(batch, layout, centerX - layout.width / 2f, y7);

        // Música
        layout.setText(font, Idiomas.t("music") + ": Pixabay");
        font.draw(batch, layout, centerX - layout.width / 2f, y8);


        font.draw(batch,
            Idiomas.t("personajeobstaculofondo"),
            0.3f,      // margen izquierdo
            y9,
            4.4f,      // ancho máximo (tu mundo es 5)
            Align.center,
            true);     // activa el wrap

        layout.setText(font, "ChatGPT y Gemini");
        font.draw(batch, layout, centerX - layout.width / 2f, y10);

        // Gracias
        layout.setText(font, Idiomas.t("graciasporjugar"));
        font.draw(batch, layout, centerX - layout.width / 2f, y11);

        layout.setText(font, Idiomas.t("tocaparavolver"));
        font.draw(batch, layout, centerX - layout.width / 2f, y12);


        batch.end();

        // Volver al menú tocando pantalla
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);
            game.setScreen(new MenuPrincipal(game));
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
        fondo.dispose();
    }
}
