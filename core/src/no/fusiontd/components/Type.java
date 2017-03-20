package no.fusiontd.components;

import com.badlogic.ashley.core.Component;

public class Type implements Component {
    String type;

    public Type(String newT){
        this.type = newT;
    }
}
