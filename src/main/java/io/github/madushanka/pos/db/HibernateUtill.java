package io.github.madushanka.pos.db;

import io.github.madushanka.pos.entity.Customer;
import io.github.madushanka.pos.entity.Item;
import io.github.madushanka.pos.entity.Order;
import io.github.madushanka.pos.entity.OrderDetail;
import lk.ijse.dep.crypto.DEPCrypt;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUtill {
    private static SessionFactory sessionFactory = buildSessionFactory();
    private static String password;
    private static String user;
    private static String port;
    private static String db;
    private static String ip;

    private static SessionFactory buildSessionFactory() {


        File file = new File("src/main/resources/application.properties");
        Properties properties = new Properties();

        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            properties.load(fileInputStream);

        } catch (Exception e) {
            Logger.getLogger("io.github.madushanka.pos.db.HibernateUtill").log(Level.SEVERE,"null",e);
            System.exit(2);
        }

        password=DEPCrypt.decode(properties.getProperty("hibernate.connection.username"),"123");
        user=DEPCrypt.decode(properties.getProperty("hibernate.connection.password"),"123");
        ip=properties.getProperty("pos.ip");
        db=properties.getProperty("pos.db");
        port=properties.getProperty("pos.port");

        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .loadProperties(file)
                .applySetting("hibernate.connection.username", DEPCrypt.decode(properties.getProperty("hibernate.connection.username"),"123"))
                .applySetting("hibernate.connection.password",DEPCrypt.decode(properties.getProperty("hibernate.connection.password"),"123"))
                .build();


        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetail.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();


        return metadata.getSessionFactoryBuilder()
                .build();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public static String getPassword(){ return password; }
    public static String getUser(){ return user; }
    public static String getPort(){
        return port;
    }
    public static String getDB(){
        return db;
    }
    public static String getIp(){
        return ip;
    }

}
