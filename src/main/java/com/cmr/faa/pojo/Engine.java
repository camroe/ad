package com.cmr.faa.pojo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Engine extends GenericEquipmentInformation {

    public Engine withModel(String model) {
        this.setModel(model);
        return this;
    }

    public Engine withSerialNumber(String serialNumber) {
        this.setSerialNumber(serialNumber);
        return this;
    }

    public Engine withManufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int hash = 1;
        if (null != getSerialNumber()) hash = getSerialNumber().hashCode();
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

        Engine e = (Engine) o;

        return new EqualsBuilder().
                append(getManufacturer(), e.getManufacturer()).
                append(getModel(), e.getModel()).
                append(getSerialNumber(), e.getSerialNumber()).
                isEquals();
    }
}
