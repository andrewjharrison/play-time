package uk.co.harrisoft.application.model;

/**
 * Simple POJO to store the vehicle data.
 *
 * @author Andrew
 */
public class VehicleData {

    /** the vehicle registration number. */
    private String registration;

    /** the vehicle make. */
    private String make;

    /** the vehicle colour. */
    private String colour;

    /** default constructor. */
    public VehicleData() {

    }

    /** populated constructor. */
    public VehicleData(final String registration, final String make, final String colour) {
        this.registration = registration;
        this.make = make;
        this.colour = colour;
    }

    /**
     * @return the registration
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * @param registration the registration to set
     */
    public void setRegistration(final String registration) {
        this.registration = registration;
    }

    /**
     * @return the make
     */
    public String getMake() {
        return make;
    }

    /**
     * @param make the make to set
     */
    public void setMake(final String make) {
        this.make = make;
    }

    /**
     * @return the colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * @param colour the colour to set
     */
    public void setColour(final String colour) {
        this.colour = colour;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (colour == null ? 0 : colour.hashCode());
        result = prime * result + (make == null ? 0 : make.hashCode());
        result = prime * result + (registration == null ? 0 : registration.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VehicleData other = (VehicleData) obj;
        if (colour == null) {
            if (other.colour != null) {
                return false;
            }
        } else if (!colour.equals(other.colour)) {
            return false;
        }
        if (make == null) {
            if (other.make != null) {
                return false;
            }
        } else if (!make.equals(other.make)) {
            return false;
        }
        if (registration == null) {
            if (other.registration != null) {
                return false;
            }
        } else if (!registration.equals(other.registration)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("VehicleData [registration=")
               .append(registration)
               .append(", make=")
               .append(make)
               .append(", colour=")
               .append(colour)
               .append("]");
        return builder.toString();
    }

}
