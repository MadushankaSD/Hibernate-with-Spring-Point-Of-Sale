package io.github.madushanka.pos;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HibernateConfig.class)
@ComponentScan(basePackages = "io.github.madushanka.pos")
public class AppConfig {

}
