package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class PuzzlePiece {
    public float x;
    public float y;
    public int i;
    public int j;
    public int rotation = 0;
    public float width;
    public float height;
    public Sprite sprite;
    public boolean isSet = false;

    public PuzzlePiece(float x, float y, TextureRegion textureRegion, float width, float height, int i, int j) {
        this.x = x;
        this.y = y;
        this.i = i;
        this.j = j;
        this.width = width;
        this.height = height;
        this.sprite = new Sprite(textureRegion);
        sprite.setSize(width, height);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        sprite.setOriginBasedPosition(x, y);
        sprite.draw(batch);
        sprite.setColor(1, 1, 1, 1);
    }

    public PuzzlePiece setMovedPiece() {
        if (mouseOnPiece() && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            isSet = false;
            setActive();
            return this;
        }
        return null;
    }

    public boolean rotate() {
        if (mouseOnPiece()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                sprite.setRotation(sprite.getRotation() + 90);
                rotation++;
                if (rotation > 3) {
                    rotation = 0;
                }
                return true;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                sprite.setRotation(sprite.getRotation() - 90);
                rotation--;
                if (rotation < 0) {
                    rotation = 3;
                }
                return true;
            }
        }
        return false;
    }

    public int rotateClockwise() {
        sprite.setRotation(sprite.getRotation() - 90);
        rotation--;
        if (rotation < 0) {
            rotation = 3;
        }
        return rotation;
    }

    public boolean setColor() {
        if (mouseOnPiece()) {
            sprite.setColor(1, 1, 1, 0.8f);
            return true;
        }
        return false;
    }

    public void setNotActive() {
        sprite.setSize(100, 100 * height / width);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(x, y);
    }

    public void setActive() {
        sprite.setSize(width, height);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(x, y);
    }

    private boolean mouseOnPiece() {
        Rectangle spriteBounds = sprite.getBoundingRectangle();
        return spriteBounds.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
    }
}
