package ru.aaivn.loganalyzer.reader;

/**
 * Adds getter for implemented java.io.Readers
 */
public interface Reader<T extends java.io.Reader> {
    T getReader();
}
