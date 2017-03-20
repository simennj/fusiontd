package no.fusiontd.components;

import com.artemis.Component;

public class Selectable extends Component{
    boolean isSelectable;

    Selectable(boolean check) {
        isSelectable = check;
    }
}
