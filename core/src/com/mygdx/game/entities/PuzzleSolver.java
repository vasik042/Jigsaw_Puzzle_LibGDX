package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;

public class PuzzleSolver {
    public static void solve(Puzzle puzzle) {
        for (PuzzlePiece p : puzzle.pieces) {
            p.x = 100 + p.width * (p.i + 0.5f);
            p.y = Gdx.graphics.getHeight() - 100 - p.height * (p.j + 0.5f);
            p.sprite.setRotation(0);
            p.rotation = 0;
            p.setActive();
            p.isSet = true;
        }
        if (puzzle.isSolved()) {
            puzzle.solved();
        }
    }
}
