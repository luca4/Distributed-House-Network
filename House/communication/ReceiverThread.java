package communication;

import bean.*;
import data.HouseComplex;
import data.Measures;
import core.ResourceManager;

// Thread which take a message from receiver queue and process it
public class ReceiverThread extends Thread {

    private HouseComplex houseComplex = HouseComplex.getInstance();
    private Measures measures = Measures.getInstance();
    private MessageQueueReceiver messageQueue = MessageQueueReceiver.getInstance();

    @Override
    public void run() {
        while (true) {
            Message message = messageQueue.getMessage();

            if(message == null) continue;

            switch (message.getMessageType()) {
                case WELCOME:
                    System.out.println("Ricevuto messaggio Welcome - id: "+message.getHouseId());
                    houseComplex.addHouse(new House(message.getHouseId(), message.getIpAddress(), message.getListeningPort()));
                    break;
                case EXIT:
                    System.out.println("Ricevuto messaggio Exit - id: "+message.getHouseId());
                    houseComplex.deleteHouse(message.getHouseId());
                    measures.removeMeasure(message.getHouseId());
                    break;
                case ADDMEASURE:
                    measures.addMeasure(message.getHouseId(), message.getValue());
                    break;
                case DATASENT:
                    System.out.println("Consumo complessivo: " + message.getValue());
                    measures.clear();
                    break;
                case REQUESTRESOURCE: {
                    ResourceManager rm = ResourceManager.getInstance();
                    int requesterId = message.getHouseId();

                    if (rm.isUsingResource())
                        rm.addWaitingHouse(message.getHouseId());
                    else if(rm.hasSentResRequest())
                    {
                        if (rm.getRequestTimestamp() < message.getTimestamp())
                            rm.addWaitingHouse(message.getHouseId());
                        else
                            SocketStub.sendResourceOk(requesterId);
                    }
                    else
                        SocketStub.sendResourceOk(requesterId);

                    break;
                }
                case RESOURCEOK: {
                    ResourceManager rm = ResourceManager.getInstance();
                    System.out.println("Arrivato messaggio ResOk - id: " + message.getHouseId());
                    rm.okMessagesUp();
                    break;
                }
            }
        }
    }


}
