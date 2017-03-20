package no.fusiontd.game;

import com.badlogic.gdx.InputProcessor;
import no.fusiontd.screens.PlayScreen;

public class GameController implements InputProcessor {

    private Map model;
    private PlayScreen view;

    public GameController(Map model, PlayScreen view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int tile = model.getTile(view.getCameraY(screenY), view.getCameraX(screenX));
        if (tile == 4) {
            return false;
        } else if (tile == 0 || tile == 2 || tile == 3) {
            model.placeTower(view.getCameraX(screenX), view.getCameraY(screenY));
            return false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
