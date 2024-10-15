package communication;

import com.google.gson.Gson;
import bean.House;
import data.HouseInfo;
import bean.Message;
import data.HouseComplex;
import core.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

// Thread which take a message from sender queue and process it
public class SenderThread extends Thread {

    private Gson gson = new Gson();
    private MessageQueueSender msgQueueSender = MessageQueueSender.getInstance();
    private HouseComplex houseComplex = HouseComplex.getInstance();

    @Override
    public void run() {
        // Reads from MessageQueueSender and send houses (null==broadcast)
        while (true)
        {
            Message message = msgQueueSender.getMessage();
            if(message == null) continue;

            Socket socket = null;
            DataOutputStream toHouse;

            ArrayList<Integer> targets = message.getTargets();
            ArrayList<House> houses = houseComplex.getHouseList();

            for (House h : houses)
            {
                // Do not send to myself if sendToOwner == false
                if (h.getHouseId() == HouseInfo.getHouseId() && !message.sendToOwner()) continue;

                // If targets==null send to all the houses
                if (targets == null || targets.contains(h.getHouseId()))
                {
                    try {
                        socket = new Socket(h.getIpAddr(), h.getListeningPort());
                        toHouse = new DataOutputStream(socket.getOutputStream());
                        toHouse.writeBytes(gson.toJson(message, Message.class));

                    }catch (IOException e) {
                        System.err.println("Errore: Comunicazione con casa con id "+h.getHouseId()+" fallita");
                        }
                    finally {
                        if (socket != null && !socket.isClosed()) {
                            try {
                                socket.close();
                            } catch (IOException e) { e.printStackTrace(); }
                        }
                    }
                }
            }

            // Make sure the "exit" message is sent before program termination
            if(message.getMessageType() == Message.MessageType.EXIT)
                Utils.setCanTerminate(true);
        }
    }
}
