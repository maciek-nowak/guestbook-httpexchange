package com.codecool.krk.mcnowak.dao;

import com.codecool.krk.mcnowak.exceptions.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    static Connection getConnection() throws DaoException {

        if (connection == null) {

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:data/guestbook.db");
            } catch (SQLException e) {
                throw new DaoException("DatabaseConnection class caused a problem!");
            }

        }

        return connection;
    }

    public static void closeConnection() throws DaoException {

        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("DatabaseConnection class caused a problem!");
        }

    }

}
