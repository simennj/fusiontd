package no.fusiontd.game;

import no.fusiontd.FusionTD;

public class
Player {
    private int lives, cash, score;
    private FusionTD game;

    public Player(int lives, int cash, int score, FusionTD game){
        this.lives = lives;
        this.cash = cash;
        this.score = score;
        this.game = game;
    }

    public void addCash(int moreCash) {
        this.cash += moreCash;
    }

    public void loseLives(int lessLives){
        this.lives -= lessLives;
        if (this.lives <= 0){
            System.out.println("You lose");
            game.returnToMenu();
        }
    }

    public void addScore(int score){
        this.score += score;
    }

    public int getLives(){ return lives; }
    public int getCash(){ return cash; }
    public int getScore(){ return score; }

}
