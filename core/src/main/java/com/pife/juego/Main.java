package com.pife.juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pife.juego.Menus.MenuPrincipal;

public class Main extends Game {

    public BitmapFont fuente;
    private Viewport viewport;
    private float escalado;
    @Override
    public void create() {

        viewport = new FitViewport(5,8);
        fuente = new BitmapFont((Gdx.files.internal("Fuente/Early GameBoy.fnt")));
        escalado = viewport.getWorldHeight() / (60f * 10);
        fuente.getData().setScale(escalado * 0.6f);
        fuente.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        fuente.setUseIntegerPositions(false);
        //Mostramos el menú principal
        setScreen(new MenuPrincipal(this));


    }
}
