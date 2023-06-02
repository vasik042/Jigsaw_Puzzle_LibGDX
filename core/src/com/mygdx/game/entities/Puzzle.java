package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Iterator;
import java.util.LinkedList;

public class Puzzle {
    public int xNum;
    public int yNum;
    public float puzzleFieldWidth;
    public float puzzleFieldHeight;
    public Sprite sprite;
    public Sprite piecesField;
    public Sprite congratulations;
    public float congratulationsFade = 0;
    public LinkedList<PuzzlePiece> pieces;
    public PuzzlePiece movedPiece;
    public boolean solved;

    public Puzzle(PuzzlePreview preview) {
        sprite = preview.sprite;
        sprite.setSize(sprite.getWidth() * 2, sprite.getHeight() * 2);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        pieces = new LinkedList<>();

        piecesField = new Sprite(new Texture("Fonts/Поле для пазлів.png"));
        piecesField.setPosition(Gdx.graphics.getWidth() - 300, 100);

        congratulations = new Sprite(new Texture("Fonts/Вітання.png"));
        congratulations.setOriginCenter();
        congratulations.setOriginBasedPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
        piecesField.draw(batch);

        if (!solved) {
            for (PuzzlePiece p : pieces) {
                if (p.setColor()) {
                    break;
                }
            }
            for (PuzzlePiece p : pieces) {
                if (p.rotate()) {
                    if (isSolved()) {
                        solved();
                    }
                    break;
                }
            }
            selectMovedPiece();
        }

        Iterator<PuzzlePiece> iterator = pieces.descendingIterator();
        while (iterator.hasNext()) {
            iterator.next().draw(batch);
        }

        if (solved) {
            if (congratulationsFade < 0.8f) {
                congratulationsFade += 0.01f;
            }
            congratulations.setColor(1, 1, 1, congratulationsFade);
            congratulations.draw(batch);
        }
    }

    private void setPiece() {
        if (isOnField(movedPiece.x, movedPiece.y)) {
            for (int i = 0; i < xNum; i++) {
                for (int j = 0; j < yNum; j++) {
                    if (movedPiece.x > 100 + movedPiece.width * i
                            && movedPiece.x < 100 + movedPiece.width * (i + 1)
                            && movedPiece.y < Gdx.graphics.getHeight() - 100 - movedPiece.height * j
                            && movedPiece.y > Gdx.graphics.getHeight() - 100 - movedPiece.height * (j + 1)) {

                        movedPiece.x = 100 + movedPiece.width * (i + 0.5f);
                        movedPiece.y = Gdx.graphics.getHeight() - 100 - movedPiece.height * (j + 0.5f);
                        movedPiece.isSet = true;
                        movedPiece.setActive();

                        replacePiece(movedPiece);
                        break;
                    }
                }
            }
        } else {
            movedPiece.setNotActive();
        }
        movedPiece = null;
    }

    private void replacePiece(PuzzlePiece movedPiece) {
        for (PuzzlePiece p : pieces) {
            if (!p.equals(movedPiece) && movedPiece.x == p.x) {
                if (movedPiece.y == p.y) {
                    p.x = 1140 + (int) (Math.random() * 60);
                    p.y = 150 + (int) (Math.random() * 300);
                    p.setNotActive();
                    p.isSet = false;
                }
            }
        }
    }

    private boolean isOnField(float x, float y) {
        return x > 100
                && x < 100 + puzzleFieldWidth
                && y < Gdx.graphics.getHeight() - 100
                && y > Gdx.graphics.getHeight() - 100 - puzzleFieldHeight;
    }

    private void selectMovedPiece() {
        if (movedPiece == null) {
            for (PuzzlePiece p : pieces) {
                movedPiece = p.setMovedPiece();
                if (movedPiece != null) {
                    pieces.remove(movedPiece);
                    pieces.addFirst(movedPiece);
                    break;
                }
            }
        }


        if (movedPiece != null) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                movedPiece.x = Gdx.input.getX();
                movedPiece.y = Gdx.graphics.getHeight() - Gdx.input.getY();
            } else {
                setPiece();
                if (isSolved()) {
                    solved();
                }
            }
        }
    }

    public boolean isSolved() {
        for (PuzzlePiece p : pieces) {
            if (!p.isSet) {
                return false;
            }
        }
        return true;
    }

    public void solved() {
        for (PuzzlePiece p : pieces) {
            if (p.x != 100 + p.width * (p.i + 0.5f)
                    || p.y != Gdx.graphics.getHeight() - 100 - p.height * (p.j + 0.5f)
                    || p.rotation != 0) {
                return;
            }
        }
        solved = true;
    }


    private void setPuzzleField() {
        puzzleFieldWidth = sprite.getWidth() / sprite.getHeight() * 500;
        puzzleFieldHeight = 500;

        sprite.setSize(200, sprite.getHeight() / sprite.getWidth() * 200);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(Gdx.graphics.getWidth() - sprite.getWidth() / 2 - 100, Gdx.graphics.getHeight() - sprite.getHeight() / 2 - 100);
    }

    public void createPieces() {
        setPuzzleField();

        Texture texture = sprite.getTexture();

        float pieceWidth = puzzleFieldWidth / xNum;
        float pieceHeight = puzzleFieldHeight / yNum;

        int textureRegionWidth = texture.getWidth() / xNum;
        int textureRegionHeight = texture.getHeight() / yNum;

        for (int i = 0; i < xNum; i++) {
            for (int j = 0; j < yNum; j++) {
                TextureRegion textureRegion = new TextureRegion(texture, textureRegionWidth * i, textureRegionHeight * j, textureRegionWidth, textureRegionHeight);

                PuzzlePiece puzzlePiece = new PuzzlePiece(1140 + (int) (Math.random() * 60), 160 + (int) (Math.random() * 280), textureRegion, pieceWidth, pieceHeight, i, j);
                puzzlePiece.rotation = (int) (Math.random() * 4);
                puzzlePiece.sprite.setRotation(90 * puzzlePiece.rotation);

                pieces.add(puzzlePiece);
            }
        }
        for (PuzzlePiece p : pieces) {
            p.setNotActive();
        }
    }
}
