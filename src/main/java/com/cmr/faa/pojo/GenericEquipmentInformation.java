package com.cmr.faa.pojo;

import com.cmr.faa.DAO.Constants;

public class GenericEquipmentInformation {
    private String serialNumber;
    private String manufacturer;
    private String model;


    public String getSerialNumber() {
        if ((null == this.serialNumber) || (this.serialNumber.equals("")))
            return Constants.DEFAULT_SERIAL_NUMBER;
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManufacturer() {
        if ((null == this.manufacturer) || (this.manufacturer.equals("")))
            return Constants.DEFAULT_MANUFACURER;
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        if ((null == this.model) || (this.model.equals("")))
            return Constants.DEFAULT_MODEL;
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
