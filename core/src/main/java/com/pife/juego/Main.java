package com.pife.juego;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    Sprite im;
    FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("Portada-Quokky.png");
        im=new Sprite(image);
        viewport=new FitViewport(8,5);
        im.setPosition(2,0);
//        im.setSize();
        im.setSize(3,5   );

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void render() {
        // Limpiar la pantalla con un color de fondo que combine
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
//        int screenWidth = Gdx.graphics.getWidth();
//        int screenHeight = Gdx.graphics.getHeight();
//        float imageWidth = image.getWidth();
//        float imageHeight = image.getHeight();
//
//        float imageAspect = imageWidth / imageHeight;
//        float screenAspect = (float) screenWidth / screenHeight;
//
//        float drawWidth, drawHeight;
//        float offsetX = 0, offsetY = 0;
////Gdx.app.log("tam",screenWidth+":"+screenHeight+"   "+imageWidth+":"+imageHeight+"   "+imageAspect);
//        if (imageAspect<1) {
//            drawHeight = screenHeight;
//            drawWidth = drawHeight * imageAspect;
//        }else {
//            drawHeight = screenHeight;
//            drawWidth = drawHeight / imageAspect;
//        }
//        Gdx.app.log("tam",screenHeight+" "+drawWidth+":"+drawHeight );
//
////        if (imageAspect<1) {
////            drawWidth = screenWidth;
////            drawHeight = drawWidth / imageAspect;
////            offsetY = (screenHeight - drawHeight) / 2; //centrado vertical
////        } else {
////            drawHeight = screenHeight;
////            drawWidth = drawHeight * imageAspect;
////            offsetX = (screenWidth - drawWidth) / 2; // centrado horizontal
////        }

        batch.begin();
        im.setSize(3,5);
        im.draw(batch);

//        batch.draw(image,1,1, drawWidth, drawHeight);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
