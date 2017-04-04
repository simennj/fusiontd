package no.fusiontd.networking.mpc;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.Scanner;

import no.fusiontd.FusionTD;


public class MPClient {
    private Client client;
    public static Scanner scanner;
    private int timeout = 5000;
    private int tcpPort = 54555;
    private int udpPort = 54556;
    private String serverIP = "localhost";
    private NetworkListener nl;
    private String thisPlayer;

    public MPClient(String serverIP, FusionTD game, String playerName) {
        thisPlayer = playerName;
        scanner = new Scanner(System.in);
        client = new Client();
        registerPackets();
        if(!serverIP.equals("")) {
            this.serverIP = serverIP;
        }
        nl = new NetworkListener();
        nl.init(client, game, playerName);
        //nl.init(client, playerName);
        client.addListener(nl);

        new Thread(client).start();

        try{
            client.connect(timeout, serverIP, tcpPort, udpPort);
        } catch(IOException e){
            e.printStackTrace();
            client.stop();
        }
    }

    public void registerPackets(){
        Kryo kryo = client.getKryo();
        kryo.register(Packet.Packet0LoginRequest.class);
        kryo.register(Packet.Packet1LoginAnswer.class);
        kryo.register(Packet.Packet2Message.class);
        kryo.register(Packet.Packet3Creep.class);
        kryo.register(Packet.Packet4Lives.class);
        kryo.register(Packet.Packet5score.class);
        kryo.register(Packet.Packet6HighScore.class);
        kryo.register(Packet.Packet7TowerPlaced.class);
        kryo.register(Packet.Packet8Meta.class);
        kryo.register(Packet.Packet9PlayerList.class);
        kryo.register(Packet.Packet10RequestPlayerList.class);
        kryo.register(Packet.Packet11RequestOpponent.class);
        kryo.register(Packet.Packet12OpponentAnswer.class);
    }

    public void sendMetaData(String metadata){
        Packet.Packet8Meta metaPacket = new Packet.Packet8Meta();
        metaPacket.metadata = metadata;
        client.sendUDP(metaPacket);
    }

    public void sendMessage(String message) {
        Packet.Packet2Message mPacket = new Packet.Packet2Message();
        mPacket.message = message;
        client.sendUDP(mPacket);
    }

    public void sendCreeps(int number){
        Packet.Packet3Creep cPacket = new Packet.Packet3Creep();
        cPacket.creepNumber = number;
        client.sendUDP(cPacket);
    }

    public void updateLives(int lives){
        Packet.Packet4Lives lifePacket = new Packet.Packet4Lives();
        lifePacket.lives = lives;
        client.sendUDP(lifePacket);
    }

    public void updateScore(int score){
        Packet.Packet5score scorePacket = new Packet.Packet5score();
        scorePacket.score = score;
        client.sendUDP(scorePacket);
    }

    public void sendHighScore(String playerName, int score){
        Packet.Packet6HighScore hsPacket = new Packet.Packet6HighScore();
        hsPacket.player = playerName;
        hsPacket.finalScore = score;
        client.sendUDP(hsPacket);
    }

    public void sendTower(String towerType, float xpos, float ypos){
        Packet.Packet7TowerPlaced tPacket= new Packet.Packet7TowerPlaced();
        tPacket.towerType = towerType;
        tPacket.x = xpos;
        tPacket.y = ypos;
        client.sendUDP(tPacket);
    }

    public void sendMPRequest(int playerid){
        Packet.Packet11RequestOpponent roPacket = new Packet.Packet11RequestOpponent();
        roPacket.sendingPlayer = thisPlayer;
        client.sendUDP(roPacket);
    }

    public void sendMPAnswer(){
        //System.out.println(nl.requestAnswer);
        client.sendUDP(nl.requestAnswer);
    }

    public void sendPlayerListRequest(){
        Packet.Packet10RequestPlayerList rpPacket = new Packet.Packet10RequestPlayerList();
        client.sendUDP(rpPacket);
    }
    /*public static void main(String[] args){
        MPClient mpClient= new MPClient("localhost", "Hax0rmaster1337");
        while(scanner.hasNext()){
            System.out.println("Write something");
            String line = scanner.nextLine();
            if(line.equals("break")){
                mpClient.sendMPRequest(0);
            }
            else if( line.equals("yes")){
                mpClient.sendMPAnswer();
            }
            else{
                mpClient.sendMessage(line);
            }
        }
        //Log.set(Log.LEVEL_DEBUG);
    }*/
}
