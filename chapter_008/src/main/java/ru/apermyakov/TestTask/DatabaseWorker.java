package ru.apermyakov.TestTask;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for build main actions with database.
 *
 * @author apermyakov
 * @version 1.0
 * @since 05.12.2017
 */
public class DatabaseWorker {

    /**
     * Field for log4j logger.
     */
    private static Logger logger = Logger.getLogger(DatabaseWorker.class);

    /**
     * Field for map of scripts.
     */
    private Map<String, String> scripts = new HashMap<>();

    /**
     * Design database worker.
     *
     * @param scripts map of scripts
     */
    DatabaseWorker(Map<String, String> scripts) {
        this.scripts = scripts;
    }

    /**
     * Method to build connection to database.
     *
     * @return connection object
     * @throws SQLException sql e
     */
    private Connection connect() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql:" + this.scripts.get("connect"), this.scripts.get("login"), this.scripts.get("password"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Method for initial vacancy and renewal tables.
     *
     * @param connection connection
     * @return true if success
     * @throws SQLException sql e
     */
    private boolean initialTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(this.scripts.get("createvacancy"));
        statement.executeUpdate(this.scripts.get("createrenewal"));
        statement.close();
        return true;
    }

    /**
     * Method for add vacancy to database if not exist.
     *
     * @param connection connection object
     * @param name       name of vacancy
     * @param href       href of vacancy
     * @param data       data of vacancy
     * @throws SQLException sql e
     */
    private void addToBase(Connection connection, String name, String href, String data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.scripts.get("insertvacancy"));
        statement.setString(1, name);
        statement.setString(2, href);
        statement.setString(3, data);
        statement.setString(4, name);
        statement.setString(5, href);
        statement.executeUpdate();
        statement.close();
    }

    /**
     * Method for end record to database.
     *
     * @param connection connection object
     * @return true if success
     * @throws SQLException sql e
     */
    private boolean endRecord(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.scripts.get("insertrenewal"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        statement.setString(1, dateFormat.format(calendar.getTime()));
        statement.executeUpdate();
        statement.close();
        return true;
    }

    /**
     * Method for check renewal to first start or not.
     *
     * @param connection connection object
     * @return false if first else false
     * @throws SQLException sql e
     */
    private boolean checkRenewal(Connection connection) throws SQLException {
        boolean result = false;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(this.scripts.get("selectrenewal"));
        if (resultSet.next()) {
            result = true;
        }
        statement.close();
        return result;
    }

    /**
     * Method for select results from database.
     *
     * @param connection connection object
     * @return true if success
     * @throws SQLException sql e
     */
    private boolean results(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(this.scripts.get("selectvacancy"));
        while (resultSet.next()) {
            logger.log(Level.INFO, (String.format("%s %s %s", resultSet.getString("vacancyname"), resultSet.getString("vacancyhref"), resultSet.getString("vacancydata"))));
        }
        statement.close();
        return true;
    }

    /**
     * Method for main activity of database worker.
     *
     * @param item chose item.
     * @return method's result
     */
    public boolean mainActivity(String item) {
        Connection connection = null;
        boolean result = false;
        try {
            connection = this.connect();
            if (("initial").equals(item)) {
                result = this.initialTable(connection);
            }
            if (("end").equals(item)) {
                result = this.endRecord(connection);
            }
            if (("check").equals(item)) {
                result = this.checkRenewal(connection);
            }
            if (("result").equals(item)) {
                result = this.results(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Overload main activity for add vacancy to database.
     *
     * @param item item
     * @param name vacancy's name
     * @param href vacancy's href
     * @param data vacancy's data
     */
    public void mainActivity(String item, String name, String href, String data) {
        Connection connection = null;
        try {
            connection = this.connect();
            if (("add").equals(item)) {
                this.addToBase(connection, name, href, data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}