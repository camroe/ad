package com.cmr.faa.pojo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import static java.lang.Integer.valueOf;

public class Aircraft extends GenericEquipmentInformation {
    private String nNumber;

    public String getnNumber() {
        return nNumber;
    }

    public void setnNumber(String nNumber) {
        this.nNumber = nNumber;
    }

    public Aircraft withModel(String model) {
        this.setModel(model);
        return this;
    }

    public Aircraft withSerialNumber(String serialNumber) {
        this.setSerialNumber(serialNumber);
        return this;
    }

    public Aircraft withManufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public Aircraft withNnumber(String nNumber) {
        this.setnNumber(nNumber);
        return this;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int hash = 0;
        try {
            hash = valueOf(getSerialNumber());
        } catch (NumberFormatException e) {
            hash = 1;
        }
        return new HashCodeBuilder(hash % 2 == 0 ? hash + 1 : hash, PRIME).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (o == this)
            return true;

        if (o.getClass() != getClass())
            return false;

        Aircraft e = (Aircraft) o;

        return new EqualsBuilder().
                append(getManufacturer(), e.getManufacturer()).
                append(getModel(), e.getModel()).
                append(getSerialNumber(), e.getSerialNumber()).
                append(getnNumber(), e.getnNumber()).
                isEquals();
    }

}
