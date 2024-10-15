package services;

import com.google.gson.Gson;
import beans.*;
import data.ComplexMeasures;
import data.HousesMeasures;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Interface for adding measurements
@Path("/measures")
public class MeasurementRestInterface {

    @Path("/addMeasures")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMeasures(String str)
    {
        Gson gson = new Gson();
        MeasuresInfo data = gson.fromJson(str, MeasuresInfo.class);

        if(str == null) return Response.status(Response.Status.BAD_REQUEST).build();

        HousesMeasures houseMeasures = HousesMeasures.getInstance();
        houseMeasures.addMeasure(data.getLocalMeasures(), data.getSendTimestamp());

        ComplexMeasures complexMeasures = ComplexMeasures.getInstance();
        complexMeasures.addMeasure(data.getComplexMeasure(), data.getTimestampComplexMeasure());

        return Response.ok().build();
    }
}