package com.pife.juego.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pife.juego.Main;
import com.pife.juego.Personajes.Quokky;
import com.pife.juego.Obstaculos.TroncosEnrredaderas;
import com.pife.juego.Idiomas.Idiomas;

public class PantallaNivelInfinito implements Screen {

    private Main game;
    private SpriteBatch batch;
    private FitViewport viewport;
    // Fondo
    private com.badlogic.gdx.graphics.Texture fondoTex1, fondoTex2;
    private float fondo1X, fondo2X;
    private float velocidadFondo = 1.5f;

    // Entidades
    private Quokky quokky;
    private TroncosEnrredaderas troncos;

    // Font para puntuación
    private BitmapFont font;

    //Musica de fondo

    public PantallaNivelInfinito(Main game) {
        this.game = game;
        viewport = new FitViewport(8, 5);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        //Idiomas
        String idioma = game.getPrefs().getString("idioma", "ES");
        Idiomas.cargar(idioma);


        // Fondo
        fondoTex1 = new com.badlogic.gdx.graphics.Texture("Pantallas/Fondo-Jungla.png");
        fondoTex2 = new com.badlogic.gdx.graphics.Texture("Pantallas/Fondo-Jungla.png");
        fondo1X = 0;
        fondo2X = viewport.getWorldWidth();

        //Musica de Fondo
        game.reproducirMusica("MusicaDeFondo/sonido-jungla.mp3");

        // Entidades
        quokky = new Quokky(viewport, Quokky.Skin.NORMAL);
        troncos = new TroncosEnrredaderas(viewport, TroncosEnrredaderas.Skin.NORMAL, -1); //El -1 es que son pares infinitos

        // Font para puntos usando la fuente de Main
        font = game.fuente;

        // Escalado de fuente según viewport y densidad
        font.setColor(Color.BLACK);
    }

    private void update(float delta) {
        // Fondo
        fondo1X = fondo1X - velocidadFondo * delta;
        fondo2X = fondo2X - velocidadFondo * delta;

        if (fondo1X + viewport.getWorldWidth() <= 0) {
            fondo1X = fondo2X + viewport.getWorldWidth();
        }
        if (fondo2X + viewport.getWorldWidth() <= 0) {
            fondo2X = fondo1X + viewport.getWorldWidth();
        }

        // Entidades
        quokky.update(delta);
        troncos.update(delta, velocidadFondo, quokky.getX());

        // Colisión
        if (troncos.colisiona(quokky.getHitbox())) {

            int puntosActuales = troncos.getPuntos();

            // Obtener récord guardado
            int recordGuardado = game.getPrefs().getInteger("record_infinito", 0);

            // Si supera el récord, lo guardamos
            if (puntosActuales > recordGuardado) {
                game.getPrefs().putInteger("record_infinito", puntosActuales);
                game.getPrefs().flush();
            }

            // true = viene del modo infinito
            game.setScreen(new PantallaGameOver(game,
                new PantallaNivelInfinito(game),
                true));
        }
    }


    @Override
    public void render(float delta) {
        update(delta);

        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        drawFondo();
        troncos.draw(batch);
        quokky.draw(batch);

        // Dibujar puntuación arriba a la izquierda
        font.draw(batch, Idiomas.t("points") +": " + troncos.getPuntos(), 0.1f, viewport.getWorldHeight() - 0.1f);

        batch.end();
    }

    private void drawFondo() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float texRatio = (float) fondoTex1.getHeight() / fondoTex1.getWidth();
        float drawHeight = worldWidth * texRatio;
        float y = (worldHeight - drawHeight) / 2f;

        batch.draw(fondoTex1, fondo1X, y, worldWidth, drawHeight);
        batch.draw(fondoTex2, fondo2X, y, worldWidth, drawHeight);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

    }

    @Override public void pause() {}
    @Override public void resume() {}
    //Al salir de la ventana paramos la musica
    @Override public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        fondoTex1.dispose();
        fondoTex2.dispose();
        quokky.dispose();
        troncos.dispose();
    }
}
