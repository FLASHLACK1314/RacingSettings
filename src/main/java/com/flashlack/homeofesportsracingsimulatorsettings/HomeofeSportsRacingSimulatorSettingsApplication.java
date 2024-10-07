package com.flashlack.homeofesportsracingsimulatorsettings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 * @author FLASHLACK
 */
@SpringBootApplication(scanBasePackages = "com.flashlack.homeofesportsracingsimulatorsettings")
@EnableAsync
public class HomeofeSportsRacingSimulatorSettingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeofeSportsRacingSimulatorSettingsApplication.class, args);
    }

}
