package org.example.assetmanagement;

import org.springframework.boot.SpringApplication;

public class TestAssetManagementApplication {

    public static void main(String[] args) {
        SpringApplication.from(AssetManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
