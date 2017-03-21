package no.fusiontd.components;

import com.badlogic.ashley.core.Component;


public class Attackable implements Component {
    public int creepradius;

    public Attackable(int newRadius){
        this.creepradius = newRadius;
    }
}
