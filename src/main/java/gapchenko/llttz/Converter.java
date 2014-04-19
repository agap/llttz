package gapchenko.llttz;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TimeZone;

import static java.lang.Math.*;

/**
 * @author artemgapchenko
 * Created on 18.04.14.
 */
public class Converter implements IConverter {
    private JsonArray array;
    private static Converter instance = null;

    private Converter() {
        loadData();
    }

    public static Converter getInstance() {
        if (instance == null) instance = new Converter();
        return instance;
    }

    @Override
    public TimeZone getTimeZone(final double lat, final double lon) {
        double bestDistance = Double.MAX_VALUE;
        JsonObject bestTZ = null;

        for (JsonElement object : array) {
            JsonObject current = object.getAsJsonObject();

            double tzLat = current.get("latitude").getAsDouble(), tzLon = current.get("longitude").getAsDouble();
            double newDistance = distance(lat, lon, tzLat, tzLon);

            if (newDistance < bestDistance) {
                bestDistance = newDistance;
                bestTZ = current;
            }
        }

        return bestTZ != null
                ? TimeZone.getTimeZone(bestTZ.get("zone").getAsString())
                : null;
    }

    private void loadData() {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(Converter.class.getResourceAsStream("/timezones.json"))
        );
        array = new JsonParser().parse(br).getAsJsonArray();
    }

    /**
     * Calculates distance between two points.
     * @see <a href="http://en.wikipedia.org/wiki/Great-circle_distance">Great-circle distance</a>
     * @param latFrom latitude of from point
     * @param lonFrom longitude of from point
     * @param latTo latitude of to point
     * @param lonTo longitude of to point
     * @return calculated distance
     */
    private double distance(final double latFrom, final double lonFrom, final double latTo, final double lonTo) {
        final double meridianLength = 111.1;

        final double latFromRad = toRadians(latFrom),
                     lonFromRad = toRadians(lonFrom),
                     latToRad   = toRadians(latTo),
                     lonToRad   = toRadians(lonTo);

        final double centralAngle = toDegrees(acos(sin(latFromRad) * sin(latToRad) + cos(latFromRad) * cos(latToRad) * cos(lonToRad - lonFromRad)));

        return meridianLength * (centralAngle <= 180.0 ? centralAngle : (360.0 - centralAngle));
    }
}