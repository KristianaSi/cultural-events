package com.kristiana.culturalevents.persitance.repository;

import java.nio.file.Path;

enum JsonPathFactory {
    USERS("users.json"),
    COMMENTS("comments.json"),
    EVENTS("events.json"),
    LOCATIONS("locations.json");

    private static final String DATA_DIRECTORY = "data";
    private final String fileName;

    JsonPathFactory(String fileName) {
        this.fileName = fileName;
    }

    public Path getPath() {
        return Path.of(DATA_DIRECTORY, this.fileName);
    }
}