package com.backbase.atm;

/**
 * Created by admin on 9/9/15.
 */

public class ATM {
    String type;
    Address address = new Address();

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }
}
