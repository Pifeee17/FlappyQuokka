package com.pife.juego;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Vector3;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture image;
    private Sprite im;
    private FitViewport viewport;

    private Texture txtJugar, txtModos, txtOpciones, txtCreditos;
    private Sprite btnJugar, btnModos, btnOpciones, btnCreditos;

//    private BitmapFont fuente;
    @Override
    public void create() {
        batch = new SpriteBatch();

        image = new Texture("Portada-Quokky.png");
        im = new Sprite(image);

        viewport = new FitViewport(8, 5);

        im.setSize(2.5f, 5f);
        im.setPosition(2.5f, 0f);

        //Definimos la textura de cada Botón
        txtJugar = new Texture("BotonJugar.png");
        txtModos = new Texture("BotonModos.png");
        txtOpciones = new Texture("BotonOpciones.png");
        txtCreditos = new Texture("BotonCreditos.png");

        //Le aplica la textura a cada uno
        btnJugar = new Sprite(txtJugar);
        btnModos = new Sprite(txtModos);
        btnOpciones = new Sprite(txtOpciones);
        btnCreditos = new Sprite(txtCreditos);

        //Definimos el tamaño de los botones
        float w = 2f;
        float h = 1f;

        //Calculamos la posicion en base a la imagen
        float x = im.getX() + 0.2f;
        float yBase = im.getY() + im.getHeight() - 2.2f;

        //definimos la posicion de cada botón
        btnJugar.setBounds(x, yBase, w, h);
        btnModos.setBounds(x, yBase - 0.7f, w, h);
        btnOpciones.setBounds(x, yBase - 1.4f, w, h);
        btnCreditos.setBounds(x, yBase - 2.1f, w, h);


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        im.draw(batch);

        btnJugar.draw(batch);
        btnModos.draw(batch);
        btnOpciones.draw(batch);
        btnCreditos.draw(batch);

        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnJugar.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "JUGAR");
            }
            if (btnModos.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "MODOS");
            }
            if (btnOpciones.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "OPCIONES");
            }
            if (btnCreditos.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("BOTON", "CREDITOS");
            }
        }
    }

    @Override
    //Este metodo sirve para liberar espacio de los recursos que usas
    public void dispose() {
        batch.dispose();
        image.dispose();

        btnJugar.getTexture().dispose();
        btnModos.getTexture().dispose();
        btnOpciones.getTexture().dispose();
        btnCreditos.getTexture().dispose();
    }
}
