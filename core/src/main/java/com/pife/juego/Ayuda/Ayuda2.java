package com.pife.juego.Ayuda;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;
import com.pife.juego.Idiomas.Idiomas;
import com.pife.juego.Main;
import com.pife.juego.Menus.MenuPrincipal;
import com.badlogic.gdx.utils.Align;

public class Ayuda2 implements Screen {

    private Main game;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture fondo;
    private Texture imagenIzquierda;

    private BitmapFont font;
    private GlyphLayout layout;
    private Texture txtBotonSiguiente;
    private Sprite btnSiguiente;

    private float tiempoTranscurrido;
    private boolean mostrarBoton;

    public Ayuda2(Main game) {
        this.game = game;
        this.font = game.fuente; // usamos tu fuente global
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(5, 8);

        fondo = new Texture("Pantallas/FondoNivelesQuokky.png");
        imagenIzquierda = new Texture("Ayuda/Derecha.png");

        layout = new GlyphLayout();

        font.getData().setScale(0.005f);

        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        txtBotonSiguiente = new Texture("Botones/Boton.png");
        btnSiguiente = new Sprite(txtBotonSiguiente);

        // Posición debajo del texto
        btnSiguiente.setBounds(1f, 0f, 3f, 2f);

        tiempoTranscurrido = 0f;
        mostrarBoton = false;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        //Tiempo para el boton
        tiempoTranscurrido = tiempoTranscurrido + delta;

        if (tiempoTranscurrido >= 3f) {
            mostrarBoton = true;
        }

        batch.begin();

        // Fondo
        batch.draw(fondo, 0, 0, 5, 8);

        // Imagen centrada arriba
        batch.draw(imagenIzquierda, 0f, 3.5f, 5f, 4.5f);

        font.setColor(Color.BLACK);

        String texto = Idiomas.t("ayuda2").replace("\\n", "\n");

        font.draw(batch,
            texto,
            0.3f,      // margen izquierdo
            2.8f,      // altura
            4.4f,      // ancho máximo del texto (IMPORTANTE)
            Align.center,
            true);     // activa el wrap automático

        if (mostrarBoton) {

            btnSiguiente.draw(batch);

            font.setColor(new Color(0.1f, 0.4f, 0.1f, 1f));

            dibujarTextoCentrado(btnSiguiente, Idiomas.t("next"));
        }

        batch.end();


        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){

            game.setScreen(new MenuPrincipal(game));
        }

        if (mostrarBoton && Gdx.input.justTouched()) {

            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnSiguiente.getBoundingRectangle().contains(touch.x, touch.y)) {
                game.setScreen(new Ayuda3(game)); // siguiente pantalla
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
        fondo.dispose();
        imagenIzquierda.dispose();
    }
    private void dibujarTextoCentrado(Sprite boton, String texto) {

        layout.setText(font, texto);

        float x = boton.getX() + (boton.getWidth() - layout.width) / 2f;

        // Centramos usando la altura total del botón
        float y = boton.getY() + (boton.getHeight() - layout.height) / 2f + layout.height + 0.05f;;

        font.draw(batch, layout, x, y);
    }
}

