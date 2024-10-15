package services;

importbeans.*;
importcore.StatsCalculator;
importdata.ComplexMeasures;
importdata.HouseComplex;
importdata.HousesMeasures;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

// AdministratorClient interface
@Path("/admin")
public class AdminRestInterface {

    @GET
    @Path("/getHouseList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHouseList()
    {
        HouseComplex houses = HouseComplex.getInstance();

        ArrayList<House> tmp = houses.getHouseList();

        if(tmp.isEmpty())
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else
            return Response.ok(tmp).build();
    }

    @GET
    @Path("/getHouseLastStat")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHouseLastStat(@QueryParam("houseId") int houseId, @QueryParam("nStat") int nStat)
    {
        if(houseId == 0 || nStat == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        HousesMeasures housesMeasure = HousesMeasures.getInstance();

        ArrayList<Measurement> tmp = housesMeasure.getLastHouseMeasures(nStat, houseId);

        if(tmp.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(tmp).build();
    }

    @GET
    @Path("/getComplexStat")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComplexStat(@QueryParam("nStat") int nStat)
    {
        if(nStat == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        ComplexMeasures complexMeasures = ComplexMeasures.getInstance();
        ArrayList<ComplexMeasure> tmp = complexMeasures.getLastMeasures(nStat);

        if(tmp.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(tmp).build();
    }

    @GET
    @Path("/calculateHouseStat")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculateHouseStat(@QueryParam("houseId") int houseId, @QueryParam("nStat") int nStat)
    {
        if(houseId == 0 || nStat == 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        HousesMeasures housesMeasure = HousesMeasures.getInstance();
        ArrayList<Measurement> tmp = housesMeasure.getLastHouseMeasures(nStat, houseId);

        if (tmp.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();

        Stats houseStats = StatsCalculator.getHousesStats(tmp);

        return Response.ok(houseStats).build();
    }

    @GET
    @Path("/calculateComplexStat")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculateComplexStat(@QueryParam("nStat") int nStat)
    {
        if(nStat == 0) return Response.status(Response.Status.BAD_REQUEST).build();

        ComplexMeasures complexMeasures = ComplexMeasures.getInstance();
        ArrayList<ComplexMeasure> tmp = complexMeasures.getLastMeasures(nStat);

        if(tmp.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();

        Stats complexStats = StatsCalculator.getComplexStats(tmp);

        return Response.ok(complexStats).build();
    }
}