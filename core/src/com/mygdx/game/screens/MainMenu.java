package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.GdxGame;
import com.mygdx.game.entities.Puzzle;
import com.mygdx.game.entities.PuzzlePreview;

import java.util.ArrayList;

public class MainMenu extends ScreenAdapter {
    GdxGame game;
    private Sprite mainMenuText;
    public ArrayList<PuzzlePreview> preview;

    public MainMenu(GdxGame game) {
        this.game = game;

        mainMenuText = new Sprite(new Texture("Fonts/Виберіть картинку.png"));
        mainMenuText.setOriginCenter();
        mainMenuText.setOriginBasedPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - mainMenuText.getHeight());

        preview = new ArrayList<>();
        preview.add(new PuzzlePreview("Puzzles/Кітик.jpg", Gdx.graphics.getWidth() / 2 - 300, 200));
        preview.add(new PuzzlePreview("Puzzles/Гори.jpg", Gdx.graphics.getWidth() / 2, 200));
        preview.add(new PuzzlePreview("Puzzles/Львів.jpg", Gdx.graphics.getWidth() / 2 + 300, 200));
        preview.add(new PuzzlePreview("Puzzles/Метелик.jpg", Gdx.graphics.getWidth() / 2 - 300, 400));
        preview.add(new PuzzlePreview("Puzzles/Серце.jpg", Gdx.graphics.getWidth() / 2, 400));
        preview.add(new PuzzlePreview("Puzzles/Jigsaw_Puzzle.png", Gdx.graphics.getWidth() / 2 + 300, 400));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    for (PuzzlePreview p : preview) {
                        if (p.mouseOnPrev()) {
                            game.setScreen(new GameSettingsScreen(game, new Puzzle(p)));
                        }
                    }
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

        mainMenuText.draw(game.batch);
        for (PuzzlePreview p : preview) {
            p.draw(game.batch);
        }

        game.batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
