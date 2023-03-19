package me.hoonti06.testcontainers;

import me.hoonti06.testcontainers.config.TestDatabaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import({TestDatabaseConfig.class, DatabaseCleanup.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DatabaseBaseTest {
  @Autowired private DatabaseCleanup databaseCleanup;

  @BeforeEach
  void setUp() {
    databaseCleanup.execute();
  }

}
