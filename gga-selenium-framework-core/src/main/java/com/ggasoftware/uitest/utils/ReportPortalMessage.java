package com.ggasoftware.uitest.utils;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;

import java.io.File;

public class ReportPortalMessage {

    private ByteSource data;

    private String message;

    public ReportPortalMessage() {
    }

    public ReportPortalMessage(String message) {
        this.message = message;
    }

    public ReportPortalMessage(final ByteSource data, String message) {
        this(message);
        this.data = data;
    }

    public ReportPortalMessage(File file, String message) {
        this(message);
        data = Files.asByteSource(file);
    }
}
