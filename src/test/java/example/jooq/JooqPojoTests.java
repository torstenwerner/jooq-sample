package example.jooq;

import example.jooq.generated.tables.pojos.Author;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static example.jooq.generated.tables.Author.AUTHOR;

@SpringBootTest
public class JooqPojoTests {
    @Test
    void shouldFindAllAuthors(@Autowired DSLContext create) {
        final List<Author> authors = create.select().from(AUTHOR).fetchInto(Author.class);
        for (Author author : authors) {
            final Integer id = author.getId();
            final String firstName = author.getFirstName();
            final String lastName = author.getLastName();
            System.out.printf("Class: %s, Id: %d, author: %s %s\n", getClass().getSimpleName(), id, firstName, lastName);
        }
    }
}
