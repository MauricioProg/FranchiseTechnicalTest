package com.tecnical_test.franchise_management.franchise_core;

import org.springframework.boot.SpringApplication;

public class TestFranchiseCoreApplication {

	public static void main(String[] args) {
		SpringApplication.from(FranchiseCoreApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
