package com.pife.juego.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pife.juego.Main;
import com.badlogic.gdx.Input;
import com.pife.juego.Menus.MenuPrincipal;


public class PantallaSeleccionModos implements Screen {

    private Main game;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture fondo;
    private Texture txtBoton;
    private Texture txtBotonVolver;
    private Sprite btnArcade, btnNiveles, btnVolver;
    private Float factorEscaladoFuente;
    private BitmapFont font;

    public PantallaSeleccionModos(Main game) {
        this.game = game;
        font = game.fuente;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(5, 8);

        fondo = new Texture("Fondo-Select-Modos.png");
        txtBoton = new Texture("Boton.png");
        txtBotonVolver = new Texture("Boton_Volver.png");


        btnArcade = new Sprite(txtBoton);
        btnNiveles = new Sprite(txtBoton);
        btnVolver = new Sprite(txtBotonVolver);

        btnArcade.setBounds(1.15f, 5f, 3f, 2f);
        btnNiveles.setBounds(1.15f, 4f, 3f, 2f);
        btnVolver.setBounds(1.15f, 1f, 3f, 2f);

        float dpiScale = Gdx.graphics.getDensity();
        factorEscaladoFuente = (viewport.getWorldHeight() / Gdx.graphics.getHeight()) * dpiScale;
        font.getData().setScale(factorEscaladoFuente * 0.58f);
        font.setColor(Color.WHITE);

        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(fondo, 0, 0, 5, 8);
        btnArcade.draw(batch);
        btnNiveles.draw(batch);
        btnVolver.draw(batch);

        font.draw(batch, "SELECCIONA UN MODO", 1.15f, 8f - 1f);
        font.draw(batch, "ARCADE", btnArcade.getX() + 1f, btnArcade.getY() + 1.1f);
        font.draw(batch, "POR NIVELES", btnNiveles.getX() + 0.6f, btnNiveles.getY() + 1.1f);
        font.draw(batch, "VOLVER", btnVolver.getX() + 1.28f, btnVolver.getY() + 1.1f);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (btnArcade.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("MODO", "ARCADE");
                game.setScreen(new PantallaNivelInfinito(game));
            }
            if (btnNiveles.getBoundingRectangle().contains(touch.x, touch.y)) {
                Gdx.app.log("MODO", "POR NIVELES");
                // game.setScreen(new PantallaNiveles(game));
            }
            if (btnVolver.getBoundingRectangle().contains(touch.x, touch.y)){
                Gdx.app.log("VOLVER", "VOLVER AL MENU PRINCIPAL");
                game.setScreen(new MenuPrincipal(game));
            }
        }

        //Con teclado la tecla Esc vuelve al menú principal y en movil la tecla de Volver del telefono vuelve al menú principal
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(new MenuPrincipal(game));
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
        txtBoton.dispose();
        font.dispose();
    }
}
