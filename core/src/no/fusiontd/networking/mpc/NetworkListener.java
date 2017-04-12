package no.fusiontd.networking.mpc;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.List;

import no.fusiontd.FusionTD;
import no.fusiontd.game.Player;


public class NetworkListener extends Listener {
    private Client client;
    private Connection connection;
    private FusionTD game;
    private String playerName, requestAnswerString, requestString;
    private List<String> playerList;
    public Packet.Packet12OpponentAnswer requestAnswer;

    public void init(Client client, FusionTD game, String playerName) {
        this.client = client;
        this.game = game;
        this.playerName = playerName;
        this.requestString = "No requests yet :C";
        playerList = new ArrayList<String>();
    }

    public void connected(Connection conn) {
        System.out.println("[CLIENT] You have connected.");
        Packet.Packet0LoginRequest lPacket = new Packet.Packet0LoginRequest();
        lPacket.playerName = playerName;
        conn.sendUDP(lPacket);
        connection = conn;
    }

    public void disconnected(Connection conn) {
        System.out.println("[CLIENT] You have disconnected.");
        connection = null;
    }

    public void received(Connection c, Object o) {

        if (o instanceof Packet.Packet1LoginAnswer) {
            boolean answer = ((Packet.Packet1LoginAnswer) o).accepted;
            if (answer) {
                System.out.println("Logged in");
            }

        } else if (o instanceof Packet.Packet2Message) {
            String message = ((Packet.Packet2Message) o).message;
            System.out.println(message);

        } else if (o instanceof Packet.Packet3Creep) {
            int numCreep = ((Packet.Packet3Creep) o).creepNumber;
            System.out.println(numCreep);

        } else if (o instanceof Packet.Packet4Lives) {
            int lives = ((Packet.Packet4Lives) o).lives;

        } else if (o instanceof Packet.Packet5score) {
            int score = ((Packet.Packet5score) o).score;

        } else if (o instanceof  Packet.Packet6HighScore){
            //hmm

        } else if (o instanceof Packet.Packet7TowerPlaced){
            String type = ((Packet.Packet7TowerPlaced) o).towerType;
            float xpos = ((Packet.Packet7TowerPlaced) o).x;
            float ypos = ((Packet.Packet7TowerPlaced) o).y;
            //place tower function here

        }else if (o instanceof Packet.Packet8Meta){
            //set up the game

        } else if ( o instanceof Packet.Packet9PlayerList){
            playerList = ((Packet.Packet9PlayerList) o).availablePlayers;
        }

        else if( o instanceof Packet.Packet11RequestOpponent){
            requestAnswer = new Packet.Packet12OpponentAnswer();
            requestAnswer.accepted = true;
            requestAnswer.playerName = playerName;
            requestAnswer.playerId = ((Packet.Packet11RequestOpponent) o).sendingPlayerId; // playerid of playerId who sent request
            String player = ((Packet.Packet11RequestOpponent) o).sendingPlayer;
            requestString = player + " requests your assistance!!!, Accept?";
            System.out.println(requestString);
        }

        else if( o instanceof Packet.Packet12OpponentAnswer){
            if(((Packet.Packet12OpponentAnswer) o).accepted){
                requestAnswerString = ((Packet.Packet12OpponentAnswer) o).playerName + " has accepted your request!";
                //game.selectMap(); //Launches mapselected multiplayer, however can't do it from here
            }
            else{
                requestAnswerString = ((Packet.Packet12OpponentAnswer) o).playerName + " has declined your request!";
            }
            System.out.println(requestAnswerString);
        }
    }

    public List<String> getPlayerList(){
        return playerList;
    }

    public String getRequestAnswerString(){
        return requestAnswerString;
    }

    public String getRequestString() {
        return requestString;
    }
}
