package com.pife.juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    private SpriteBatch batch;
    private Texture image;
    private Sprite im;
    private FitViewport viewport;

    private Texture txtJugar, txtModos, txtOpciones, txtCreditos;
    private Sprite btnJugar, btnModos, btnOpciones, btnCreditos;


    @Override
    public void create() {
        batch = new SpriteBatch();

        image = new Texture("Portada-Quokky.png");
        im = new Sprite(image);

        viewport = new FitViewport(8, 5);

        im.setSize(2.5f, 5f);
        im.setPosition(2.5f, 0f);

        // Texturas de botones
        txtJugar = new Texture("BotonJugar.png");
        txtModos = new Texture("BotonModos.png");
        txtOpciones = new Texture("BotonOpciones.png");
        txtCreditos = new Texture("BotonCreditos.png");

        // Sprites de botones
        btnJugar = new Sprite(txtJugar);
        btnModos = new Sprite(txtModos);
        btnOpciones = new Sprite(txtOpciones);
        btnCreditos = new Sprite(txtCreditos);

        // Tamaño de botones
        float w = 2f;
        float h = 1f;

        float x = im.getX() + 0.2f;
        float yBase = im.getY() + im.getHeight() - 2.2f;

        btnJugar.setBounds(x, yBase, w, h);
        btnModos.setBounds(x, yBase - 0.7f, w, h);
        btnOpciones.setBounds(x, yBase - 1.4f, w, h);
        btnCreditos.setBounds(x, yBase - 2.1f, w, h);
    }

    @Override
    public void render() {
        // Si hay una Screen activa, deja que la Screen se dibuje
        if (getScreen() != null) {
            super.render();
            return;
        }

        // Definimos el Menú
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f); //Limpia la pantalla antes de dibujar nada.
        viewport.apply(); // Actualiza la cámara para el viewport actual y ajusta la escala
        batch.setProjectionMatrix(viewport.getCamera().combined); // Hace que los sprites se dibujen usando las coordenadas del mundo

        batch.begin();
        im.draw(batch);               // Fondo de portada
        btnJugar.draw(batch);         // Botones encima
        btnModos.draw(batch);
        btnOpciones.draw(batch);
        btnCreditos.draw(batch);
        batch.end();

        // Detectar clicks
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnJugar.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "JUGAR");
                setScreen(new PantallaNivelInfinito(this)); // Cambia de pantalla
            } else  if (btnModos.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "MODOS");
            }else  if (btnOpciones.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "OPCIONES");
            }else  if (btnCreditos.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "CREDITOS");
            }
        }


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        btnJugar.getTexture().dispose();
        btnModos.getTexture().dispose();
        btnOpciones.getTexture().dispose();
        btnCreditos.getTexture().dispose();
    }
}
