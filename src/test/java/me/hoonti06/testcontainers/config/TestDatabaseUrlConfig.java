package me.hoonti06.testcontainers.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("test")
@TestConfiguration
public class TestDatabaseUrlConfig {

  @Bean
  public HikariDataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:tc:mariadb:///test" + "?TC_INITFUNCTION=me.hoonti06.testcontainers.config.TestDatabaseUrlConfig::initSchema");
    return new HikariDataSource(config);
  }

  private void initSchema(Connection connection) throws SQLException {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(connection.getMetaData().getURL());

    Flyway flyway = Flyway
        .configure()
        .dataSource(dataSource)
        .load();
    flyway.migrate();
  }

}
