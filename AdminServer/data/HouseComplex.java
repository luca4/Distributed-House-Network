package data;

import beans.House;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

// Save house that are added in the complex. Implements singleton pattern
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HouseComplex {

    private static HouseComplex instance = null;
    private ArrayList<House> houseList = new ArrayList<House>();

    private HouseComplex() {  }

    //for singleton paradigm
    public synchronized static HouseComplex getInstance() {
        if(instance == null)
        {
            instance = new HouseComplex();
        }
        return instance;
    }

    public synchronized ArrayList<House> getHouseList() {
        return new ArrayList<House>(houseList);
    }

    public synchronized boolean addHouse(House house)
    {
        //TODO: Due case entrano contemporaneamente
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if(!houseExist(house)) {
            houseList.add(house);

            return true;
        }
        else
            return false;
    }

    private synchronized boolean houseExist(House house)
    {
        if(houseList.contains(house))
            return true;
        else
            return false;
    }

    public synchronized boolean deleteHouse(int houseId)
    {
        House h;
        for(int i=0; i<houseList.size(); i++)
        {
            h = houseList.get(i);

            if (h.getHouseId() == houseId) {
                houseList.remove(h);
                return true;
            }
        }
        return false;
    }
}
