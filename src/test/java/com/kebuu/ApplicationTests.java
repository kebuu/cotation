package com.kebuu;

import com.kebuu.config.SpringConfig;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.test.DataForTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class ApplicationTests {

    @Autowired private SpringConfig config;

    @Test
    public void testSpringConfig() throws SQLException {
        BuiltCotations builtCotations = config.cotationBuilders().basedOn(new Cotations(DataForTests.cotations()));
        assertThat(builtCotations).isNotNull();
    }
}
