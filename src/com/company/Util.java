package com.company;

import java.sql.*;

public class Util {
    private static Connection con;

    static {
        try {
            Class.forName( Constants.DRIVER_NAME );
        } catch ( ClassNotFoundException e ) {
            System.out.println( "Driver class not found" );
        }
    }

    public static Connection getConnection() throws SQLException {
        con = DriverManager.getConnection( Constants.URL, Constants.USERNAME, Constants.PASSWORD );
        return con;
    }

    public static void closeConnection( Connection con ) throws SQLException {
        if ( con != null ) {
            con.close();
        }
    }

    public static void closePreparedStatement( PreparedStatement stmt ) throws SQLException {
        if ( stmt != null ) {
            stmt.close();
        }
    }

    public static void closeResultSet( ResultSet rs ) throws SQLException {
        if ( rs != null ) {
            rs.close();
        }
    }
}
