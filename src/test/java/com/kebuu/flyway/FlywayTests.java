package com.kebuu.flyway;

import com.kebuu.Application;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.dbsupport.JdbcTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;

import java.sql.SQLException;

@SpringApplicationConfiguration(classes={Application.class})
public class FlywayTests {

    @Autowired private Flyway flyway;
    @Autowired private JdbcTemplate jdbcTemplate;

    @Test
    public void testMigration() throws SQLException {
        jdbcTemplate.execute("DROP ALL OBJECTS;");
        flyway.migrate();
    }
}
