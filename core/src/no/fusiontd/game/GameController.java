package no.fusiontd.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;

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
        if (model.getTile(MathUtils.clamp(view.getCameraY(screenY), 0, model.TILEROWS-1),MathUtils.clamp(view.getCameraX(screenX), 0, model.TILECOLS-1)) == 4){
            model.creepSpawn(view.getCameraX(screenX), view.getCameraY(screenY),1);
            return false;
        }
        else if (model.getTile(MathUtils.clamp(view.getCameraY(screenY), 0, model.TILEROWS-1),MathUtils.clamp(view.getCameraX(screenX), 0, model.TILECOLS-1)) == 0 ||
                model.getTile(MathUtils.clamp(view.getCameraY(screenY), 0, model.TILEROWS-1),MathUtils.clamp(view.getCameraX(screenX), 0, model.TILECOLS-1)) == 2 ||
                model.getTile(MathUtils.clamp(view.getCameraY(screenY), 0, model.TILEROWS-1),MathUtils.clamp(view.getCameraX(screenX), 0, model.TILECOLS-1)) == 3 ) {
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
