package communication;

import data.HouseInfo;
import bean.Message;
import core.Utils;
import smartMeter.Measurement;

// Allows houses to communicate
public class SocketStub {

    private static MessageQueueSender messageSender = MessageQueueSender.getInstance();

    public static void sendWelcome()
    {
        Message message = new Message(Message.MessageType.WELCOME);
        message.setHouseId(HouseInfo.getHouseId());
        message.setIpAddress(HouseInfo.getHouseIpAddr());
        message.setListeningPort(HouseInfo.getHousePort());

        messageSender.addMessage(message);
    }

    public static void exitFromNetwork()
    {
        Message message = new Message(Message.MessageType.EXIT);
        message.setHouseId(HouseInfo.getHouseId());

        messageSender.addMessage(message);
    }

    public static void sendMeasure(Measurement measure)
    {
        Message message = new Message(Message.MessageType.ADDMEASURE);
        message.setHouseId(HouseInfo.getHouseId());
        message.setValue(measure);

        messageSender.addMessage(message);
    }

    public static void sendDATASENTMessage(Measurement measure)
    {
        Message message = new Message(Message.MessageType.DATASENT);
        message.setHouseId(HouseInfo.getHouseId());
        message.setValue(measure);

        messageSender.addMessage(message);
    }

    public static void sendResourceRequest()
    {
        Message message = new Message(Message.MessageType.REQUESTRESOURCE);
        message.setHouseId(HouseInfo.getHouseId());
        message.setSendToOwner(true);
        message.setIpAddress(HouseInfo.getHouseIpAddr());
        message.setListeningPort(HouseInfo.getHousePort());
        message.setTimestamp(Utils.getTimestamp());

        messageSender.addMessage(message);
    }

    public static void sendResourceOk(int target)
    {
        Message message = new Message(Message.MessageType.RESOURCEOK);
        message.setHouseId(HouseInfo.getHouseId());
        message.setSendToOwner(true);
        message.addTarget(target);

        messageSender.addMessage(message);
    }
}