package com.flashlack.homeofesportsracingsimulatorsettings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 * @author FLASHLACK
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScans({
        @ComponentScan("com.flashlack.homeofesportsracingsimulatorsettings"),
        @ComponentScan(basePackageClasses = {
                com.xlf.utility.UtilProperties.class,
                com.xlf.utility.exception.PublicExceptionHandlerAbstract.class,
                com.xlf.utility.config.app.MybatisPlusConfiguration.class
        })
})
@EnableAsync
public class HomeofeSportsRacingSimulatorSettingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeofeSportsRacingSimulatorSettingsApplication.class, args);
    }

}
