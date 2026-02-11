package com.pife.juego.Personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Quokky {

    public enum Skin {
        NORMAL,
        NIEVE,
        CUEVA,
        PIRAMIDE
    }

    private Texture texture_abajo, texture_medio, texture_arriba;
    private Texture texturaActual;
    private FitViewport viewport;

    private float x;
    private float y;
    private float width = 0.75f;
    private float height = 0.75f;

    private float velocidad = 3f;
    private float rotation = 0f;

    private Rectangle hitbox = new Rectangle();

    // Variables para el aleteo
    private float tiempoAleteo = 0f;
    private float intervaloAleteo = 0.12f;
    private int frameAleteo = 0;

    public Quokky(FitViewport viewport, Skin skin) {
        this.viewport = viewport;

        cargarTexturas(skin);

        texturaActual = texture_medio;

        x = 0.3f;
        y = viewport.getWorldHeight() / 2f - height / 2f;
    }

    private void cargarTexturas(Skin skin) {
        // Si había texturas anteriores, las liberamos (por si reutilizas el mismo objeto)
        if (texture_abajo != null) texture_abajo.dispose();
        if (texture_medio != null) texture_medio.dispose();
        if (texture_arriba != null) texture_arriba.dispose();

        if (skin == Skin.NIEVE) {
            // Ajusta estos nombres EXACTOS a tus archivos reales (mayúsculas/minúsculas importan en Android)
            texture_medio = new Texture("Personajes/Quokka_Nieve_Alas medio.png");
            texture_arriba = new Texture("Personajes/Quokka_Nieve_Alas arriba.png");
            // No existe abajo, reutilizamos medio
            texture_abajo = texture_medio;
        } else if(skin == Skin.NORMAL){
            texture_abajo = new Texture("Personajes/quokka_alas_abajo.png");
            texture_medio = new Texture("Personajes/quokka-medio.png");
            texture_arriba = new Texture("Personajes/Quokka_alas_Arriba.png");
        } else if (skin == Skin.CUEVA) {
            texture_abajo = new Texture("Personajes/Quokka_Cueva_Alas abajo.png");
            texture_medio = new Texture("Personajes/Quokka_Cueva_Alas medio.png");
            texture_arriba = new Texture("Personajes/Quokka_Cueva_Alas arriba.png");
        } else if (skin == Skin.PIRAMIDE) {
            texture_abajo = new Texture("Personajes/Quokka_Cueva_Alas abajo.png");
            texture_medio = new Texture("Personajes/Quokka_Cueva_Alas medio.png");
            texture_arriba = new Texture("Personajes/Quokka_Cueva_Alas arriba.png");
        }
    }

    public void update(float delta) {
        float direccionY = 0f;
        float rotacionObjetivo = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            direccionY = 1f;
            rotacionObjetivo = 12f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direccionY = -1f;
            rotacionObjetivo = -12f;
        }

        float inclinacion = Gdx.input.getAccelerometerY();
        if (inclinacion > 1f) {
            direccionY = 1f;
            rotacionObjetivo = 12f;
        } else if (inclinacion < -1f) {
            direccionY = -1f;
            rotacionObjetivo = -12f;
        }

        float nuevaY = y + direccionY * velocidad * delta;
        if (nuevaY < 0f) y = 0f;
        else if (nuevaY > viewport.getWorldHeight() - height) y = viewport.getWorldHeight() - height;
        else y = nuevaY;

        rotation = MathUtils.lerp(rotation, rotacionObjetivo, 8f * delta);

        float tiempo = tiempoAleteo + delta;
        if (tiempo >= intervaloAleteo) {
            tiempo = 0f;

            int frame = frameAleteo + 1;
            if (frame > 4) frame = 0;
            frameAleteo = frame;

            if (frameAleteo == 0 || frameAleteo == 4) texturaActual = texture_abajo;
            else if (frameAleteo == 1 || frameAleteo == 3) texturaActual = texture_medio;
            else texturaActual = texture_arriba;
        }
        tiempoAleteo = tiempo;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
            texturaActual,
            x, y,
            width / 2f, height / 2f,
            width, height,
            1f, 1f,
            rotation,
            0, 0,
            texturaActual.getWidth(),
            texturaActual.getHeight(),
            false, false
        );
    }

    public Rectangle getHitbox() {
        hitbox.set(
            x + width * 0.33f,
            y + height * 0.38f,
            width * 0.34f,
            height * 0.38f
        );
        return hitbox;
    }

    public float getX() { return x; }

    public void dispose() {
        if (texture_abajo != null) texture_abajo.dispose();
        if (texture_medio != null) texture_medio.dispose();
        if (texture_arriba != null) texture_arriba.dispose();
    }
}
