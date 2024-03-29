/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author gamal
 */
public class MyDB {

    public static Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "org.apache.derby.jdbc.ClientDriver");
        String url = "jdbc:derby://localhost/sun-appserv-samples;create=true";
        String username = "APP";
        String password = "APP";
        return DriverManager.getConnection(url, username, password);
    }

    public void createMyuserTable() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            stmnt.execute("CREATE TABLE MYUSER("
                    + " UserId CHAR(6) CONSTRAINT PK_CUSTOMER PRIMARY KEY, "
                    + " Name CHAR(30), Password CHAR(6), Email CHAR(30), "
                    + " Phone CHAR(10), Address CHAR(60), "
                    + " SecQn CHAR(60), SecAns CHAR(60))");
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }

    public void dropMyuserTable() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            stmnt.execute("DROP TABLE MYUSER");
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }

    public void addRecords(ArrayList<Myuser> myUsers) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO MYUSER VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            for (Myuser myuser : myUsers) {
                pStmnt.setString(1, myuser.getUserid());
                pStmnt.setString(2, myuser.getName());
                pStmnt.setString(3, myuser.getPassword());
                pStmnt.setString(4, myuser.getEmail());
                pStmnt.setString(5, myuser.getPhone());
                pStmnt.setString(6, myuser.getAddress());
                pStmnt.setString(7, myuser.getSecQn());
                pStmnt.setString(8, myuser.getSecAns());
                int rowCount = pStmnt.executeUpdate();
                if (rowCount == 0) {
                    throw new SQLException("Cannot insert records!");
                }
            }
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }

    Boolean createRecord(Myuser myuser) {
        /*
        acceptsaccepts a Myuser object and checks whether
        the actual record exists in the database. If the record does not exist, it will create a new record in
        MYUSER database with the information in the Myuser object and return true. Otherwise, it returns
        false (and does not create the record)
         */

        Connection cnnct = null;
        Statement stmnt = null;
        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmnt.executeQuery("SELECT * FROM MYUSER WHERE UserId = '" + myuser.getUserid() + "'");
            if (!rs.isBeforeFirst()) {
                String preQueryStatement = "INSERT INTO MYUSER VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                pStmnt = cnnct.prepareStatement(preQueryStatement);
                pStmnt.setString(1, myuser.getUserid());
                pStmnt.setString(2, myuser.getName());
                pStmnt.setString(3, myuser.getPassword());
                pStmnt.setString(4, myuser.getEmail());
                pStmnt.setString(5, myuser.getPhone());
                pStmnt.setString(6, myuser.getAddress());
                pStmnt.setString(7, myuser.getSecQn());
                pStmnt.setString(8, myuser.getSecAns());
                int rowCount = pStmnt.executeUpdate();
                if (rowCount == 0) {
                    throw new SQLException("Cannot insert records!");
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
        return false;
    }

    Boolean updateRecord(Myuser myuser) {
        /*
        accepts a Myuser object and checks whether
        the actual record exists in the database. If it does, it will update the information of the record with
        the current information stored in the Myuser object and return true. Otherwise, it returns false
        without doing anything.
         */
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE MYUSER SET NAME = ?,PASSWORD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, SECQN = ?, SECANS = ? WHERE USERID = ? ";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, myuser.getName());
            pStmnt.setString(2, myuser.getPassword());
            pStmnt.setString(3, myuser.getEmail());
            pStmnt.setString(4, myuser.getPhone());
            pStmnt.setString(5, myuser.getAddress());
            pStmnt.setString(6, myuser.getSecQn());
            pStmnt.setString(7, myuser.getSecAns());
            pStmnt.setString(8, myuser.getUserid());
            int rowCount = pStmnt.executeUpdate();
            if (rowCount == 0) {
                throw new SQLException("Cannot insert records!");
            }
            return true;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
        return false;
    }

    Boolean deleteRecord(String userid) {
        /*
        accepts a String object whose value is the
        userId of a record to be deleted. If the record can be found, it removes the record in the database
        and return true. Otherwise, it returns false.
         */

        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int i = stmnt.executeUpdate("DELETE FROM MYUSER WHERE USERID = '" + userid+"'");
            if (i==1) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
        return false;
    }

    Myuser getRecord(String userID) {
        /*
        accepts a String object whose value is the userId of
    a record to be searched. If the record can be found, it returns a Myuser object that stores the
    information of the actual database record. Otherwise, it returns a “null” object
         */
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            String ID = "";
            String Name = "";
            String Password = "";
            String email = "";
            String phone = "";
            String address = "";
            String secQn = "";
            String secAns = "";
            cnnct = getConnection();
            stmnt = cnnct.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmnt.executeQuery("SELECT * FROM MYUSER WHERE USERID = '" + userID + "'");
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    ID = rs.getString("USERID");
                    Name = rs.getString("NAME");
                    Password = rs.getString("PASSWORD");
                    email = rs.getString("EMAIL");
                    phone = rs.getString("PHONE");
                    address = rs.getString("ADDRESS");
                    secQn = rs.getString("SECQN");
                    secAns = rs.getString("SECANS");

                }
                Myuser getuser = new Myuser(ID, Name, Password, email, phone, address, secQn, secAns);
                return getuser;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }

        return null;
    }
}
