package core;

import beans.ComplexMeasure;
import beans.Measurement;
import beans.Stats;

import java.util.ArrayList;

// Compute stats based on measurements
public class StatsCalculator {

    public static Stats getHousesStats(ArrayList<Measurement> measures)
    {
        double average = 0;
        for (Measurement m : measures)
        {
            average += m.getValue();
        }
        average = average / measures.size();

        double sum = 0;
        for(Measurement m : measures)
        {
            sum += Math.pow((m.getValue() - average), 2);
        }
        sum /= measures.size();
        double stdDev = Math.sqrt(sum);

        return new Stats(average, stdDev);
    }

    public static Stats getComplexStats(ArrayList<ComplexMeasure> measures)
    {
        double average = 0;
        for (ComplexMeasure m : measures)
        {
            average += m.getMeasure();
        }
        average = average / measures.size();

        double sum = 0;
        for(ComplexMeasure m : measures)
        {
            sum += Math.pow((m.getMeasure() - average), 2);
        }
        sum /= measures.size();
        double stdDev = Math.sqrt(sum);

        return new Stats(average, stdDev);
    }
}
