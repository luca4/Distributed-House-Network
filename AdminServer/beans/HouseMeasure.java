package beans;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

// Save measure of a group of houses
@XmlRootElement
public class HouseMeasure implements Comparable<HouseMeasure> {

    private HashMap<Integer, Measurement> measures;
    private long sendTimestamp;

    public HouseMeasure(HashMap<Integer, Measurement> measures, long sendTimestamp) {
        this.measures = measures;
        this.sendTimestamp = sendTimestamp;
    }

    public void deleteData(int houseId)
    {
        measures.remove(houseId);
    }

    public HashMap<Integer, Measurement> getMeasures() {
        return measures;
    }

    public long getSendTimestamp() {
        return sendTimestamp;
    }

    @Override
    public int compareTo(HouseMeasure m) {
        return Long.compare(sendTimestamp, m.getSendTimestamp());
    }

    @Override
    public String toString() {
        return measures.toString() + "--" + sendTimestamp;
    }
}
