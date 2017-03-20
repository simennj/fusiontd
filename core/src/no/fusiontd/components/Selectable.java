package no.fusiontd.components;

import com.badlogic.ashley.core.Component;

public class Selectable implements Component {
    boolean isSelectable;

    Selectable(boolean check) {
        isSelectable = check;
    }
}
