package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GdxGame;
import com.mygdx.game.entities.Puzzle;
import com.mygdx.game.entities.PuzzlePiece;
import com.mygdx.game.entities.PuzzleSolver;

public class GameScreen extends ScreenAdapter {
    GdxGame game;
    Puzzle puzzle;
    private Sprite instructionText;
    private ShapeRenderer debugRenderer;

    public GameScreen(GdxGame game, Puzzle puzzle) {
        this.game = game;
        this.puzzle = puzzle;
        debugRenderer = new ShapeRenderer();

        instructionText = new Sprite(new Texture("Fonts/Інструкція.png"));
        instructionText.setSize(1000, 65);
        instructionText.setOriginCenter();
        instructionText.setOriginBasedPosition(Gdx.graphics.getWidth() / 2, instructionText.getHeight());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenu(game));
                }

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        drawField();
        debugRenderer.end();

        game.batch.begin();
        instructionText.draw(game.batch);
        puzzle.draw(game.batch);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            PuzzleSolver.solve(puzzle);
        }
    }

    private void drawField() {
        Vector2 start;
        Vector2 end;
        float pieceWidth = puzzle.pieces.get(0).width;
        float pieceHeight = puzzle.pieces.get(0).height;

        for (int i = 0; i < puzzle.xNum + 1; i++) {
            float x1 = 100 + (i) * pieceWidth;
            float y1 = Gdx.graphics.getHeight() - 100;
            start = new Vector2(x1, y1);
            end = new Vector2(x1, y1 - puzzle.puzzleFieldHeight);

            drawLine(start, end);
        }

        for (int i = 0; i < puzzle.yNum + 1; i++) {
            float x1 = 100;
            float y1 = Gdx.graphics.getHeight() - 100 - (i) * pieceHeight;
            start = new Vector2(x1, y1);
            end = new Vector2(x1 + puzzle.puzzleFieldWidth, y1);

            drawLine(start, end);
        }
    }

    public void drawLine(Vector2 start, Vector2 end) {
        Gdx.gl.glLineWidth(2);
        debugRenderer.setColor(0.5f, 0.5f, 0.5f, 1f);
        debugRenderer.line(start, end);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}

