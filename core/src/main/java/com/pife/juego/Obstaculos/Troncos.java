package com.pife.juego.Obstaculos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

public class Troncos {

    private Array<Sprite> abajo = new Array<>();
    private Array<Sprite> arriba = new Array<>();

    private Texture texAbajo, texArriba;
    private Random random = new Random();

    private float tiempo = 0f;
    private float intervalo = 2.5f;
    private float hueco = 1.5f;

    private FitViewport viewport;

    public Troncos(FitViewport viewport) {
        this.viewport = viewport;
        texAbajo = new Texture("tronco vertical.png");
        texArriba = new Texture("tronco vertical mas largo.png");
    }

    public void update(float delta, float velocidad) {
        tiempo += delta;

        if (tiempo >= intervalo) {
            tiempo = 0;
            generarPar();
        }

        for (Sprite s : abajo) {
            s.setX(s.getX() - velocidad * delta);
        }
        for (Sprite s : arriba) {
            s.setX(s.getX() - velocidad * delta);
        }

        // Eliminar el par cuando sale de pantalla
        if (abajo.size > 0 && abajo.first().getX() + abajo.first().getWidth() < 0) {
            abajo.removeIndex(0);
            arriba.removeIndex(0);
        }
    }

    private void generarPar() {
        float alturaAbajo = 1f + random.nextFloat()
            * (viewport.getWorldHeight() - hueco - 2f);
        float alturaArriba = viewport.getWorldHeight() - alturaAbajo - hueco;

        Sprite sAbajo = new Sprite(texAbajo);
        sAbajo.setSize(1f, alturaAbajo);
        sAbajo.setPosition(viewport.getWorldWidth(), 0);

        Sprite sArriba = new Sprite(texArriba);
        sArriba.setSize(1f, alturaArriba);
        sArriba.setPosition(viewport.getWorldWidth(), alturaAbajo + hueco);

        abajo.add(sAbajo);
        arriba.add(sArriba);
    }

    public boolean colisiona(Rectangle personaje) {
        for (int i = 0; i < abajo.size; i++) {
            if (personaje.overlaps(getHitbox(abajo.get(i))) ||
                personaje.overlaps(getHitbox(arriba.get(i)))) {
                return true;
            }
        }
        return false;
    }

    private Rectangle getHitbox(Sprite s) {
        return new Rectangle(
            s.getX() + s.getWidth() * 0.15f,
            s.getY(),
            s.getWidth() * 0.7f,
            s.getHeight()
        );
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < abajo.size; i++) {
            abajo.get(i).draw(batch);
            arriba.get(i).draw(batch);
        }
    }

    public void dispose() {
        texAbajo.dispose();
        texArriba.dispose();
    }
}
