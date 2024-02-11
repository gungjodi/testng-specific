package io.github.gungjodi.testngspecificstarter.commons;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author gungjodi
 * @version $Id: FileUtils.java, v1.0 2023‐07‐17 18.44 gungjodi Exp $$
 */
public class FileUtils {
    public static File getFileFromResource(String pathFromSourceRoot) {
        try {
            URL url = FileUtils.class.getClassLoader().getResource(pathFromSourceRoot);
            return Paths.get(Objects.requireNonNull(url).toURI()).toFile();
        } catch (URISyntaxException e) {
            return null;
        }
    }
}

