package ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Event;
import model.EventLog;

/**
 * Represents a file printer for printing the log to file.
 */
public class LogPrinter {

    // EFFECTS: prints events that have been logged to console since application start
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next);
        }
    }
}
