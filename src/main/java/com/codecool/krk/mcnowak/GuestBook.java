package com.codecool.krk.mcnowak;

import com.codecool.krk.mcnowak.dao.EntryDao;
import com.codecool.krk.mcnowak.exceptions.DaoException;
import com.codecool.krk.mcnowak.models.Entry;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.codecool.krk.mcnowak.models.EntryList;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.io.IOException;
import java.io.OutputStream;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestBook implements HttpHandler {
    private EntryList entryList;
    private EntryDao entryDao = new EntryDao();

    public GuestBook(EntryList entryList) throws DaoException {
        this.entryList = entryList;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println(formData);
            Map inputs = parseFormData(formData);

            String name = inputs.get("name").toString();
            String content = inputs.get("content").toString();

            Entry entry = new Entry(content, name);
            this.entryList.addEntry(entry);

            try {
                this.entryDao.save(entry);
            } catch (DaoException e) {
                e.printStackTrace();
            }

        }

        List<Entry> entriesObjects = this.entryList.getEntryList();
        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/index.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with values
        model.with("entries", entriesObjects);

        // render a template to a string
        String response = template.render(model);

        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

}
