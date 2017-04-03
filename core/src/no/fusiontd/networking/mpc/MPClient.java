package no.fusiontd.networking.mpc;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.Scanner;


public class MPClient {
    public Client client;
    public static Scanner scanner;
    private int timeout = 5000;
    private int tcpPort = 54555;
    private int udpPort = 54556;
    private String serverIP = "localhost";
    private NetworkListener nl;

    public MPClient() {
        scanner = new Scanner(System.in);
        client = new Client();
        registerPackets();

        nl = new NetworkListener();
        nl.init(client);
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
    }

    public static void main(String[] args){
        MPClient mpClient= new MPClient();
        while(scanner.hasNext()){
            System.out.println("Write something");
            String line = scanner.nextLine();
            mpClient.nl.sendMessage(line);
            //mpClient.nl.sendCreeps(10);
        }
        //Log.set(Log.LEVEL_DEBUG);

    }
}
