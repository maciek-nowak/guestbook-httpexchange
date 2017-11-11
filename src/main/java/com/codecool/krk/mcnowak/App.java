package com.codecool.krk.mcnowak;

import com.sun.net.httpserver.HttpServer;
import com.codecool.krk.mcnowak.models.EntryList;

import java.net.InetSocketAddress;


public class App {
    public static void main(String[] args) throws Exception {
        EntryList entryList = new EntryList();
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/", new GuestBook(entryList));
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}
