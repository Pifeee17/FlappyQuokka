package com.pife.juego;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pife.juego.Menus.MenuPrincipal;

public class Main extends Game {

    public BitmapFont fuente;

    private Viewport viewport;
    private float escalado;

    // Ajustes
    private Preferences prefs;

    // Música (UNA sola para todo el juego)
    private Music musicaActual;
    private String musicaActualPath;
    private String musicaObjetivoPath;


    @Override
    public void create() {

        viewport = new FitViewport(5, 8);

        prefs = Gdx.app.getPreferences("ajustes");

        fuente = new BitmapFont(Gdx.files.internal("Fuente/Early GameBoy.fnt"));
        fuente.setUseIntegerPositions(false);
        fuente.setColor(Color.WHITE);

        // 👇 ESTA LÍNEA ES LA QUE FALTA
        setScreen(new MenuPrincipal(this));
    }

    // --- GET PREFS (para vibración, idioma, etc.) ---
    public Preferences getPrefs() {
        return prefs;
    }

    // --- MUSICA GLOBAL ---
    public void reproducirMusica(String archivo) {

        // Guardamos cuál es la música que DEBERÍA sonar en esta pantalla
        musicaObjetivoPath = archivo;

        boolean musicaOn = prefs.getBoolean("musicaOn", true);
        float volumen = prefs.getFloat("volumen", 0.5f);

        if (!musicaOn) {
            pararMusica();
            return;
        }

        if (musicaActual != null && archivo.equals(musicaActualPath)) {
            musicaActual.setVolume(volumen);
            if (!musicaActual.isPlaying()) {
                musicaActual.play();
            }
            return;
        }

        pararMusica();
        musicaActualPath = archivo;
        musicaActual = Gdx.audio.newMusic(Gdx.files.internal(archivo));
        musicaActual.setLooping(true);
        musicaActual.setVolume(volumen);
        musicaActual.play();
    }


    public void setMusicaActiva(boolean activa) {
        prefs.putBoolean("musicaOn", activa);
        prefs.flush();

        if (!activa) {
            pararMusica();
        } else {

            // Si ya hay música cargada, play
            if (musicaActual != null) {
                musicaActual.play();
            } else {

                // Si no hay música cargada, intenta reproducir la que "toca" en esta pantalla
                if (musicaObjetivoPath != null) {
                    reproducirMusica(musicaObjetivoPath);
                }
            }
        }
    }


    public void setVolumenMusica(float volumen) {
        prefs.putFloat("volumen", volumen);
        prefs.flush();

        if (musicaActual != null) musicaActual.setVolume(volumen);
    }

    public void pararMusica() {
        if (musicaActual != null) {
            musicaActual.stop();
            musicaActual.dispose();
            musicaActual = null;

        }
    }

    @Override
    public void dispose() {
        pararMusica();
        if (fuente != null) fuente.dispose();
        super.dispose();
    }
}
