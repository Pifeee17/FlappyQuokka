package com.pife.juego;

import com.badlogic.gdx.Game;
import com.pife.juego.Menus.MenuPrincipal;

public class Main extends Game {

    @Override
    public void create() {
        //Mostramos el menú principal
        setScreen(new MenuPrincipal(this));
    }
}
