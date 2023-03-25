package me.hoonti06.testcontainers;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseCleanExtension implements BeforeEachCallback {

  @Override
  public void beforeEach(ExtensionContext context) {
    final ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
    final DatabaseCleanup databaseCleanup = (DatabaseCleanup) applicationContext.getBean("me.hoonti06.testcontainers.DatabaseCleanup");
    databaseCleanup.execute();
  }
}
