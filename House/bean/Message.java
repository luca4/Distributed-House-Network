package bean;

import smartMeter.Measurement;

import java.util.ArrayList;

// Represents a message sent in the P2P network
public class Message {

    public enum MessageType{
        WELCOME,
        EXIT,
        ADDMEASURE,
        DATASENT,
        REQUESTRESOURCE,
        RESOURCEOK
    }

    private MessageType messageType;
    private Integer houseId = null;
    private String ipAddress = null;
    private Integer listeningPort = null;
    private Measurement value = null;
    private Long timestamp = null;
    private ArrayList<Integer> targets = null; //se null allora è broadcast
    private boolean sendToOwner = false;

    public Message(MessageType messageType)
    {
        this.messageType = messageType;
    }

    public MessageType getMessageType(){
        return messageType;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getListeningPort() {
        return listeningPort;
    }

    public void setListeningPort(Integer listeningPort) {
        this.listeningPort = listeningPort;
    }

    public Measurement getValue() {
        return value;
    }

    public void setValue(Measurement value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<Integer> getTargets() {
        return targets;
    }

    public void addTarget(int t) {
        if(targets==null)
            targets = new ArrayList<>();

        targets.add(t);
    }

    public boolean sendToOwner() {
        return sendToOwner;
    }

    public void setSendToOwner(boolean sendToOwner) {
        this.sendToOwner = sendToOwner;
    }
}
