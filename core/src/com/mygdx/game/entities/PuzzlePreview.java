package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PuzzlePreview {
    public Sprite sprite;
    public float x;
    public float y;

    public PuzzlePreview(String path, float x, float y) {
        this.sprite = new Sprite(new Texture(path));
        this.x = x;
        this.y = y;
        sprite.setSize(150 * sprite.getWidth() / sprite.getHeight(), 150);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        if (mouseOnPrev()) {
            if (sprite.getHeight() < 170) {
                sprite.setSize(sprite.getWidth() * 1.01f, sprite.getHeight() * 1.01f);
            }
        } else if (sprite.getHeight() > 150) {
            sprite.setSize(sprite.getWidth() / 1.01f, sprite.getHeight() / 1.01f);
        }

        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(x, y);
        sprite.draw(batch);
    }

    public boolean mouseOnPrev() {
        Rectangle spriteBounds = sprite.getBoundingRectangle();
        return spriteBounds.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
    }
}
