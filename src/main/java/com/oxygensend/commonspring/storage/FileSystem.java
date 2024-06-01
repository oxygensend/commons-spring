package com.oxygensend.commonspring.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;

final class FileSystem {

    public void save(InputStream stream, Path destination) throws IOException {
        Files.copy(stream, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    public void delete(Path path) throws IOException {
        FileSystemUtils.deleteRecursively(path);
    }

    public void createDirectory(Path dir) throws IOException {
        if (!Files.isDirectory(dir)) {
            Files.createDirectories(dir);
        }
    }

    public Resource getResource(Path path) throws MalformedURLException {
        return new UrlResource(path.toUri());
    }

}
