package com.codecool.krk.mcnowak.models;

import com.codecool.krk.mcnowak.dao.EntryDao;
import com.codecool.krk.mcnowak.exceptions.DaoException;

import java.util.Iterator;
import java.util.List;

public class EntryList {
    private List<Entry> entryList;

    public EntryList() throws DaoException {
        this.entryList = new EntryDao().getAllEntries();
    }

    public void addEntry(Entry entry) {
        this.entryList.add(entry);
    }

    public Iterator<Entry> getIterator() {
        return this.entryList.iterator();
    }

}
