package com.oxygensend.commons_jdk.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

final class LocalStorageService implements StorageService {

    private final Path rootDir;
    private final FileSystem fileSystem;


    LocalStorageService(String rootDir, FileSystem fileSystem) {
        validatePath(rootDir);
        this.rootDir = Paths.get(rootDir);
        this.fileSystem = fileSystem;
        init();
    }

    @Override
    public String store(MultipartFile file) {
        return store(file, null);
    }

    @Override
    public String storeImage(BufferedImage image, String outputFormat, String destinationDir, ImageWriteParam writeParam) {
        var fileName = getRandomFileName() + '.' + outputFormat;
        var destinationFile = resolveDestinationPath(destinationDir, fileName);
        var file = new File(destinationFile.toString());

        try (var fios = new FileImageOutputStream(file)) {
            ImageWriter writer = ImageIO.getImageWritersByFormatName(outputFormat).next();
            if (writer == null) {
                throw new StorageException("No writer found for mime type " + outputFormat);
            }

            writer.setOutput(fios);
            writer.write(null, new IIOImage(image, null, null), writeParam);
            writer.dispose();
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + e.getMessage());
        }

        return destinationFile.getFileName().toString();
    }

    @Override
    public String store(MultipartFile file, String destinationDir) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        var fileName = getRandomFileName() + '.' + getFileExtension(file.getOriginalFilename());
        var destinationFile = resolveDestinationPath(destinationDir, fileName);

        try (InputStream inputStream = file.getInputStream()) {
            fileSystem.save(inputStream, destinationFile);
        } catch (IOException e) {
            throw new StorageException("Failed to store file: " + e.getMessage());
        }
        return destinationFile.getFileName().toString();
    }

    @Override
    public Resource load(String filename) throws Exception {
        try {
            var file = loadFile(filename);
            var resource = fileSystem.getResource(file);
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("Could not read file " + filename);
            }
        } catch (MalformedURLException e) {
            throw new Exception("Could not read file " + filename);
        }
    }

    @Override
    public void delete(String filename) {
        try {
            fileSystem.delete(rootDir.resolve(filename));
        } catch (IOException e) {
            throw new StorageException("Cannot delete file " + filename);
        }
    }

    private Path resolveDestinationPath(String destinationDir, String fileName) {
        var subDirPath = Optional.ofNullable(destinationDir)
                                 .map(Paths::get)
                                 .orElse(Paths.get(""));
        return rootDir.resolve(subDirPath)
                      .resolve(Paths.get(fileName))
                      .normalize()
                      .toAbsolutePath();
    }

    private String getRandomFileName() {
        return UUID.randomUUID().toString();
    }

    private String getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                       .filter(f -> f.contains("."))
                       .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                       .orElseThrow(() -> new StorageException("Cannot read extension for file " + filename));
    }

    private Path loadFile(String filename) {
        return rootDir.resolve(filename);
    }

    private void validatePath(String uploadDir) {
        if (!StringUtils.hasLength(uploadDir)) {
            throw new StorageException("File upload location cannot be empty");
        }
    }

    private void init() {
        try {
            fileSystem.createDirectory(rootDir);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage: " + e.getMessage());
        }
    }
}
