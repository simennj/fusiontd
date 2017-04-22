package no.fusiontd.game;

class CreepBluePrint {
    final String texture;
    final int life;
    final float speed;
    final int reward;
    CreepBluePrint(String texture, int life, float speed, int reward) {
        this.texture = texture;
        this.life = life;
        this.speed = speed;
        this.reward = reward;
    }
}
