# What llttz is?
**llttz** (stands for **l**atitude **l**ongitude **t**o **t**ime **z**one) is a light-weight project written in Java which allows you to obtain the time zone in a place with specified latitude and longitude. Original idea (along with the dataset) was taken from the [APTimezones project](https://github.com/Alterplay/APTimeZones).
To obtain the ```TimeZone``` you need to create an object of ```Converter``` class, and invoke its ```getInstance(double lat, double lon)``` method, like so:

    IConverter iconv = Converter.getInstance();
    TimeZone tz = iconv.getTimeZone(53.5233333, 49.4125); 

# I see that you're using a list of timezones here instead of the list of timezones' polygones. The calculated time zone won't be precise, right?
Yes, in some cases you can get the error in +\\- 2 hours.

# Is it useful then?
It depends. In my case I needed library which could allow me to calculate the time zone by provided latitude/longitude without making requests to remote web-services, such as [GeoNames](http://www.geonames.org). I also needed this library to be a light-weight (I had to use it on Android devices) and preciseness in calculations wasn't the mandatory requirement.