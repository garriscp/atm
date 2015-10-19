# Full Stack Sample Application

Here's my attempt at building a small web application which uses all of the common technologies we use at my current employer.  
I'm a FE developer by trade, but this is mainly a stab at some BE technologies.  

## Overview

I created a Java web app based on the standard servlet spec.  
Using Maven, this produces a WAR file which you can drop into a vanilla Tomcat 7 and run with no configuration.
This app uses Camel to get a full set of data (ATMs / Locations).
Also using Camel, I expose some REST endpoints for filtering this data.
The FE uses an angular factory to hit the REST endpoints I've setup and refresh the data in the table.

## The full list of tech I'm using is:

* Java
* Maven
* Camel
* Angular
* Twitter Bootstrap