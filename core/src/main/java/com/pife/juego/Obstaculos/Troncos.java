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
    private Array<Boolean> contado = new Array<>(); // marca si el par ya sumó punto

    private Texture texAbajo, texArriba;
    private Random random = new Random();

    private float tiempo = 0f;
    private float intervalo = 2.5f;
    private float hueco = 1.5f;

    private FitViewport viewport;

    // Anchos personalizables
    private float anchoTronco = 1f;
    private float anchoEnredadera = 2f;

    private float huecoMin = 0.65f;
    private float huecoMax = 0.75f;


    private int puntos = 0; // puntuación

    public Troncos(FitViewport viewport) {
        this.viewport = viewport;
        texAbajo = new Texture("tronco vertical.png");
        texArriba = new Texture("Enrredadera_Vertical.png");
    }

    public void update(float delta, float velocidad, float personajeX) {
        tiempo = tiempo + delta;

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

        // Contar puntos: el primer par que haya pasado al personaje
        if (abajo.size > 0) {
            if (contado.first() == false &&
                abajo.first().getX() + abajo.first().getWidth() < personajeX) {
                puntos = puntos + 1; // sumar de forma explícita
                contado.set(0, true); // marcamos como contado
            }
        }

        // Eliminar el par cuando sale de pantalla
        if (abajo.size > 0 && abajo.first().getX() + abajo.first().getWidth() < 0) {
            abajo.removeIndex(0);
            arriba.removeIndex(0);
            contado.removeIndex(0); // eliminamos la marca también
        }
    }

    private void generarPar() {
        float worldH = viewport.getWorldHeight();

        // Hueco variable (más pequeño)
        float huecoActual = huecoMin + random.nextFloat() * (huecoMax - huecoMin);

        // Alturas independientes
        float minAbajo = 0.15f * worldH;
        float maxAbajo = 0.65f * worldH;

        float minArriba = 0.15f * worldH;
        float maxArriba = 0.65f * worldH;

        float alturaAbajo = minAbajo + random.nextFloat() * (maxAbajo - minAbajo);
        float alturaArriba = minArriba + random.nextFloat() * (maxArriba - minArriba);

        // Ajuste de seguridad para que quepan
        float espacioTotal = alturaAbajo + alturaArriba + huecoActual;
        if (espacioTotal > worldH) {
            float exceso = espacioTotal - worldH;
            alturaArriba = alturaArriba - exceso;
        }

        // Tronco abajo
        Sprite sAbajo = new Sprite(texAbajo);
        sAbajo.setSize(anchoTronco, alturaAbajo);
        sAbajo.setPosition(viewport.getWorldWidth(), 0);

        // Enredadera arriba (mucho más cerca)
        Sprite sArriba = new Sprite(texArriba);
        sArriba.setSize(anchoEnredadera, alturaArriba);
        float offsetX = (anchoTronco - anchoEnredadera) / 2f;
        sArriba.setPosition(
            viewport.getWorldWidth() + offsetX,
            worldH - alturaArriba
        );

        abajo.add(sAbajo);
        arriba.add(sArriba);
        contado.add(false);
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

    public int getPuntos() {
        return puntos;
    }

    public void dispose() {
        texAbajo.dispose();
        texArriba.dispose();
    }
}
