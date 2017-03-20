package no.fusiontd.networking.mps;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * Created by Odd on 09.03.2017.
 */
public class NetworkListener extends Listener{

    public void connected(Connection arg0){
        System.out.println("[SERVER] Someone is trying to connect");

    }

    public void disconnected(Connection arg0){
        System.out.println("[SERVER] Someone is trying to disconnect");

    }

    public void received(Connection c, Object o) {
        if (o instanceof Packet.Packet0LoginRequest) {
            Packet.Packet1LoginAnswer loginAnswer = new Packet.Packet1LoginAnswer();
            loginAnswer.accepted = true;
            c.sendTCP(loginAnswer);
        }

        if (o instanceof Packet.Packet2Message) {
            String message = ((Packet.Packet2Message) o).message;
            System.out.println(message);
            //Log.info(message);
        }
    }
}
