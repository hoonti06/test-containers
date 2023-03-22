package me.hoonti06.testcontainers.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
//import org.flywaydb.core.Flyway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

@Profile("test")
@TestConfiguration
public class TestDatabaseConfig {

  @Bean
  public MariaDBContainer mariaDbContainer() {
    final MariaDBContainer mariaDbContainer = new MariaDBContainer(DockerImageName.parse("mariadb:10.6"));
    mariaDbContainer.start();
    return mariaDbContainer;
  }

  @Bean
  @DependsOn("mariaDbContainer")
  public HikariDataSource dataSource(MariaDBContainer mariaDbContainer) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(mariaDbContainer.getJdbcUrl());
    config.setUsername(mariaDbContainer.getUsername());
    config.setPassword(mariaDbContainer.getPassword());
    HikariDataSource dataSource = new HikariDataSource(config);
    initSchema(dataSource);
    return dataSource;
  }

  private void initSchema(DataSource dataSource) {
//    Flyway flyway = Flyway
//        .configure()
//        .dataSource(dataSource)
//        .load();
//    flyway.migrate();
  }

  @Bean
  @DependsOn("dataSource")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource);
    emf.setPackagesToScan("me.hoonti06.testcontainers.entity");
    emf.setJpaVendorAdapter(jpaVendorAdapters());
    emf.setJpaProperties(jpaProperties());
    return emf;
  }

  @Bean
  @DependsOn("dataSource")
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
    return transactionManager;
  }

  private JpaVendorAdapter jpaVendorAdapters() {
    return new HibernateJpaVendorAdapter();
  }

  private Properties jpaProperties() {
    Properties jpaProperties = new Properties();
    jpaProperties.setProperty("hibernate.show_sql", "true");
    jpaProperties.setProperty("hibernate.format_sql", "true");
    jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create");
    jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB106Dialect");
    jpaProperties.setProperty("hibernate.physical_naming_strategy",
        "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
    return jpaProperties;
  }

}
