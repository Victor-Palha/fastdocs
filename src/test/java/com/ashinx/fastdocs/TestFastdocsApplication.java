package com.ashinx.fastdocs;

import org.springframework.boot.SpringApplication;

public class TestFastdocsApplication {

    public static void main(String[] args) {
        SpringApplication.from(FastdocsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
