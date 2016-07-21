package example.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        final Result<Record> authors = create.select().from(AUTHOR).fetch();
        System.out.printf("authors:\n%s\n", authors);
    }
}
