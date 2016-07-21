package example.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static example.jooq.generated.tables.Author.AUTHOR;
import static java.lang.String.format;

@Component
public class JooqRecordExample implements CommandLineRunner {
    private final DSLContext create;

    @Autowired
    public JooqRecordExample(DSLContext create) {
        this.create = create;
    }

    @Override
    public void run(String... args) throws Exception {
        final List<String> messages = create.selectFrom(AUTHOR).fetch(author -> {
            final Integer id = author.getId();
            final String firstName = author.getFirstName();
            final String lastName = author.getLastName();
            return format("Class: %s, Id: %d, author: %s %s\n", getClass().getSimpleName(), id, firstName, lastName);
        });
        messages.forEach(System.out::print);
    }
}
