package data;

import communication.RestStub;
import communication.SocketStub;
import core.Utils;
import smartMeter.Measurement;

import java.util.HashMap;

// Save measurements of all the network houses. Implements pattern singleton
public class Measures {

    private static Measures instance = null;
    private HashMap<Integer, Measurement> values = new HashMap<Integer, Measurement>();
    private HouseComplex houseComplex = HouseComplex.getInstance();

    private Measures() {}

    public synchronized static Measures getInstance()
    {
        if(instance == null)
            instance = new Measures();

        return instance;
    }

    public synchronized void addMeasure(int houseId, Measurement value)
    {
        values.put(houseId, value);

        // IF I have all the data and I'm the coordinator, compute total consumption
        if (houseComplex.getSize() == values.size() && HouseInfo.isCoordinator()) {
            double complexMeasure = 0;
            for (Measurement m : values.values()) {
                complexMeasure += m.getValue();
            }

            long timestpComplexMeasure = Utils.getTimestamp();

            // Program can't terminate while sending data
            Utils.setCanTerminate(false);

            System.out.println("Consumo complessivo: " + complexMeasure);

            // Send data to server
            RestStub.sendDataToServer(new HashMap<>(values), complexMeasure, timestpComplexMeasure);

            SocketStub.sendDATASENTMessage(new Measurement(String.valueOf(HouseInfo.getHouseId()), "plug", complexMeasure, timestpComplexMeasure));

            // Reset actual measurement data
            values.clear();

            Utils.setCanTerminate(true);
        }
    }

    public synchronized void removeMeasure(int houseId)
    {
        values.remove(houseId);
    }

    public synchronized void clear()
    {
        values.clear();
    }
}
