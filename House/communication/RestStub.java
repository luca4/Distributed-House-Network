package communication;

import com.google.gson.Gson;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import bean.House;
import data.HouseInfo;
import bean.MeasuresInfo;
import data.HouseComplex;
import core.Utils;
import smartMeter.Measurement;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;

// Interface that communicate with REST server
public class RestStub {

    private static Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
    private static final String restHouseRoot = "http://"+ HouseInfo.getServerIpAddr() +":"+HouseInfo.getServerPort()+"/house/";
    private static final String restMeasurementRoot = "http://"+HouseInfo.getServerIpAddr()+":"+HouseInfo.getServerPort()+"/measures/";

    // Alert server of house entering in the network and get id of already entered houses
    public static boolean insertHouse(int houseId, String houseIpAddr, int houseListeningPort)
    {
        WebTarget webTarget = client.target(restHouseRoot).path("insertHouse").queryParam("houseId", houseId)
                .queryParam("ipAddress", houseIpAddr)
                .queryParam("listeningPort", houseListeningPort);
        Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);

        Response response = null;
        try {
            response = builder.post(null);
        }catch (ProcessingException e)
        {
            System.err.println("Errore: comunicazione con il server fallita");
            return false;
        }

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.err.println("Errore: Parametri non corretti o assenti");
            return false;
        }
        else if(response.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
            System.err.println("Errore: Casa con houseId già presente!");
            return false;
        }

        // Save houses already entered in the network
        ArrayList<House> houseList = response.readEntity(new GenericType<ArrayList<House>>() {});
        if(houseList != null)
        {
            HouseComplex complex = HouseComplex.getInstance();
            for (House h : houseList)
            {
                complex.addHouse(h);
            }
        }
        return true;
    }

    // Alert server of house exiting from network
    public static void deleteHouseFromServer()
    {
        WebTarget webTarget = client.target(restHouseRoot).path("deleteHouse").queryParam("houseId", HouseInfo.getHouseId());
        Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
        builder.delete();
    }

    // Send global and local measures to REST server
    public static void sendDataToServer(HashMap<Integer, Measurement> localMeasures, double complexMeasure, long timestampComplexMeasure)
    {
        WebTarget webTarget = client.target(restMeasurementRoot).path("addMeasures");
        Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        MeasuresInfo measuresInfo = new MeasuresInfo(localMeasures, Utils.getTimestamp(), complexMeasure, timestampComplexMeasure);
        String s = gson.toJson(measuresInfo, MeasuresInfo.class);

        Response response = null;
        try {
            response = builder.post(Entity.entity(s, MediaType.APPLICATION_JSON));

        }catch (ProcessingException e) {
            System.out.println("Errore: comunicazione con il server fallita");
            return;
        }
        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.err.println("Errore: Parametri non corretti o assenti");
        }
    }
}
