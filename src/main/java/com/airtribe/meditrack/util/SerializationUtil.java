package com.airtribe.meditrack.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class SerializationUtil {
    private SerializationUtil() {}

    public static void write(Object value, Path path) throws IOException {
        if (path.getParent() != null) Files.createDirectories(path.getParent());
        try (OutputStream output = Files.newOutputStream(path); ObjectOutputStream objects = new ObjectOutputStream(output)) {
            objects.writeObject(value);
        }
    }

    public static Object read(Path path) throws IOException, ClassNotFoundException {
        try (InputStream input = Files.newInputStream(path); ObjectInputStream objects = new ObjectInputStream(input)) {
            return objects.readObject();
        }
    }
}
