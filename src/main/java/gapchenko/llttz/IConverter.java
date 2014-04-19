package gapchenko.llttz;

import java.util.TimeZone;

/**
 * @author artemgapchenko
 * Created on 18.04.14.
 */
public interface IConverter {
    /**
     * Obtains the instance of {@link java.util.TimeZone} based on the provided latitude and longitude.
     * @param lat point latitude
     * @param lon point longitude
     * @return found timezone on success, null otherwise.
     */
    public TimeZone getTimeZone(final double lat, final double lon);
}
