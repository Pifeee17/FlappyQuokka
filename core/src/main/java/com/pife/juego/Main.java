package com.pife.juego;

import com.badlogic.gdx.Game;
import com.pife.juego.Menus.MenuPrincipal;

public class Main extends Game {

    @Override
    public void create() {
        // Al iniciar el juego mostramos directamente el menú
        setScreen(new MenuPrincipal(this));
    }
}
