# Geographical-Analysis-of-RSS-Data
A side project done in the summer - an interative earthquake map. It is able to read and analyze data from a live RSS feed (Rich Site Summary - an online source of frequently updated data). Various interative features are able to display more information. The analysis on earthquakes and life expectancy around the globe is used as examples here but in fact the code works with any other geographically tagged data, which is then visualized on a GUI in Java - PApplet. 

This project:

1. Filters the earthquakes by its magnitude, depth, distance from any location, title of the quake or any of those crteria combined. 

2. Uses different sorting algorithms to sort the QuakeEntry based on depth, magnitude, title, distance from any location etc. 

3. Marks the global map based on the magnitude, timing and location of earthquakes (live data) and the major cities that each earthquake is likely to impact/ life expectancy (CSV file provided by the World Bank) around the globe.

Here are the examples demo:

This is a screenshot of part of the global map. The magnitude, depth of each quake, whether it occured on sea of land and whether it occured in the recently are all featured by the marking styles. 

![alt tag](https://github.com/frankcchen/Analyze-and-Visualize-Geo-tagged-Data/blob/master/demo1.png)

Whenever you click on a quake, it is able to show its threat circle - a region it is going to impact. Whenever you click on a major city, the map will only show the quakes that are dangerous to the city. When you hover your mouse of a quake or a city, it will dispaly relevant infomation. 

![alt tag](https://github.com/frankcchen/Analyze-and-Visualize-Geo-tagged-Data/blob/master/demo2.png)

Life Expectancy map:

![alt tag](https://github.com/frankcchen/Geographical-Analysis-of-RSS-Data/blob/master/life%20expectancy.png)

P.S. Live earthquake data is obtained from the website http://earthquake.usgs.gov/. 
     Life expectancy data is obtained from the World Bank: http://data.worldbank.org/.
