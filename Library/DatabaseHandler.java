package com.example.library;

import java.sql.*;

public class DatabaseHandler {
    private static final String DB_url = "jdbc:derby:database/forum;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;
    public static DatabaseHandler handler;
    public static String libraryTable = "LIBRARY";
    public static String userTable = "userTBL";


    public DatabaseHandler() {
        try {
            createConnection();
            createTable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static DatabaseHandler getHandler(){
        if(handler == null){
            handler = new DatabaseHandler();
            return handler;
        }else{
            return handler;
        }
    }

    /**
     * @param tblName: target table name
     * @return boolean
     * @throws SQLException
     */

    public static boolean tableExists(String tblName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tblName, new String[] {"TABLE"});

        return resultSet.next();
    }

    /**
     * Create a table with the given table name
     */
    public void createTable() {
        try {
            stmt = conn.createStatement();

            if (tableExists(userTable)) {
                System.out.println("Table " + userTable + " exists");
            } else {
                String statement = "CREATE TABlE " + userTable + " ("
                        + "firstName varchar(200), \n"
                        + "lastName varchar(200), \n"
                        + "pin bigint, \n"
                        + "bookID bigint, \n"
                        + "userID bigint primary key)";

                System.out.println(statement);
                stmt.execute(statement);
                System.out.println("CREATED USER table");

            }

            if (tableExists(libraryTable)) {
                System.out.println("Table " + libraryTable + " exists");
            } else {
                String statement = "CREATE TABlE " + libraryTable + " ("
                        + "title varchar(200), \n"
                        + "author varchar(200), \n"
                        + "genre varchar(200), \n"
                        + "quantity bigint, \n"
                        + "idNumber bigint primary key)";

                System.out.println(statement);
                stmt.execute(statement);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Create a connection to DB
     * @throws ClassNotFoundException
     */
    private static void createConnection() throws ClassNotFoundException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(DB_url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Execute the given query
     * @param qu
     */
    public void execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);

        } catch (SQLException throwables) {
            System.out.println(throwables);
            System.out.println("Did not enter data");
        }
    }
    public ResultSet execQuery(String query){
        ResultSet resultSet;
        try{
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return resultSet;
    }
}
