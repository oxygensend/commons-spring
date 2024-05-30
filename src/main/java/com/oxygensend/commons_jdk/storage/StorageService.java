package com.oxygensend.commons_jdk.storage;

import java.awt.image.BufferedImage;
import javax.imageio.ImageWriteParam;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    /**
     * Store the file in provided subdirectory path in root dir of storage service
     */
    String store(MultipartFile file, String destinationDir);

    /**
     * Store  file in root dir of storage service
     */
    String store(MultipartFile file);

    String storeImage(BufferedImage image, String outputFormat, String destinationDir, ImageWriteParam writeParam);

    Resource load(String filename) throws Exception;

    void delete(String filename);
}
