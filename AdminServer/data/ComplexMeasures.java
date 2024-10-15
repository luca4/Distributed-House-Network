package data;

import beans.ComplexMeasure;

import java.util.ArrayList;
import java.util.Collections;

// Save global measurements. Implements singleton pattern
public class ComplexMeasures {

    private ArrayList<ComplexMeasure> measures = new ArrayList<ComplexMeasure>();
    private static ComplexMeasures instance = null;

    public synchronized static ComplexMeasures getInstance() {
        if(instance == null)
        {
            instance = new ComplexMeasures();
        }
        return instance;
    }

    public synchronized void addMeasure(double measure, long timestamp)
    {
        measures.add(new ComplexMeasure(measure, timestamp));
    }

    public synchronized ArrayList<ComplexMeasure> getLastMeasures(int n)
    {
        ArrayList<ComplexMeasure> tmp = new ArrayList<>(measures);
        tmp.sort(Collections.reverseOrder());

        if (n <= tmp.size())
            return new ArrayList<>(tmp.subList(0, n));
        else
            return tmp;
    }
}
