package org.example.core;

import java.io.PrintStream;

public class consoleCapturer extends PrintStream {
    private final PrintStream originalOut;
    private final StringBuilder buffer;

    public consoleCapturer(PrintStream originalOut) {
        super(originalOut);
        this.originalOut = originalOut;
        this.buffer = new StringBuilder();
    }

    @Override
    public void println(String x) {
        originalOut.println(x);
        buffer.append(x).append("\n");
    }

    @Override
    public void println(Object x) {
        originalOut.println(x);
        buffer.append(String.valueOf(x)).append("\n");
    }

    public String getCapturedLogs() {
        return buffer.toString();
    }
}