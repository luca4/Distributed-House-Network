package beans;

import javax.xml.bind.annotation.XmlRootElement;

// Save house's computed stats
@XmlRootElement
public class Stats {

    private double average;
    private double stdDeviation;

    public Stats(double average, double stdDeviation)
    {
        this.average = average;
        this.stdDeviation = stdDeviation;
    }

    public double getAverage() { return average; }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getStdDeviation() {
        return stdDeviation;
    }

    public void setStdDeviation(double stdDeviation) {
        this.stdDeviation = stdDeviation;
    }
}
