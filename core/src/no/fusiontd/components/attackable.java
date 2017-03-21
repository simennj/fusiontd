package no.fusiontd.components;

import com.badlogic.ashley.core.Component;


public class attackable implements Component {
    public int creepradius;

    public attackable(int newRadius){
        this.creepradius = newRadius;
    }
}
