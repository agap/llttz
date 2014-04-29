package gapchenko.llttz.stores;

import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

/**
 * @author artemgapchenko
 * Created on 22.04.14.
 */
public class TimeZoneListStore extends TimeZoneStore {
    private List<Location> timeZones = new LinkedList<>();

    @Override
    public void insert(Location loc) {
        timeZones.add(loc);
    }

    @Override
    public TimeZone nearestTimeZone(Location node) {
        double bestDistance = Double.MAX_VALUE;
        Location bestGuess = timeZones.get(0);

        for (Location current : timeZones.subList(1, timeZones.size())) {
            double newDistance = distanceInKilometers(node, current);

            if (newDistance < bestDistance) {
                bestDistance = newDistance;
                bestGuess = current;
            }
        }

        return java.util.TimeZone.getTimeZone(bestGuess.getZone());
    }
}
