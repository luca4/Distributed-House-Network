package beans;

import javax.xml.bind.annotation.XmlRootElement;

// Save complex measures
@XmlRootElement
public class ComplexMeasure implements Comparable<ComplexMeasure> {

    private double measure;
    private Long timestamp;

    public ComplexMeasure() {}

    public ComplexMeasure(double measure, long timestamp) {
        this.measure = measure;
        this.timestamp = timestamp;
    }

    public double getMeasure() {
        return measure;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(ComplexMeasure m) {
        return Long.compare(timestamp, m.getTimestamp());
    }

    @Override
    public String toString() {
        return measure + "-" + timestamp;
    }
}
