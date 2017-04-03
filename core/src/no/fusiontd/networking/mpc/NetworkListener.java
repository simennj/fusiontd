package no.fusiontd.networking.mpc;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import no.fusiontd.FusionTD;


public class NetworkListener extends Listener {
    private Client client;
    private Connection connection;
    private FusionTD game;

    public void init(Client client, FusionTD game) {
        this.client = client;
        this.game = game;
    }

    public void connected(Connection conn) {
        System.out.println("[CLIENT] You have connected.");
        Packet.Packet0LoginRequest lPacket = new Packet.Packet0LoginRequest();
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
            int numCreep = ((Packet.Packet3Creep) o).creepnumber;
            System.out.println(numCreep);

        } else if (o instanceof Packet.Packet4Lives) {
            int lives = ((Packet.Packet4Lives) o).lives;

        } else if (o instanceof Packet.Packet5score) {
            int score = ((Packet.Packet5score) o).score;

        } else if (o instanceof  Packet.Packet6HighScore){
            //hmm
        }
    }

    public void sendMessage(String message) {
        Packet.Packet2Message mPacket = new Packet.Packet2Message();
        mPacket.message = message;
        connection.sendUDP(mPacket);
    }

    public void sendCreeps(int number){
        Packet.Packet3Creep cPacket = new Packet.Packet3Creep();
        cPacket.creepnumber = number;
        connection.sendUDP(cPacket);
    }

    public void updateLives(int lives){
        Packet.Packet4Lives lifePacket = new Packet.Packet4Lives();
        lifePacket.lives = lives;
        connection.sendUDP(lifePacket);
    }

    public void updateScore(int score){
        Packet.Packet5score scorePacket = new Packet.Packet5score();
        scorePacket.score = score;
        connection.sendUDP(scorePacket);
    }

    public void sendHighScore(String playerName, int score){
        Packet.Packet6HighScore hsPacket = new Packet.Packet6HighScore();
        hsPacket.player = playerName;
        hsPacket.finalScore = score;
        connection.sendUDP(hsPacket);
    }
}
