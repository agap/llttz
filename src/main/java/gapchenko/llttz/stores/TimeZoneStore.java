package gapchenko.llttz.stores;

import java.util.TimeZone;

import static java.lang.Math.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * @author artemgapchenko
 * Created on 22.04.14.
 */
public abstract class TimeZoneStore {

    abstract public void insert(final Location loc);

    abstract public TimeZone nearestTimeZone(final Location node);

    /**
     * Calculates distance between two points.
     * @see <a href="http://en.wikipedia.org/wiki/Great-circle_distance">Great-circle distance</a>
     * @param latFrom latitude of from point
     * @param lonFrom longitude of from point
     * @param latTo latitude of to point
     * @param lonTo longitude of to point
     * @return calculated distance
     */
    protected double distanceInKilometers(final double latFrom, final double lonFrom, final double latTo, final double lonTo) {
        final double meridianLength = 111.1;
        return meridianLength * centralAngle(latFrom, lonFrom, latTo, lonTo);
    }

    protected double centralAngle(final Location from, final Location to) {
        return centralAngle(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
    }

    protected double centralAngle(final double latFrom, final double lonFrom, final double latTo, final double lonTo) {
        final double latFromRad = toRadians(latFrom),
                lonFromRad = toRadians(lonFrom),
                latToRad   = toRadians(latTo),
                lonToRad   = toRadians(lonTo);

        final double centralAngle = toDegrees(acos(sin(latFromRad) * sin(latToRad) + cos(latFromRad) * cos(latToRad) * cos(lonToRad - lonFromRad)));

        return centralAngle <= 180.0 ? centralAngle : (360.0 - centralAngle);
    }

    protected double distanceInKilometers(final Location from, final Location to) {
        return distanceInKilometers(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
    }
}
