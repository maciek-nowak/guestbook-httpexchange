package com.codecool.krk.mcnowak.views;

import com.codecool.krk.mcnowak.models.Entry;
import com.codecool.krk.mcnowak.models.EntryList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class EntryListView {

    private String convertDateToString(Date purchaseDate) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String entryDateString = dateFormatter.format(purchaseDate);

        return entryDateString;
    }

    public String getFullList(EntryList entryList) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Entry> iterator = entryList.getIterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            String dateString = this.convertDateToString(entry.getDate());
            stringBuilder.append("<tr>\n<td>");
            stringBuilder.append("" + entry.getContent());
            stringBuilder.append("<br>Name: " + entry.getName());
            stringBuilder.append("<br>Date: " + dateString);
            stringBuilder.append("<br><br><br></td>\n</tr>\n");
        }
        return stringBuilder.toString();
    }
}
