package com.backbase.atm;

/**
 * Created by admin on 9/9/15.
 */
public class Address {
    String street;
    String housenumber;
    String postalcode;
    String city;

    GeoLocation geoLocation = new GeoLocation();

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public void setPostalCode(String postalcode) {
        this.postalcode = postalcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public String getCity() {
        return city;
    }

    public GeoLocation getGeoLocation() { return geoLocation; }
}
