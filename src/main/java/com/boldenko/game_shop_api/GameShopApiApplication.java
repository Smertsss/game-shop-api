package com.boldenko.game_shop_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GameShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameShopApiApplication.class, args);
	}

}
