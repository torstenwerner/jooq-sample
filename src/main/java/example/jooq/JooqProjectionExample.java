package example.jooq;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static example.jooq.generated.tables.Author.AUTHOR;
import static java.lang.String.format;
import static org.jooq.impl.DSL.concat;
import static org.jooq.impl.DSL.val;

@Component
public class JooqProjectionExample implements CommandLineRunner {
    private final DSLContext create;

    @Autowired
    public JooqProjectionExample(DSLContext create) {
        this.create = create;
    }

    @Override
    public void run(String... args) throws Exception {
        final Field<String> field = concat(
                val(format("Class: %s, Id: ", getClass().getSimpleName())),
                AUTHOR.ID,
                val(", author: "),
                AUTHOR.FIRST_NAME,
                val(" "),
                AUTHOR.LAST_NAME,
                val("\n"));
        final List<String> messages = create.select(field).from(AUTHOR).fetch(field);
        messages.forEach(System.out::print);
    }
}
