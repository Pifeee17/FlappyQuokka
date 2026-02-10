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
import com.pife.juego.Idiomas.Idiomas;
import com.pife.juego.Menus.MenuSeleccionarNivel;


public class PantallaSeleccionModos implements Screen {

    private Main game;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture fondo;
    private Texture txtBoton;
    private Texture txtBotonVolver;
    private Sprite btnArcade, btnNiveles, btnVolver;
    private BitmapFont font;

    public PantallaSeleccionModos(Main game) {
        this.game = game;
        font = game.fuente;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(5, 8);

        //Idiomas
        String idioma = game.getPrefs().getString("idioma", "ES");
        Idiomas.cargar(idioma);

        fondo = new Texture("Pantallas/Fondo-Select-Modos.png");
        txtBoton = new Texture("Botones/Boton.png");
        txtBotonVolver = new Texture("Botones/Boton_Volver.png");

        btnArcade = new Sprite(txtBoton);
        btnNiveles = new Sprite(txtBoton);
        btnVolver = new Sprite(txtBotonVolver);

        btnArcade.setBounds(1.15f, 5f, 3f, 2f);
        btnNiveles.setBounds(1.15f, 4f, 3f, 2f);
        btnVolver.setBounds(1.5f, 1f, 2.5f, 1f);

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

        font.setColor(Color.WHITE);
        font.draw(batch, Idiomas.t("select"), 1.15f, 8f - 1f);

        font.setColor(new Color(0.1f,0.4f,0.1f,1));
        font.draw(batch, Idiomas.t("arcade"), btnArcade.getX() + 1f, btnArcade.getY() + 1.1f);
        font.draw(batch, Idiomas.t("tipes"), btnNiveles.getX() + 0.6f, btnNiveles.getY() + 1.1f);
        font.draw(batch, Idiomas.t("back"), btnVolver.getX() + 1f, btnVolver.getY() + 0.6f);
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
                game.setScreen(new MenuSeleccionarNivel(game));
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
