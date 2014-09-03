package com.kebuu.test;

import org.flywaydb.core.internal.dbsupport.JdbcTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import javax.sql.DataSource;

public class WithDbSetupExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        JdbcTemplate jdbcTemplate = testContext.getApplicationContext().getBean(JdbcTemplate.class);
        DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);

        jdbcTemplate.execute("DROP ALL OBJECTS");

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("db/init/structure.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("db/init/cotation.sql"));

        DatabasePopulatorUtils.execute(resourceDatabasePopulator, dataSource);
    }
}
