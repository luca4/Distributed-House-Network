package core;

import data.HouseInfo;
import communication.ReceiverThreadPool;
import communication.RestStub;
import communication.SenderThreadPool;
import communication.SocketStub;
import data.MeasureBuffer;
import smartMeter.SmartMeterSimulator;

public class Main {

    public static void main(String[] args) {

        SmartMeterSimulator smartMeterSimulator;

        checkParams(args);
        enterInServer();
        SocketStub.sendWelcome();

        // Initialize threads that process in/out messages
        new ReceiverThreadPool();
        new SenderThreadPool();

        MeasureBuffer buffer = new MeasureBuffer();
        smartMeterSimulator = new SmartMeterSimulator(String.valueOf(HouseInfo.getHouseId()), buffer);
        smartMeterSimulator.start();

        // Start thread that receive messages
        new Server().start();

        ResourceManager r = ResourceManager.getInstance();
        r.setSimulator(smartMeterSimulator);

        // Start thread that receive console messages
        new Console().start();
    }

    // Check program input parameters
    private static void checkParams(String[] args)
    {
        if (args.length != 4)
        {
            System.err.println("Errore: Inserisci 4 parametri non nulli e non con valore 0 - houseId, houseListeningPort, serverIpAddress, serverPort");
            System.exit(1);
        }

        int id = -1;
        int listeningPort = -1;
        String serverIpAddr = "";
        int serverPort = -1;

        try {
            id = Integer.parseInt(args[0]);
            listeningPort = Integer.parseInt(args[1]);
            serverIpAddr = args[2];
            serverPort = Integer.parseInt(args[3]);
        }catch(NumberFormatException e)
        {
            System.err.println("Errore nel parsing dei parametri - inserisci in forma \"intero, intero, stringa, intero\"");
            System.exit(1);
        }

        if (id<=0 || listeningPort<=0 || serverIpAddr.isEmpty() || serverPort<=0)
        {
            System.err.println("Errore: Uno o più valori nulli o 0");
            System.exit(1);
        }

        HouseInfo.setHouseId(id);
        HouseInfo.setHousePort(listeningPort);
        HouseInfo.setServerIpAddr(serverIpAddr);
        HouseInfo.setServerPort(serverPort);
        HouseInfo.setHouseIpAddr("localhost");
    }

    // Notify the server of house entering in the network making sure the house has unique id
    private static void enterInServer()
    {
        boolean status = RestStub.insertHouse(HouseInfo.getHouseId(), HouseInfo.getHouseIpAddr(), HouseInfo.getHousePort());
        if (!status)
        {
            System.exit(1);
        }
    }
}
