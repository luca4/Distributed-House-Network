package core;

import beans.ComplexMeasure;
import beans.House;
import beans.Measurement;
import beans.Stats;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

// Manage connection with server
public class Stub {

    private Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
    private WebTarget webTarget;
    private String ipAddr = "http://localhost:1337/admin/";
    private String connectionErrorText = "Errore nella connessione al server";

    public void viewAllHouses()
    {
        webTarget = client.target(ipAddr).path("getHouseList");
        Invocation.Builder invocBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        ArrayList<House> response = null;
        try {
            response = invocBuilder.get(new GenericType<ArrayList<House>>(){});
        }catch (NotFoundException e) {
            System.out.println("Elemento assente");
            return;
        }catch (ProcessingException e){
            System.err.println(connectionErrorText);
            return;
        }
        printArrayContent(response);
    }
    public void getNHouseStat(long houseId, int n)
    {
        webTarget = client.target(ipAddr).path("getHouseLastStat").queryParam("houseId", houseId).queryParam("nStat", n);
        Invocation.Builder invocBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        ArrayList<Measurement> response = null;
        try {
            response = invocBuilder.get(new GenericType<ArrayList<Measurement>>() {});
        }catch (NotFoundException e) {
            System.out.println("Elemento assente");
            return;
        }catch (ProcessingException e){
            System.err.println(connectionErrorText);
            return;
        }

        printArrayContent(response);
    }
    public void getNComplexStat(int n)
    {
        webTarget = client.target(ipAddr).path("getComplexStat").queryParam("nStat", n);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        ArrayList<ComplexMeasure> response = null;
        try {
            response = invocationBuilder.get(new GenericType<ArrayList<ComplexMeasure>>() {});
        }catch (NotFoundException e) {
            System.out.println("Elemento assente");
            return;
        }catch (ProcessingException e){
            System.err.println(connectionErrorText);
            return;
        }

        printArrayContent(response);
    }
    public void calculateNHouseStat(long houseId, int n)
    {
        webTarget = client.target(ipAddr).path("calculateHouseStat").queryParam("houseId", houseId).queryParam("nStat", n);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Stats stats = null;
        try{
            stats = invocationBuilder.get(new GenericType<Stats>() {});
        }catch (NotFoundException e) {
            System.out.println("Elemento assente");
            return;
        }catch (ProcessingException e){
            System.err.println(connectionErrorText);
            return;
        }
        System.out.println("Media: "+stats.getAverage()+"\nDeviazione standard: "+stats.getStdDeviation());
    }
    public void calculateNComplexStat(int n)
    {
        webTarget = client.target(ipAddr).path("calculateComplexStat").queryParam("nStat", n);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Stats stats = null;
        try{
            stats = invocationBuilder.get(new GenericType<Stats>() {});
        }catch (NotFoundException e) {
            System.out.println("Elemento assente");
            return;
        }catch (ProcessingException e){
            System.err.println(connectionErrorText);
            return;
        }
        System.out.println("Media: "+stats.getAverage()+"\nDeviazione standard: "+stats.getStdDeviation());
    }

    private void printArrayContent(ArrayList<?> array)
    {
        if (array == null) return;

        int i = 1;
        for (Object o : array) {
            System.out.println(i+": "+o.toString());
            i++;
        }
    }
}
