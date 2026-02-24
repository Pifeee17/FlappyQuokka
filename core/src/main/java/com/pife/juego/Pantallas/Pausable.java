package com.pife.juego.Pantallas;

import com.badlogic.gdx.Screen;

public interface Pausable {
    void reanudarJuego();   // reanuda la misma instancia
    Screen reiniciar();     // devuelve una pantalla nueva del mismo nivel
}
