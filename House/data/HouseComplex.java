package data;

import bean.House;
import java.util.ArrayList;

// Save house that are added in the complex. Implements singleton pattern
public class HouseComplex {

    private static HouseComplex instance = null;
    private ArrayList<House> houseList = new ArrayList<House>();

    private HouseComplex() { }

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

    public synchronized int getSize()
    {
        return houseList.size();
    }

    public synchronized boolean addHouse(House house)
    {
        if(!houseExist(house)) {

            houseList.add(house);

            if (getMaxHouseId() == HouseInfo.getHouseId())
                HouseInfo.setIsCoordinator(true);
            else
                HouseInfo.setIsCoordinator(false);

            return true;
        }
        else
            return false;
    }

    // Return max house's id
    private synchronized int getMaxHouseId()
    {
        int max = 0;
        for (House h : houseList)
        {
            if(h.getHouseId() > max)
                max = h.getHouseId();
        }
        return max;
    }

    private synchronized boolean houseExist(House house)
    {
        if(houseList.contains(house))
            return true;
        else
            return false;
    }

    public synchronized void deleteHouse(int houseId)
    {
        House h;
        for(int i=0; i<houseList.size(); i++)
        {
            h = houseList.get(i);

            if (h.getHouseId() == houseId) {
                houseList.remove(h);
            }
        }

        if (getMaxHouseId() == HouseInfo.getHouseId())
            HouseInfo.setIsCoordinator(true);
        else
            HouseInfo.setIsCoordinator(false);
    }
}
