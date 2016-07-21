package example.jooq;

import example.jooq.generated.tables.pojos.Author;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static example.jooq.generated.tables.Author.AUTHOR;

@Component
public class JooqFlexExample implements CommandLineRunner {
    private final DSLContext create;

    @Autowired
    public JooqFlexExample(DSLContext create) {
        this.create = create;
    }

    @Override
    public void run(String... args) throws Exception {
        final Result<Record> authors = create.select().from(AUTHOR).fetch();
        for (Record author : authors) {
            final Integer id = author.getValue(AUTHOR.ID);
            final String firstName = author.getValue(AUTHOR.FIRST_NAME);
            final String lastName = author.getValue(author.field("LAST_NAME"), String.class);
            System.out.printf("Class: %s, Id: %d, author: %s %s\n", getClass().getSimpleName(), id, firstName, lastName);
        }
    }
}
