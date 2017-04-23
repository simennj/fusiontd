package no.fusiontd.game;

import no.fusiontd.screens.PlayScreen;

public class GameController {

    private no.fusiontd.maps.Map model;
    private PlayScreen view;

    public GameController(no.fusiontd.maps.Map model, PlayScreen view) {
        this.model = model;
        this.view = view;
    }

}
