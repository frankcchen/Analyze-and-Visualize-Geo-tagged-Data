# Geographical-Analysis-of-RSS-Data
A side project done in the summer - it is able to read and analyze data from a live RSS feed (Rich Site Summary - an online source of frequently updated data). The analysis on earthquakes and life expectancy around the globe is used as examples here but in fact the code works with any other geotagged data, which is visualized on a GUI in Java - PApplet. 

This project:

1. Filters the earthquakes by its magnitude, depth, distance from any location, title of the quake or any of those crteria combined. 

2. Uses different sorting algorithms to sort the QuakeEntry based on depth, magnitude, title, distance from any location etc. 

3. Marks the global map based on the magnitude of earthquakes (live data) / life expectancy (provided by World Bank) around the globe.



P.S.

1) Live earthquake data is obtained from the website http://earthquake.usgs.gov/. 

2) The 'Location' class is adopted from an Andriod Open Source Project licensed under the Apache License: http://www.apache.org/licenses/LICENSE-2.0.
