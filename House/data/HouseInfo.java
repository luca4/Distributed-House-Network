package data;

// Save house info
public class HouseInfo {

    private static int houseId;
    private static String houseIpAddr;
    private static int housePort;
    private static boolean isCoordinator = false;

    private static String serverIpAddr;
    private static int serverPort;

    public static int getHouseId() {
        return houseId;
    }

    public static void setHouseId(int houseId) {
        HouseInfo.houseId = houseId;
    }

    public static String getHouseIpAddr() {
        return houseIpAddr;
    }

    public static void setHouseIpAddr(String houseIpAddr) {
        HouseInfo.houseIpAddr = houseIpAddr;
    }

    public static int getHousePort() {
        return housePort;
    }

    public static void setHousePort(int housePort) {
        HouseInfo.housePort = housePort;
    }

    public static String getServerIpAddr() {
        return serverIpAddr;
    }

    public static void setServerIpAddr(String serverIpAddr) {
        HouseInfo.serverIpAddr = serverIpAddr;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        HouseInfo.serverPort = serverPort;
    }

    public synchronized static boolean isCoordinator() {
        return isCoordinator;
    }

    public synchronized static void setIsCoordinator(boolean isCoordinator) {
        HouseInfo.isCoordinator = isCoordinator;
    }
}
