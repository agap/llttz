package gapchenko.llttz.stores;

import java.util.Arrays;

/**
 * @author artemgapchenko
 * Created on 22.04.14.
 */
public class Location {
    private double[] coordinates;
    private String zone;

    public Location(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public Location(final double latitude, final double longitude, final String zone) {
        this.coordinates = new double[] { latitude, longitude };
        this.zone = zone;
    }

    public double[] getCoordinates() {
        return this.coordinates;
    }

    public double getLatitude() {
        return this.coordinates[0];
    }

    public double getLongitude() {
        return this.coordinates[1];
    }

    public String getZone() {
        return zone;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().isAssignableFrom(Location.class)) throw new IllegalArgumentException("provided obj is: " + obj);
        return Arrays.equals(coordinates, ((Location) obj).getCoordinates());
    }
}
