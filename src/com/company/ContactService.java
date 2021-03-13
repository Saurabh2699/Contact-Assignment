package com.company;

import java.io.*;
import java.sql.*;
import java.util.*;

class ContactNotFoundException extends Exception {
    ContactNotFoundException(String msg) {
        super(msg);
    }
}

public class ContactService {
    // add contact
    void addContact(Contact contact, List<Contact> contacts) {
        contacts.add(contact);
    }

    // remove contact
    void removeContact(Contact contact, List<Contact> contacts) throws ContactNotFoundException {
        int pos=-1;
            for(int i=0;i<contacts.size();i++) {
                if(contact.contactID == contacts.get(i).contactID) {
                    pos = i;
                    break;
                }
            }
            if(pos == -1) {
               throw new ContactNotFoundException("Contact not found cannot be deleted....");
            }

            else {
                contacts.remove(pos);
            }
    }

    // search contact by name
    Contact seachContactByName(String name, List<Contact> contacts) throws ContactNotFoundException{
        for(Contact c: contacts) {
            if(name.equals(c.getContactName())) {
                return c;
            }
        }

        return null;
    }

    // seach by number
    List<Contact> seachContactByNumber(String number, List<Contact> contacts) {
        List<Contact> list = new ArrayList<>();
        for(Contact c: contacts) {
            for(String s: c.getContactNumber()) {
                if(s.contains(number)) {
                    list.add(c);
                }
            }
        }
        return list;
    }

    // add contact for particular id
    void addContactNumber(int contactId, String contactNo, List<Contact> contacts) {
        for(Contact c: contacts) {
            if(c.getContactID() == contactId) {
                c.getContactNumber().add(contactNo);
            }
        }
    }

    // sort contacts by name
    void sortContactsByName(List<Contact> contacts) {
        Collections.sort(contacts,(c1,c2) -> {
            return c1.getContactName().compareTo(c2.getContactName());
        });
    }

    // read contacts from file
    void readContactsFromFile(List<Contact> contacts, String filename) throws FileNotFoundException {
        File file = new File(filename);
        if(!file.exists()) {
            throw new FileNotFoundException("File not found...");
        }

        Contact c = new Contact();
        Scanner sc = new Scanner(file);
        while(sc.hasNextLine()) {
            String[] line = sc.nextLine().split(",");
            int id = Integer.parseInt(line[0]);
            String name = line[1];
            String email = line[2];
            List<String> ls = Arrays.asList(line[3].split(","));

            c = new Contact(id,name,email,ls);

            contacts.add(c);
        }
    }

    // serialize contacts
    void serializeContactDetails(List<Contact> contacts , String filename) {
        File file = new File(filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(contacts);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // deserialize contacts
    @SuppressWarnings("unchecked")
    List<Contact> deserializeContact(String filename) {
        List<Contact> result = new ArrayList<>();
        File file = new File(filename);
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = (List<Contact>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    // populate contacts from db
    Set<Contact> populateContactFromDb() throws SQLException {
        Set<Contact> contacts = new HashSet<>();

        Connection con = null;
        ResultSet rs = null;

        try {
            con = Util.getConnection();
            if(con == null) {
                System.out.println("Database connectivity error...");
            }

            String query = "select * from contact_tbl";

            assert con != null;
            Statement st = con.createStatement();
            rs = st.executeQuery(query);

            Contact c = new Contact();
            while(rs.next()) {
                c.setContactID(rs.getInt("contactId"));
                c.setContactName(rs.getString("contactName"));
                c.setEmail(rs.getString("contactEmail"));
                if(rs.getString("contactList") != null)
                    c.setContactNumber(Arrays.asList(rs.getString("contactList").split(",")));

                contacts.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
             Util.closeResultSet(rs);
             Util.closeConnection(con);
        }
        return contacts;
    }

    // add contacts
    boolean addContacts(List<Contact> existingContact,Set<Contact> newContacts) {
        if(existingContact.size() == 0) {
            System.out.println("No contacts found...");
            return false;
        }
        newContacts.addAll(existingContact);

        return true;
    }
}
