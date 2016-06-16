# Geographical-Analysis-of-RSS-Data
A side project done in the summer - it is able to read and analyze data from a live RSS feed (Rich Site Summary - an online source of frequently updated data). The analysis on earthquakes and life expectancy around the globe is used as examples here but in fact the code works with any other geographically tagged data, which is then visualized on a GUI in Java - PApplet. 

This project:

1. Filters the earthquakes by its magnitude, depth, distance from any location, title of the quake or any of those crteria combined. 

2. Uses different sorting algorithms to sort the QuakeEntry based on depth, magnitude, title, distance from any location etc. 

3. Marks the global map based on the magnitude of earthquakes (live data) / life expectancy (CSV file provided by the World Bank) around the globe.

Here are the examples demo:

![alt tag](https://github.com/frankcchen/Geographical-Analysis-of-RSS-Data/blob/master/life%20expectancy.png)

![alt tag](https://github.com/frankcchen/Geographical-Analysis-of-RSS-Data/blob/master/earthquakes.png)

P.S. Live earthquake data is obtained from the website http://earthquake.usgs.gov/. 
