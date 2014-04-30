# What llttz is?
**llttz** (stands for **l**atitude **l**ongitude **t**o **t**ime **z**one) is a light-weight project written in Java which allows you to obtain the time zone in a place with specified latitude and longitude. Original idea (along with the dataset) was taken from the [APTimezones project](https://github.com/Alterplay/APTimeZones).
To obtain the ```TimeZone``` you need to create an object of ```Converter``` class, and invoke its ```getInstance(double lat, double lon)``` method, like so:

    IConverter iconv = Converter.getInstance(TimeZoneListStore.class);
    TimeZone tz = iconv.getTimeZone(53.5233333, 49.4125);

Here ```TimeZoneListStore``` is a class which holds a list of timezones. The time complexity of [nearest neighbor search](http://en.wikipedia.org/wiki/Nearest_neighbor_search) is O(n) in that case. There is also another class, named ```TimeZoneTreeStore```, which you can use to perform nearest neighbor searches. It holds a simple implementation of [k-d tree](http://en.wikipedia.org/wiki/K-d_tree) which can perform nearest neighbor lookup with the time complexity of 0(ln(n)) in the best case (it's a little bit unstable and returns wrong timezones when we perform searches in the areas which are located near the South or North Pole so you can stick to ```TimeZoneListStore``` for now).

# I see that you're using a list of timezones here instead of the list of timezones' polygones. The calculated time zone won't be precise, right?
Yes, in some cases you can get the error in +\\- 2 hours.

# Is it useful then?
It depends. In my case I needed library which could allow me to calculate the time zone by provided latitude/longitude without making requests to remote web-services, such as [GeoNames](http://www.geonames.org). I also needed this library to be a light-weight (I had to use it on Android devices) and preciseness in calculations wasn't the mandatory requirement.