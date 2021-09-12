package com.example.novigrad.Users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Address implements Serializable {
    private String unitNumber;
    private String streetNumber;
    private String streetName;
    private String city;
    private String province;
    private String postalCode;
    private String country;


    public Address(){

    }

    public Address(String unitNumber, String streetNumber, String streetName, String city, String province, String postalCode, String country){
        this.unitNumber = unitNumber;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }

    public void setUnitNumber(String unitNumber){ this.unitNumber = unitNumber; }
    public void setStreetNumber(String streetNumber){ this.streetNumber = streetNumber; }
    public void setStreetName(String streetName){ this.streetName = streetName;}
    public void setCity(String city){ this.city = city;}
    public void setProvince(String province){ this.province = province;}
    public void setPostalCode(String postalCode){ this.province = postalCode;}
    public void setCountry(String country){this.country = country; }

    public String getUnitNumber(){ return this.unitNumber; }
    public String getStreetNumber(){ return this.streetNumber; }
    public String getStreetName(){ return this.streetName;}
    public String getCity(){ return this.city;}
    public String getProvince(){ return this.province;}
    public String getPostalCode(){ return this.province;}
    public String getCountry(){return this.country; }


    public Map<String, Object> toMap() {
        HashMap<String, Object> addressMap = new HashMap<>(6);
        addressMap.put("unitNumber", unitNumber);
        addressMap.put("streetNumber", streetNumber);
        addressMap.put("streetName", streetName);
        addressMap.put("city", city);
        addressMap.put("province", province);
        addressMap.put("postalCode", postalCode);
        addressMap.put("country", country);
        return addressMap;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(unitNumber + " - " + streetNumber + " " + streetName + ",\n");
        sb.append(city + " " + province + " " + postalCode + ",\n");
        sb.append(country);
        return sb.toString();
    }


}
