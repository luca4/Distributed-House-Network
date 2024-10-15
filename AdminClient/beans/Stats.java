package beans;

import javax.xml.bind.annotation.XmlRootElement;

// Save statistical data obtained from energetic measurements
@XmlRootElement
public class Stats {

    private double average;
    private double stdDeviation;

    public Stats() {}

    public Stats(double average, double stdDeviation)
    {
        this.average = average;
        this.stdDeviation = stdDeviation;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getStdDeviation() {
        return stdDeviation;
    }

    public void setStdDeviation(double stdDeviation) {
        this.stdDeviation = stdDeviation;
    }

    @Override
    public String toString() {
        return String.valueOf(average).concat(String.valueOf(stdDeviation));
    }
}
