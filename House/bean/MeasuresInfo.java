package bean;

import smartMeter.Measurement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

// Save Measure info. Allows to send info to server
@XmlRootElement(name = "MeasuresInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class MeasuresInfo {

    private HashMap<Integer, Measurement> localMeasures;
    private long sendTimestamp;
    private double complexMeasure;
    private long timestampComplexMeasure;

    public MeasuresInfo() {}

    public MeasuresInfo(HashMap<Integer, Measurement> localMeasures, long sendTimestamp, double complexMeasure, long timestampComplexMeasure) {
        this.localMeasures = localMeasures;
        this.sendTimestamp = sendTimestamp;
        this.complexMeasure = complexMeasure;
        this.timestampComplexMeasure = timestampComplexMeasure;
    }

    public HashMap<Integer, Measurement> getLocalMeasures() {
        return localMeasures;
    }

    public long getSendTimestamp() {
        return sendTimestamp;
    }

    public double getComplexMeasure() {
        return complexMeasure;
    }

    public long getTimestampComplexMeasure() {
        return timestampComplexMeasure;
    }

    @Override
    public String toString() {
        return localMeasures.toString()+"-"+sendTimestamp+"-"+complexMeasure+"-"+timestampComplexMeasure;
    }
}
