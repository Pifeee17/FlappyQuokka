package com.pife.juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    private SpriteBatch batch;
    private Texture image;
    private Sprite im;
    private FitViewport viewport;
    BitmapFont font;
    Float factorEscaladoFuente;
    private Texture txtBotones;
    private Sprite btnJugar, btnModos, btnOpciones, btnCreditos;

    @Override
    public void create() {
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
        float w = 2f;
        float h = 1f;

        float x = im.getX() + 0.2f;
        float yBase = im.getY() + im.getHeight() - 2.2f;

        //Coordenadas Botones
        btnJugar.setBounds(x, yBase, w, h);
        btnModos.setBounds(x, yBase - 0.7f, w, h);
        btnOpciones.setBounds(x, yBase - 1.4f, w, h);
        btnCreditos.setBounds(x, yBase - 2.1f, w, h);

        //Poniendo la fuente
        // Se usa la fuente interna de libGDX
        font = new BitmapFont();
        // Se usa una fuente externa generada con Hiero
//        font = new BitmapFont(Gdx.files.internal("Fuente/Early GameBoy.fnt"));
        // Densidad de la pantalla del dispositivo
        float dpiScale = Gdx.graphics.getDensity();
        factorEscaladoFuente = (viewport.getWorldHeight() / Gdx.graphics.getHeight()) * dpiScale;
        //Escalamos la fuente y le damos color
        font.getData().setScale(factorEscaladoFuente * 2);
        font.setColor(Color.BLACK);
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
        ScreenUtils.clear(Color.GRAY); //Limpia la pantalla antes de dibujar nada.
        viewport.apply(); // Actualiza la cámara para el viewport actual y ajusta la escala
        batch.setProjectionMatrix(viewport.getCamera().combined); // Hace que los sprites se dibujen usando las coordenadas del mundo


        ShapeRenderer shapeRenderer=new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();


        shapeRenderer.setColor(Color.RED);

        shapeRenderer.rect(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        shapeRenderer.end();
        batch.begin();

        im.draw(batch);               // Fondo de portada
        btnJugar.draw(batch);         // Botones encima
        btnModos.draw(batch);
        btnOpciones.draw(batch);
        btnCreditos.draw(batch);

        // TEXTO DE LOS BOTONES

        font.draw(batch, "JUGAR2", 2,2);
        font.draw(batch, "JUGAR", btnJugar.getX() + 0.35f, btnJugar.getY() + 0.65f);
        font.draw(batch, "MODOS", btnModos.getX() + 0.30f, btnModos.getY() + 0.65f);
        font.draw(batch, "OPCIONES", btnOpciones.getX() + 0.15f, btnOpciones.getY() + 0.65f);
        font.draw(batch, "CREDITOS", btnCreditos.getX() + 0.15f, btnCreditos.getY() + 0.65f);

                if (Gdx.input.justTouched()) {
                    Gdx.app.log("pulso", Gdx.input.getX() + " " + Gdx.input.getY());
                    Vector2 vector2=new Vector2();
                    vector2.add(Gdx.input.getX(),Gdx.input.getY());
                    viewport.unproject(vector2);
                    Gdx.app.log("pulso 1", Gdx.input.getX() + " " + Gdx.input.getY());
                    Gdx.app.log("pulso 2", vector2.x + " " + vector2.y);
                }
        batch.end();

        // Detectar clicks
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnJugar.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "JUGAR");
                setScreen(new PantallaNivelInfinito(this)); // Cambia de pantalla
            } else if (btnModos.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "MODOS");
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

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        font.dispose();
        btnJugar.getTexture().dispose();
        btnModos.getTexture().dispose();
        btnOpciones.getTexture().dispose();
        btnCreditos.getTexture().dispose();
    }
}
