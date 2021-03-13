package com.company;

import java.util.*;

public class Contact {
    int contactID;
    String ContactName;
    String email;
    List<String> contactNumber;

    Contact() {}

    public Contact(int contactID, String contactName, String email, List<String> contactNumber) {
        this.contactID = contactID;
        ContactName = contactName;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(List<String> contactNumber) {
        this.contactNumber = contactNumber;
    }

}
