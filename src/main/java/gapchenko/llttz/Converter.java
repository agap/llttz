package gapchenko.llttz;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gapchenko.llttz.stores.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TimeZone;

/**
 * @author artemgapchenko
 * Created on 18.04.14.
 */
public class Converter implements IConverter {
    private TimeZoneStore tzStore;
    private static Converter instance = null;

    private Converter(Class clazz) {
        if (!TimeZoneStore.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Illegal store provided: " + clazz.getName());
        }
        try {
            tzStore = (TimeZoneStore) clazz.newInstance();
            loadData();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Converter getInstance(final Class clazz) {
        if (instance == null || !instance.getStoreClass().equals(clazz)) instance = new Converter(clazz);
        return instance;
    }

    public Class getStoreClass() {
        return tzStore.getClass();
    }

    @Override
    public TimeZone getTimeZone(final double lat, final double lon) {
        return tzStore.nearestTimeZone(new Location(new double[]{lat, lon}));
    }

    private void loadData() {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(Converter.class.getResourceAsStream("/timezones.json"))
        );
        JsonArray array = new JsonParser().parse(br).getAsJsonArray();

        for (JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();
            tzStore.insert(new Location(
                    obj.get("latitude").getAsDouble(), obj.get("longitude").getAsDouble(), obj.get("zone").getAsString()
            ));
        }
    }
}