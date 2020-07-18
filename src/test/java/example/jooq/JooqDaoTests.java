package example.jooq;

import example.jooq.generated.tables.daos.AuthorDao;
import example.jooq.generated.tables.pojos.Author;
import org.jooq.Configuration;
import org.jooq.impl.DefaultConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class JooqDaoTests {
    @Test
    void shouldFindAllAuthors(@Autowired DataSource dataSource) {
        final Configuration configuration = new DefaultConfiguration().set(dataSource);
        final AuthorDao authorDao = new AuthorDao(configuration);

        for (Author author : authorDao.findAll()) {
            final Integer id = author.getId();
            final String firstName = author.getFirstName();
            final String lastName = author.getLastName();
            System.out.printf("Class: %s, Id: %d, author: %s %s\n", getClass().getSimpleName(), id, firstName, lastName);
        }
    }
}
