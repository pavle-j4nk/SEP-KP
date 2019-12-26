package rs.ac.uns.ftn.sep.kp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import rs.ac.uns.ftn.sep.kp.properties.KpProperties;

@SpringBootApplication(scanBasePackages = "rs.ac.uns.ftn.sep")
@EnableConfigurationProperties(KpProperties.class)
@EnableEurekaClient
public class KoncentraltorPlacanjaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoncentraltorPlacanjaApplication.class, args);
    }

}
