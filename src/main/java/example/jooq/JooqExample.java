package example.jooq;

import example.jooq.generated.tables.pojos.Author;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static example.jooq.generated.tables.Author.AUTHOR;

@Component
public class JooqExample implements CommandLineRunner {
    private final DSLContext create;

    @Autowired
    public JooqExample(DSLContext create) {
        this.create = create;
    }

    @Override
    public void run(String... args) throws Exception {
        final List<Author> authors = create.select().from(AUTHOR).fetchInto(Author.class);
        for (Author author : authors) {
            final Integer id = author.getId();
            final String firstName = author.getFirstName();
            final String lastName = author.getLastName();
            System.out.printf("Id: %d, author: %s %s\n", id, firstName, lastName);
        }
    }
}
