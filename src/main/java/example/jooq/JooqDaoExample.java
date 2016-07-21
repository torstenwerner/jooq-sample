package example.jooq;

import example.jooq.generated.tables.daos.AuthorDao;
import example.jooq.generated.tables.pojos.Author;
import org.jooq.Configuration;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JooqDaoExample implements CommandLineRunner {
    private final AuthorDao authorDao;

    @Autowired
    public JooqDaoExample(DataSource dataSource) {
        final Configuration configuration = new DefaultConfiguration().set(dataSource);
        authorDao = new AuthorDao(configuration);
    }

    @Override
    public void run(String... args) throws Exception {
        for (Author author : authorDao.findAll()) {
            final Integer id = author.getId();
            final String firstName = author.getFirstName();
            final String lastName = author.getLastName();
            System.out.printf("Id: %d, author: %s %s\n", id, firstName, lastName);
        }
    }
}
