package io.github.madushanka.pos;

import lk.ijse.dep.crypto.DEPCrypt;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("file:${user.dir}/resources/application.properties")
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(dataSource);
        lsfb.setPackagesToScan("io.github.madushanka.pos.entity");
        lsfb.setHibernateProperties(hibernateProperty());
        return lsfb;

    }
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dmds = new DriverManagerDataSource();
        dmds.setDriverClassName(env.getRequiredProperty("hibernate.connection.driver_class"));
        dmds.setUrl(env.getRequiredProperty("hibernate.connection.url"));
        dmds.setUsername(DEPCrypt.decode(env.getRequiredProperty("hibernate.connection.username"),"123"));
        dmds.setPassword(DEPCrypt.decode(env.getRequiredProperty("hibernate.connection.password"),"123"));
        return dmds;
    }

    private Properties hibernateProperty(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect",env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql",env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto",env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
    }
}
