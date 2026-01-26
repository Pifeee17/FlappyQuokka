package com.pife.juego.Personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Quokky {
    private Sprite sprite;
    private float velocidad = 3f;
    private FitViewport viewport;

    public Quokky(FitViewport viewport) {
        this.viewport = viewport;

        Texture tex = new Texture("Personaje-Quokky.png");
        sprite = new Sprite(tex);
        sprite.setSize(0.75f, 0.75f);
        sprite.setPosition(0.3f, viewport.getWorldHeight() / 2f - sprite.getHeight() / 2f);
    }

    public void update(float delta) {
        // Teclado
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            sprite.setY(sprite.getY() + velocidad * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            sprite.setY(sprite.getY() - velocidad * delta);
        }

        // Acelerómetro (móvil)
        float inclinacion = Gdx.input.getAccelerometerY();
        if (inclinacion > 1f) sprite.setY(sprite.getY() + velocidad * delta);
        if (inclinacion < -1f) sprite.setY(sprite.getY() - velocidad * delta);

        // Límites
        sprite.setY(MathUtils.clamp(
            sprite.getY(),
            0,
            viewport.getWorldHeight() - sprite.getHeight()
        ));
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Rectangle getHitbox() {
        return new Rectangle(
            sprite.getX() + sprite.getWidth() * 0.33f,
            sprite.getY() + sprite.getHeight() * 0.38f,
            sprite.getWidth() * 0.34f,
            sprite.getHeight() * 0.38f
        );
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }

    public float getX() {
        return sprite.getX();
    }
}
