package beans;

import javax.xml.bind.annotation.XmlRootElement;

// Save house measures
@XmlRootElement
public class House {

    private int houseId, listeningPort;
    private String ipAddr;

    public House() {}

    public House(int houseId, String ipAddr, int listeningPort)
    {
        this.houseId = houseId;
        this.listeningPort = listeningPort;
        this.ipAddr = ipAddr;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public int getListeningPort() {
        return listeningPort;
    }

    public void setListeningPort(int listeningPort) {
        this.listeningPort = listeningPort;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    @Override
    public String toString() {
        return houseId+"-"+ipAddr+"-"+listeningPort;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(obj instanceof House)
        {
            House tmp = (House)obj;
            return this.houseId == tmp.getHouseId();
        }
        else
            return false;
    }
}
