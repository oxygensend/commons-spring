package com.oxygensend.commons_jdk.storage;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(StorageProperties.class)
@Configuration
public class StorageConfiguration {

    @Bean
    StorageService imagesStoreService(StorageProperties storageProperties, FileSystem fileSystem) {
        return new LocalStorageService(storageProperties.rootLocation(), fileSystem);
    }

}
