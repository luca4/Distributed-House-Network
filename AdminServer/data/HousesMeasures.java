package data;

import beans.HouseMeasure;
import beans.Measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


// Save house measurements. Implements singleton pattern
public class HousesMeasures {

    private ArrayList<HouseMeasure> measures = new ArrayList<HouseMeasure>();
    private static HousesMeasures instance = null;

    public synchronized static HousesMeasures getInstance() {
        if(instance == null)
        {
            instance = new HousesMeasures();
        }
        return instance;
    }

    public synchronized void addMeasure(HashMap<Integer, Measurement> measures, long sendTimestamp)
    {
       this.measures.add(new HouseMeasure(measures, sendTimestamp));
    }

    public synchronized ArrayList<Measurement> getLastHouseMeasures(int n, int houseId)
    {
        ArrayList<HouseMeasure> tmp = new ArrayList<>(measures);
        ArrayList<Measurement> res = new ArrayList<>();

        tmp.sort(Collections.reverseOrder());

        if(n > tmp.size()) n = tmp.size();

        for (int i=0; i<n; i++)
        {
            Measurement m = tmp.get(i).getMeasures().get(houseId);
            if (m != null)
                res.add(m);
        }
        return res;
    }

    public synchronized void deleteData(int houseId)
    {
        for (HouseMeasure measure : measures)
        {
            measure.deleteData(houseId);
        }
    }
}
