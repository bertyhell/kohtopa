/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.contractstab;

import data.entities.*;
import be.belgium.eid.eidlib.BeID;
import be.belgium.eid.objects.IDAddress;
import be.belgium.eid.objects.IDData;

/**
 *
 * @author Ruben
 */
public class EIDPerson {

    private Address homeAddress;

    private String name;
    private String firstName;

    public EIDPerson() throws Exception {
        try {
            BeID portal = new BeID(true);
            portal.connect();

            // Get raw address data from BeID
            IDAddress rawAddress = portal.getIDAddress();

            // Convert raw address into database address
            String streetAndStreetNumber = rawAddress.getStreet();
            String street = "";
            String streetNumber;
            int i = 0;
            while (i < streetAndStreetNumber.length() && !Character.isDigit(streetAndStreetNumber.charAt(i))) {
                if (streetAndStreetNumber.charAt(i) != ' ') {
                    street += streetAndStreetNumber.charAt(i);
                }
                i++;
            }
            streetNumber = streetAndStreetNumber.substring(i, streetAndStreetNumber.length());
            String zipcode = rawAddress.getZipCode();
            String city = rawAddress.getMunicipality();
            String country = "BE";

            homeAddress = new Address(street, streetNumber, zipcode, city, country);

            // Get raw personal data from BeID
            IDData rawData = portal.getIDData();

            // Convert raw personal data into usable personal data
            name = rawData.getName();
            firstName = rawData.get1stFirstname();

        } catch (Exception exc) {
            throw new Exception(exc.getMessage());
        }
    }

    @Override
    public String toString() {
        return name + "\n" +
               firstName + "\n" +
               homeAddress.toString() + "\n";
    }

    public String getFirstName() {
        return firstName;
    }

    public Address getAddress() {
        return homeAddress;
    }

    public String getName() {
        return name;
    }

}
