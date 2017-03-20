package no.fusiontd.networking.mps;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

/**
 * Created by Odd on 09.03.2017.
 */
public class MPServer {
    private int timeout = 5000;
    private int udpPort = 54556;
    private int tcpPort = 54555;
    private Server server;

    public MPServer(){
        server = new Server();
        server.addListener(new NetworkListener());
        try{
            server.bind(tcpPort);
        } catch(IOException e){
            e.printStackTrace();
        }
        registerPackets();
        server.start();

    }

    private void registerPackets(){
        Kryo kryo = server.getKryo();
        kryo.register(Packet.Packet0LoginRequest.class);
        kryo.register(Packet.Packet1LoginAnswer.class);
        kryo.register(Packet.Packet2Message.class);
    }

    public static void main(String[] args) {
            new MPServer();
            Log.set(Log.LEVEL_DEBUG);
            System.out.println("Server started");
    }

}
