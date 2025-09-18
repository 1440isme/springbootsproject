package vn.binh.springbootsproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import vn.binh.springbootsproject.config.StorageProperties;
import vn.binh.springbootsproject.service.IStorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringbootsprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootsprojectApplication.class, args);
    }

    @Bean
    CommandLineRunner init(IStorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

}
