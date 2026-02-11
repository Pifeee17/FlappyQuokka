package com.pife.juego.Obstaculos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

public class TroncosEnrredaderas {

    public enum Skin {
        NORMAL,
        NIEVE,
        CUEVA,
        PIRAMIDE
    }

    private Array<Sprite> abajo = new Array<Sprite>();
    private Array<Sprite> arriba = new Array<Sprite>();
    private Array<Boolean> contado = new Array<Boolean>();

    private Texture texAbajo;
    private Texture texArriba;

    private Random random = new Random();

    private float tiempo = 0f;
    private float intervalo = 2.5f;

    private FitViewport viewport;

    private float anchoTronco = 1f;
    private float anchoEnredadera = 1f;

    private float huecoMin = 0.67f;
    private float huecoMax = 0.8f;

    private int puntos = 0;

    // 🔥 NUEVO (mínimo necesario)
    private int paresGenerados = 0;
    private int maxPares;

    //Constructor con límite
    public TroncosEnrredaderas(FitViewport viewport, Skin skin, int maxPares) {
        this.viewport = viewport;
        this.maxPares = maxPares;
        cargarTexturas(skin);
    }

    private void cargarTexturas(Skin skin) {
        if (texAbajo != null) texAbajo.dispose();
        if (texArriba != null) texArriba.dispose();

        if (skin == Skin.NIEVE) {
            texAbajo = new Texture("Obstaculos/tronco vertical_Nieve.png");
            texArriba = new Texture("Obstaculos/Enrredadera_Vertical_Nieve.png");
        } else if(skin == Skin.NORMAL){
            texAbajo = new Texture("Obstaculos/tronco vertical.png");
            texArriba = new Texture("Obstaculos/Enrredadera_Vertical.png");
        } else if (skin == Skin.CUEVA) {
            texAbajo = new Texture("Obstaculos/Estalagmita.png");
            texArriba = new Texture("Obstaculos/Estalactita.png");
        } else if (skin == Skin.PIRAMIDE) {
            texAbajo = new Texture("Obstaculos/Monta_Calaveras.png");
            texArriba = new Texture("Obstaculos/Farolillo.png");
        }
    }

    public void update(float delta, float velocidad, float personajeX) {
        tiempo = tiempo + delta;

        boolean puedeGenerar;

        if (maxPares == -1) {
            puedeGenerar = true;
        } else {
            puedeGenerar = paresGenerados < maxPares;
        }

        if (tiempo >= intervalo && puedeGenerar) {
            tiempo = 0f;
            generarPar();
            paresGenerados = paresGenerados + 1;
        }

        for (int i = 0; i < abajo.size; i++) {
            Sprite s = abajo.get(i);
            float nuevaX = s.getX() - velocidad * delta;
            s.setX(nuevaX);
        }

        for (int i = 0; i < arriba.size; i++) {
            Sprite s = arriba.get(i);
            float nuevaX = s.getX() - velocidad * delta;
            s.setX(nuevaX);
        }

        if (abajo.size > 0) {
            boolean yaContado = contado.first();
            float limiteX = abajo.first().getX() + abajo.first().getWidth();

            if (yaContado == false && limiteX < personajeX) {
                puntos = puntos + 1;
                contado.set(0, true);
            }
        }

        if (abajo.size > 0) {
            float limiteX = abajo.first().getX() + abajo.first().getWidth();
            if (limiteX < 0) {
                abajo.removeIndex(0);
                arriba.removeIndex(0);
                contado.removeIndex(0);
            }
        }
    }

    //
    public boolean nivelTerminado() {
        if (maxPares == -1) {
            return false;
        }
        return paresGenerados >= maxPares && abajo.size == 0;
    }

    private void generarPar() {
        float worldH = viewport.getWorldHeight();

        float huecoActual = huecoMin + random.nextFloat() * (huecoMax - huecoMin);

        float minAbajo = 0.15f * worldH;
        float maxAbajo = 0.65f * worldH;

        float minArriba = 0.15f * worldH;
        float maxArriba = 0.65f * worldH;

        float alturaAbajo = minAbajo + random.nextFloat() * (maxAbajo - minAbajo);
        float alturaArriba = minArriba + random.nextFloat() * (maxArriba - minArriba);

        float espacioTotal = alturaAbajo + alturaArriba + huecoActual;
        if (espacioTotal > worldH) {
            float exceso = espacioTotal - worldH;
            alturaArriba = alturaArriba - exceso;
        }

        Sprite sAbajo = new Sprite(texAbajo);
        sAbajo.setSize(anchoTronco, alturaAbajo);
        sAbajo.setPosition(viewport.getWorldWidth(), 0f);

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
        if (texAbajo != null) texAbajo.dispose();
        if (texArriba != null) texArriba.dispose();
    }
}
