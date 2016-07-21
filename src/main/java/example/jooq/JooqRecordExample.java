package example.jooq;

import example.jooq.generated.tables.records.AuthorRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static example.jooq.generated.tables.Author.AUTHOR;

@Component
public class JooqRecordExample implements CommandLineRunner {
    private final DSLContext create;

    @Autowired
    public JooqRecordExample(DSLContext create) {
        this.create = create;
    }

    @Override
    public void run(String... args) throws Exception {
        final Result<AuthorRecord> authors = create.selectFrom(AUTHOR).fetch();
        for (AuthorRecord author : authors) {
            final Integer id = author.getId();
            final String firstName = author.getFirstName();
            final String lastName = author.getLastName();
            System.out.printf("Class: %s, Id: %d, author: %s %s\n", getClass().getSimpleName(), id, firstName, lastName);
        }
    }
}
