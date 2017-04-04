package no.fusiontd.game;

public class Player {
    private int lives, cash, score;

    public Player(int lives, int cash, int score){

        this.lives = lives;
        this.cash = cash;
        this.score = score;
    }

    public void addCash(int moreCash) {
        this.cash += moreCash;
    }

    public void loseLives(int lessLives){
        this.lives -= lessLives;
        if (this.lives <= 0){
            // Meet the Grim Reaper
        }
    }

    public void addScore(int score){
        this.score += score;
    }

}
