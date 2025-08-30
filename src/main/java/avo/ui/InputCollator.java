package avo.ui;

/**
 * functional interface for functions that collate user inputs
 * and returns them as strings
 */
@FunctionalInterface
public interface InputCollator {
    String getInput();
}
