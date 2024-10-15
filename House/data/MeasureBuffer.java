package data;

import communication.SocketStub;
import smartMeter.*;

import java.util.ArrayList;

// Manage measurement sliding window
public class MeasureBuffer implements Buffer {

    private ArrayList<Measurement> measurements = new ArrayList<>(24);
    private Measures measures;

    public MeasureBuffer()
    {
        measures = Measures.getInstance();
    }

    @Override
    public void addMeasurement(Measurement m) {

        measurements.add(m);

        if(measurements.size() == 24)
        {
            Measurement res = slide();

            measures.addMeasure(HouseInfo.getHouseId(), res);
            SocketStub.sendMeasure(res);
        }
    }

    private Measurement slide()
    {
        double result = 0;
        for (Measurement m : measurements)
        {
            result += m.getValue();
        }
        result /= 24;

        long timestamp = measurements.get(23).getTimestamp();

        ArrayList<Measurement> tmp = new ArrayList<>(measurements.subList(12, 24));
        measurements = new ArrayList<>(24);
        measurements.addAll(tmp);

        return new Measurement(String.valueOf(HouseInfo.getHouseId()), "plug", result, timestamp);
    }
}
