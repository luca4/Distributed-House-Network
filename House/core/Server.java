package core;

import com.google.gson.Gson;
import data.HouseInfo;
import bean.Message;
import communication.MessageQueueReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    @Override
    public void run() {
        super.run();

        MessageQueueReceiver messageQueueReceiver = MessageQueueReceiver.getInstance();
        Gson gson = new Gson();

        ServerSocket welcomeSocket;
        BufferedReader fromHouse;

        try {
            welcomeSocket = new ServerSocket(HouseInfo.getHousePort());

            while (true)
            {
                Socket connSocket = welcomeSocket.accept();

                fromHouse = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
                Message m = gson.fromJson(fromHouse.readLine(), Message.class);

                messageQueueReceiver.addMessage(m);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
