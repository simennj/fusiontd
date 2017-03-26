package no.fusiontd.game;

import no.fusiontd.screens.PlayScreen;

public class GameController {

    private Map model;
    private PlayScreen view;

    public GameController(Map model, PlayScreen view) {
        this.model = model;
        this.view = view;
    }

}
