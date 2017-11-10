package com.codecool.krk.mcnowak.dao;

import com.codecool.krk.mcnowak.exceptions.DaoException;
import com.codecool.krk.mcnowak.models.Entry;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EntryDao {

    private final Connection connection;
    private PreparedStatement stmt = null;

    public EntryDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        Date entryDate = dateFormatter.parse(dateString);

        return entryDate;
    }

    private String convertDateToString(Date purchaseDate) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String entryDateString = dateFormatter.format(purchaseDate);

        return entryDateString;
    }

    public ArrayList<Entry> getAllEntries() throws DaoException {

        ArrayList<Entry> foundEntries = new ArrayList<>();
        String sqlQuery = "SELECT * FROM messages";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                String content = result.getString("message");
                String name = result.getString("user_name");
                String entryDateString = result.getString("message_date");
                Date entryDate = this.parseDate(entryDateString);

                Entry entry = new Entry(content, name, entryDate);
                foundEntries.add(entry);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return foundEntries;
    }

    public void save(Entry entry) throws DaoException {

        String message = entry.getContent();
        String name = entry.getName();
        Date entryDate = entry.getDate();
        String entryDateString = this.convertDateToString(entryDate);

        String sqlQuery = "INSERT INTO messages "
                + "(message, user_name, message_date) "
                + "VALUES (?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, message);
            stmt.setString(2, name);
            stmt.setString(3, entryDateString);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

}
