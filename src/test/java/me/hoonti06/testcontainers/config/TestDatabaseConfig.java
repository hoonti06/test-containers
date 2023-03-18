package me.hoonti06.testcontainers.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

@Profile("test")
//@TestConfiguration
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
    Flyway flyway = Flyway
        .configure()
        .dataSource(dataSource)
        .load();
    flyway.migrate();
  }

}
