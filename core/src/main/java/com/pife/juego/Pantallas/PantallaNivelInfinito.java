package com.pife.juego.Pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pife.juego.Main;

import java.util.Random;

public class PantallaNivelInfinito implements Screen {

    private Main game;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture fondoTex1;
    private Texture fondoTex2;

    private Texture personajeTex;
    private Sprite personaje;

    private float velocidad = 3f;
    private float fondo1X;
    private float fondo2X;
    private float velocidadFondo = 1.5f;


    private Array<Sprite> obstaculosArriba;
    private Array<Sprite> obstaculosAbajo;
    private Texture troncoVerticalTex;
    private Texture troncoGrandeTex;
    private float tiempoAcumulado;
    private float intervaloObstaculo;
    private Random random;
    private float hueco = 1.5f;

    public PantallaNivelInfinito(Main Main) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        // Fondos
        fondoTex1 = new Texture("FondoNivelesQuokky.png");
        fondoTex2 = new Texture("Fondo-Reves.png");

        // Posición inicial
        fondo1X = 0;
        fondo2X = viewport.getWorldWidth();

        // Personaje
        personajeTex = new Texture("Personaje-Quokky.png");
        personaje = new Sprite(personajeTex);
        personaje.setSize(0.75f, 0.75f);
        personaje.setPosition(0.2f, (6f - 2f) / 2f);

        // Obstáculos
        obstaculosArriba = new Array<Sprite>();
        obstaculosAbajo = new Array<Sprite>();
        troncoVerticalTex = new Texture("tronco vertical.png");
        troncoGrandeTex = new Texture("tronco vertical mas largo.png");
        tiempoAcumulado = 0f;
        intervaloObstaculo = 2.5f;
        random = new Random();
    }

    private Rectangle getHitboxPersonaje() {
        return new Rectangle(
            personaje.getX() + personaje.getWidth() * 0.33f,   // centra el cuerpo (ignora cola)
            personaje.getY() + personaje.getHeight() * 0.38f,  // sube el hitbox (incluye cabeza)
            personaje.getWidth() * 0.34f,                      // ancho justo del cuerpo
            personaje.getHeight() * 0.38f                      // altura real del personaje
        );
    }

    private Rectangle getHitboxObstaculo(Sprite obst) {
        return new Rectangle(
            obst.getX() + obst.getWidth() * 0.15f,
            obst.getY(),
            obst.getWidth() * 0.7f,
            obst.getHeight()
        );
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // Movimiento teclado
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            personaje.setY(personaje.getY() + velocidad * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            personaje.setY(personaje.getY() - velocidad * delta);
        }

        // Movimiento móvil
        float inclinacion = Gdx.input.getAccelerometerY();
        float umbral = 1f;
        if (inclinacion > umbral) {
            personaje.setY(personaje.getY() + velocidad * delta);
        }
        if (inclinacion < -umbral) {
            personaje.setY(personaje.getY() - velocidad * delta);
        }

        // Límites
        if (personaje.getY() < 0) {
            personaje.setY(0);
        }
        if (personaje.getY() + personaje.getHeight() > viewport.getWorldHeight()) {
            personaje.setY(viewport.getWorldHeight() - personaje.getHeight());
        }

        // MOVIMIENTO FONDO
        fondo1X = fondo1X - velocidadFondo * delta;
        fondo2X = fondo2X - velocidadFondo * delta;

        if (fondo1X + viewport.getWorldWidth() <= 0) {
            fondo1X = fondo2X + viewport.getWorldWidth();
        }
        if (fondo2X + viewport.getWorldWidth() <= 0) {
            fondo2X = fondo1X + viewport.getWorldWidth();
        }

        // GENERAR OBSTÁCULOS
        tiempoAcumulado = tiempoAcumulado + delta;
        if (tiempoAcumulado >= intervaloObstaculo) {
            tiempoAcumulado = 0;

            // Altura aleatoria del tronco inferior
            float alturaAbajo = 1f + random.nextFloat() * (viewport.getWorldHeight() - hueco - 2f);
            float alturaArriba = viewport.getWorldHeight() - alturaAbajo - hueco;

            // Tronco abajo
            Sprite obstAbajo = new Sprite(troncoVerticalTex);
            float relacionAbajo = obstAbajo.getHeight() / obstAbajo.getWidth();
            obstAbajo.setSize(alturaAbajo / relacionAbajo, alturaAbajo);
            obstAbajo.setPosition(viewport.getWorldWidth(), 0);
            obstaculosAbajo.add(obstAbajo);

            // Tronco arriba
            Sprite obstArriba = new Sprite(troncoGrandeTex);
            float relacionArriba = obstArriba.getHeight() / obstArriba.getWidth();
            obstArriba.setSize(alturaArriba / relacionArriba, alturaArriba);
            obstArriba.setPosition(viewport.getWorldWidth(), alturaAbajo + hueco);
            obstaculosArriba.add(obstArriba);
        }

        // MOVER Y ELIMINAR OBSTÁCULOS
        for (int i = 0; i < obstaculosAbajo.size; i = i + 1) {
            Sprite obst = obstaculosAbajo.get(i);
            obst.setX(obst.getX() - velocidadFondo * delta);
        }
        for (int i = 0; i < obstaculosArriba.size; i = i + 1) {
            Sprite obst = obstaculosArriba.get(i);
            obst.setX(obst.getX() - velocidadFondo * delta);
        }

        // Eliminar los que salen de la pantalla
        for (int i = obstaculosAbajo.size - 1; i >= 0; i = i - 1) {
            if (obstaculosAbajo.get(i).getX() + obstaculosAbajo.get(i).getWidth() < 0) {
                obstaculosAbajo.removeIndex(i);
                obstaculosArriba.removeIndex(i);
            }
        }

        // DIBUJO
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        // Fondos
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float texRatio = (float) fondoTex1.getHeight() / fondoTex1.getWidth();
        float worldRatio = worldHeight / worldWidth;
        float drawWidth;
        float drawHeight;
        if (texRatio > worldRatio) {
            drawWidth = worldWidth;
            drawHeight = worldWidth * texRatio;
        } else {
            drawHeight = worldHeight;
            drawWidth = worldHeight / texRatio;
        }
        float y = (worldHeight - drawHeight) / 2f;

        batch.draw(fondoTex1, fondo1X, y, drawWidth, drawHeight);
        batch.draw(fondoTex2, fondo2X, y, drawWidth, drawHeight);

        // Dibujar obstáculos
        for (int i = 0; i < obstaculosAbajo.size; i = i + 1) {
            obstaculosAbajo.get(i).draw(batch);
            obstaculosArriba.get(i).draw(batch);
        }

        // Dibujar personaje
        personaje.draw(batch);
        batch.end();

        // Colisiones
        Rectangle rPersonaje = getHitboxPersonaje();
        for (int i = 0; i < obstaculosAbajo.size; i = i + 1) {
            if (rPersonaje.overlaps(getHitboxObstaculo(obstaculosAbajo.get(i))) ||
                rPersonaje.overlaps(getHitboxObstaculo(obstaculosArriba.get(i)))) {
                System.out.println("¡Choque!");

                game.setScreen(new PantallaGameOver(game)); //Cambiamos a la pantalla de GameOver al chocar

                break;
            }
        }
    }

    @Override
    public void resize(int width, int height) { viewport.update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        fondoTex1.dispose();
        fondoTex2.dispose();
        personajeTex.dispose();
        troncoVerticalTex.dispose();
        troncoGrandeTex.dispose();
    }
}
