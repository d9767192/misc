package org.misc;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.misc.util.Apps;

import java.io.IOException;

import static org.misc.ConstVar.*;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class);

    public static void main(String[] args) throws IOException {

        // set configuration
        Configuration config = Apps.getConfiguration();
        LOGGER.debug("The configuration is ready.");

        // setup connection
        Connection conn = Apps.getConnection(config.getUrlBondDaily(), USER_AGENT, REFERRER, TIME_OUT);
        LOGGER.debug("The connection is ready.");

        // execute connection
        Connection.Response resp = conn.execute();
        LOGGER.debug("The connection has been tested.");

        // get connection response status code
        if (resp.statusCode() != 200) {
            LOGGER.error(String.format("The connection response status code is %s. " +
                    "Please check if the internet is working.", resp.statusCode()));
            return;
        }
        LOGGER.error(String.format("The connection response status code is %s. %n", resp.statusCode()));

        // convert HTML to doc
        Document doc = conn.get();
        LOGGER.debug("The HTML has been converted as a Document object.");

        // select all tables
        Elements tables = doc.select(TABLE); // get all tables
        if (tables.size() <= 0) {
            LOGGER.debug(String.format("<%s> was not found.", TABLE));
            return;
        }
        LOGGER.debug(String.format("Got all <%s>.", TABLE));

        // get a specific table
        Element table = Apps.searchTable(tables, CLASS, YUI_TEXT_LEFT);
        if (table == null) {
            LOGGER.debug(String.format("<%s %s=%s> was not found.", TABLE, CLASS, YUI_TEXT_LEFT));
            return;
        }
        LOGGER.debug(String.format("Got the <%s %s=%s>.", TABLE, CLASS, YUI_TEXT_LEFT));

        // get a specific table
        table = Apps.searchTable(table.select(TABLE), BGCOLOR, BGCOLOR_VALUE);
        if (table == null) {
            LOGGER.debug(String.format("<%s %s=%s> was not found.", TABLE, BGCOLOR, BGCOLOR_VALUE));
            return;
        }
        LOGGER.debug(String.format("Got the <%s %s=%s>.", TABLE, BGCOLOR, BGCOLOR_VALUE));


        System.out.printf("table: %s%n", table);




    }

}
