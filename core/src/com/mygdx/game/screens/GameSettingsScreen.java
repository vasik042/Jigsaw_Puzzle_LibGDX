package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GdxGame;
import com.mygdx.game.entities.Puzzle;

public class GameSettingsScreen extends ScreenAdapter {
    GdxGame game;
    Puzzle puzzle;
    private Sprite settingsText;
    private Sprite settingsTextX;
    private Sprite settingsTextY;
    private Sprite doneText;
    private int xNum = 4;
    private int yNum = 4;
    private ShapeRenderer debugRenderer;

    public GameSettingsScreen(GdxGame game, Puzzle puzzle) {
        this.game = game;
        this.puzzle = puzzle;

        settingsText = new Sprite(new Texture("Fonts/Виберіть складність.png"));
        settingsText.setOriginCenter();
        settingsText.setOriginBasedPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - settingsText.getHeight());

        settingsTextX = new Sprite(new Texture("Fonts/Кількість стовпців.png"));
        settingsTextX.setSize(320, 220);
        settingsTextX.setOriginCenter();
        settingsTextX.setOriginBasedPosition(Gdx.graphics.getWidth() / 2 + settingsText.getWidth() / 2 + settingsTextX.getWidth() / 2, Gdx.graphics.getHeight() - settingsTextX.getHeight() * 2);

        settingsTextY = new Sprite(new Texture("Fonts/Кількість рядків.png"));
        settingsTextY.setSize(320, 220);
        settingsTextY.setOriginCenter();
        settingsTextY.setOriginBasedPosition(Gdx.graphics.getWidth() / 2 - settingsText.getWidth() / 2 - settingsTextY.getWidth() / 2, Gdx.graphics.getHeight() - settingsTextY.getHeight() * 2);

        doneText = new Sprite(new Texture("Fonts/Підтвердити.png"));
        doneText.setSize(200, 60);
        doneText.setOriginCenter();
        doneText.setOriginBasedPosition(Gdx.graphics.getWidth() / 2, doneText.getHeight());

        debugRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenu(game));
                }
                if (keyCode == Input.Keys.ENTER) {
                    puzzle.xNum = xNum;
                    puzzle.yNum = yNum;
                    puzzle.createPieces();

                    game.setScreen(new GameScreen(game, puzzle));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();


        settingsText.draw(game.batch);
        settingsTextX.draw(game.batch);
        settingsTextY.draw(game.batch);
        doneText.draw(game.batch);
        puzzle.sprite.draw(game.batch);

        game.batch.end();

        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        selectDifficulty();
        drawLines();
        debugRenderer.end();
    }


    private void selectDifficulty() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && yNum > 2) {
            yNum--;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            yNum++;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && xNum > 2) {
            xNum--;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            xNum++;
        }
    }

    private void drawLines() {
        Vector2 start;
        Vector2 end;
        for (int i = 1; i < xNum; i++) {
            float x1 = puzzle.sprite.getX() + puzzle.sprite.getWidth() / xNum * i;
            float y1 = puzzle.sprite.getY() + puzzle.sprite.getHeight();
            start = new Vector2(x1, y1);
            end = new Vector2(x1, y1 - puzzle.sprite.getHeight());

            drawLine(start, end);
        }

        for (int i = 1; i < yNum; i++) {
            float x1 = puzzle.sprite.getX() + puzzle.sprite.getWidth();
            float y1 = puzzle.sprite.getY() + puzzle.sprite.getHeight() / yNum * i;
            start = new Vector2(x1, y1);
            end = new Vector2(x1 - puzzle.sprite.getWidth(), y1);

            drawLine(start, end);
        }
    }

    public void drawLine(Vector2 start, Vector2 end) {
        Gdx.gl.glLineWidth(1);
        debugRenderer.setColor(0, 0, 0, 1);
        debugRenderer.line(start, end);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}

