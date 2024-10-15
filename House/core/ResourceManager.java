package core;

import communication.SocketStub;
import data.HouseComplex;
import smartMeter.SmartMeterSimulator;

import java.util.ArrayList;

// implements Singleton pattern
public class ResourceManager {

    private SmartMeterSimulator smartMeterSimulator;
    private static ResourceManager instance = null;

    private ArrayList<Integer> waitingHouses = new ArrayList<>();
    private final Object lockWaitingHouses = new Object();

    private Long requestTimestamp = null;
    private final Object lockRequestTimestamp = new Object();

    private int okResMessages = 0;
    private final Object waitForOk = new Object();

    private boolean isUsingResource = false;
    private final Object lockIsUsingRes = new Object();

    private boolean hasSentResRequest = false;
    private final Object lockHasSentResRequest = new Object();

    private ResourceManager() {}

    public void setSimulator(SmartMeterSimulator s)
    {
        smartMeterSimulator = s;
    }

    public synchronized static ResourceManager getInstance()
    {
        if(instance == null)
            instance = new ResourceManager();

        return instance;
    }

    // Start resource request
    public void obtainResource()
    {
        Requester r = new Requester();
        r.start();
    }

    public void okMessagesUp()
    {
        if(!hasSentResRequest) return;

        synchronized (waitForOk) {
            okResMessages++;

            if (okResMessages >= HouseComplex.getInstance().getSize() - 1) {
                waitForOk.notify();
            }
        }
    }

    // Thread that manage resource usage
    private class Requester extends Thread
    {
        @Override
        public void run() {

            synchronized (waitForOk) {
                setHasSentResRequest(true);
                SocketStub.sendResourceRequest();
                setRequestTimestamp(Utils.getTimestamp());

                try {
                    waitForOk.wait();

                    setIsUsingResource(true);
                    System.out.println("Using resource!");
                    smartMeterSimulator.boost();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Finished using resource");
                okResMessages = 0;
                setRequestTimestamp(null);
                setIsUsingResource(false);
                setHasSentResRequest(false);

                //invio ok alle case in attesa
                for (Integer id : waitingHouses)
                    SocketStub.sendResourceOk(id);
                clearWaitingHouses();
            }
        }
    }

    public void addWaitingHouse(int id)
    {
        synchronized (lockWaitingHouses) {
            waitingHouses.add(id);
        }
    }

    private void clearWaitingHouses()
    {
        synchronized (lockWaitingHouses) {
            waitingHouses.clear();
        }
    }

    private void setRequestTimestamp(Long value) {
        synchronized (lockRequestTimestamp) {
            requestTimestamp = value;
        }
    }

    public Long getRequestTimestamp() {
        synchronized (lockRequestTimestamp) {
            return requestTimestamp;
        }
    }

    public boolean isUsingResource() {
        synchronized (lockIsUsingRes) {
            return isUsingResource;
        }
    }

    private void setIsUsingResource(boolean isUsingResource) {
        synchronized (lockIsUsingRes) {
            this.isUsingResource = isUsingResource;
        }
    }

    public boolean hasSentResRequest() {
        synchronized (lockHasSentResRequest) {
            return hasSentResRequest;
        }
    }

    private void setHasSentResRequest(boolean hasSentResRequest) {
        synchronized (lockHasSentResRequest) {
            this.hasSentResRequest = hasSentResRequest;
        }
    }
}
