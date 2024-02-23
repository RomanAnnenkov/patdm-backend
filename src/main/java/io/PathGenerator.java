package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathGenerator {

    private final String originalFilePath;
    private final String pathSeparator = File.separator;

    public PathGenerator(String originalFilePath) {
        this.originalFilePath = originalFilePath;
    }

    public String getPath(String prefix, String extension) throws IOException {
        return createOutputDir() + pathSeparator + prefix + "_" + getFileName() + "." + extension;
    }

    private String getFileName() {
        String[] splitPath = originalFilePath.split(pathSeparator);
        String fileWithExtension = splitPath[splitPath.length - 1];
        return fileWithExtension.split("\\.")[0];
    }

    private String createOutputDir() throws IOException {
        File file = new File(originalFilePath);
        String dir = file.getParent() + pathSeparator + getFileName();
        Files.createDirectories(Path.of(dir));
        return dir;
    }
}
