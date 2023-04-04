package com.driver.model;

import javax.lang.model.element.Element;

public enum Station {

    JAMMU(0),
    JALANDHAR(1),
    LUDHIANA(2),
    DELHI(3),
    KANPUR(4),
    AGRA(5),
    MATHURA(6),
    GWALIOR(7),
    NAGPUR(8),
    MUMBAI(9),
    PUNE(10),
    CHENNAI(11),
    TIPURATI(12),
    KANYAKUMARI(13),
    PRAYAGRAJ(14),
    VARANASI(15),
    DARJELLING(16),
    KOLKATA(17);

    public final int stationNo;

    private Station(int stationNo){
        this.stationNo=stationNo;
    }
    public int getStationNo(){
        return this.stationNo;
    }
}
