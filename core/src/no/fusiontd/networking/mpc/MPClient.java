package no.fusiontd.networking.mpc;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Odd on 07.03.2017.
 */

public class MPClient {
    public Client client;
    public static Scanner scanner;
    private int timeout = 5000;
    private int tcpPort = 54555;
    private int udpPort = 54556;
    private String serverIP = "localhost";

    public MPClient() {
        scanner = new Scanner(System.in);
        client = new Client();
        registerPackets();

        NetworkListener nl = new NetworkListener();
        nl.init(client);
        client.addListener(nl);

        new Thread(client).start();

        try{
            client.connect(timeout, serverIP, tcpPort);
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
    }

    public static void main(String[] args){
        new MPClient();
        Log.set(Log.LEVEL_DEBUG);

    }
}
