package me.hoonti06.testcontainers;

import me.hoonti06.testcontainers.config.TestDatabaseConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestDatabaseConfig.class)
@SpringBootTest
class TestContainerApplicationTests {

  @Test
  void contextLoads() {
  }

}
