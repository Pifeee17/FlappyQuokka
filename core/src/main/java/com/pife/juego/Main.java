package com.pife.juego;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("Portada-Quokky.png");

        // Quitamos la línea de fullscreen para probar si el problema es ahí.
        // Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());  // Eliminar esta línea temporalmente
    }

    @Override
    public void render() {
        // Limpiar la pantalla
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Obtener las dimensiones de la pantalla y la imagen
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();

        // Imprimir las dimensiones en la consola para depuración
        System.out.println("Screen Width: " + screenWidth);
        System.out.println("Screen Height: " + screenHeight);
        System.out.println("Image Width: " + imageWidth);
        System.out.println("Image Height: " + imageHeight);

        // Calcular las proporciones de la imagen y la pantalla
        float imageAspect = imageWidth / imageHeight;
        float screenAspect = (float) screenWidth / screenHeight;

        // Variables para las dimensiones finales de la imagen ajustada
        float drawWidth, drawHeight;
        float offsetX = 0, offsetY = 0;

        if (imageAspect > screenAspect) {
            // Si la imagen es más ancha que la pantalla, ajustamos el ancho
            drawWidth = screenWidth;
            drawHeight = drawWidth / imageAspect;
            offsetY = (screenHeight - drawHeight) / 2; // Centrado vertical
        } else {
            // Si la imagen es más alta que la pantalla, ajustamos la altura
            drawHeight = screenHeight;
            drawWidth = drawHeight * imageAspect;
            offsetX = (screenWidth - drawWidth) / 2; // Centrado horizontal
        }

        // Dibujar la imagen ajustada
        batch.begin();
        batch.draw(image, offsetX, offsetY, drawWidth, drawHeight);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
