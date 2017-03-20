package no.fusiontd.networking.mpc;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * Created by Odd on 07.03.2017.
 */

public class NetworkListener extends Listener {
    private Client client;

    public void init(Client client){
        this.client = client;
    }

    public void connected(Connection arg0){
        System.out.println("[CLIENT] You have connected.");
        client.sendTCP(new Packet.Packet0LoginRequest());
    }

    public void disconnected(Connection arg0){
        System.out.println("[CLIENT] You have disconnected.");
    }

    public void received(Connection c, Object o){
        if( o instanceof Packet.Packet1LoginAnswer){
            boolean answer = ((Packet.Packet1LoginAnswer) o).accepted;

            if(answer){
                System.out.println("Please enter your first message for the server!");
                //Log.info("Please enter your first message for the server!");

                while(true){
                    if(MPClient.scanner.hasNext()){
                        Packet.Packet2Message mPacket = new Packet.Packet2Message();
                        mPacket.message = MPClient.scanner.nextLine();
                        client.sendTCP(mPacket);
                        System.out.println("Please enter another message");
                        //Log.info("Please enter another message");
                    }
                }
            } else{
                c.close();
            }

        }
        if( o instanceof Packet.Packet2Message){
            String message = ((Packet.Packet2Message)o).message;
            System.out.println(message);
        }
    }
}
