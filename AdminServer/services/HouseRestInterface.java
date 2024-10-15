package services;

import beans.House;
import data.HouseComplex;
import data.HousesMeasures;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

// Interface for add/remove house from network
@Path("/house")
public class HouseRestInterface {

    private HouseComplex houseComplex = HouseComplex.getInstance();

    @Path("/insertHouse")
    @POST
    @Produces("application/json")
    public Response insertHouse(@QueryParam("houseId") int houseId,
                                @QueryParam("ipAddress") String ipAddress,
                                @QueryParam("listeningPort") int listeningPort)
    {
        if(houseId == 0 || ipAddress == null || listeningPort == 0)  //dati mancanti
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else if(houseComplex.addHouse(new House(houseId, ipAddress, listeningPort)))
        {
            return Response.ok(houseComplex.getHouseList()).build();
        }
        else
            return Response.status(Response.Status.CONFLICT).build(); //casa con id già presente
    }

    @Path("/deleteHouse")
    @DELETE
    public Response deleteHouse(@QueryParam("houseId") int houseId)
    {
        if(houseId == 0)  //parametro non passato correttamente
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (houseComplex.deleteHouse(houseId))
        {
            HousesMeasures.getInstance().deleteData(houseId);
            return Response.ok().build();
        }
        else   //casa non trovata
            return Response.status(Response.Status.NOT_FOUND).build();
    }
}