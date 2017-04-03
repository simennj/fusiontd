package no.fusiontd.components;


import com.badlogic.ashley.core.Component;

public class Targeting implements Component {
    public int range;
    public int attackspeed;

    public Targeting(int range, int attackspeed) {
        this.range = range;
        this.attackspeed = attackspeed;
    }
}
