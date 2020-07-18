package example.jooq;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static example.jooq.generated.tables.Author.AUTHOR;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class JooqUpdateTests {
    @Test
    void shouldUpdateOneAuthor(@Autowired DSLContext create) {

        final int updatedRows = create
                .update(AUTHOR)
                .set(AUTHOR.FIRST_NAME, "Hans")
                .where(AUTHOR.LAST_NAME.eq("von Mythenmetz"))
                .execute();

        assertThat(updatedRows).isEqualTo(1);

        final String firstName = create
                .select(AUTHOR.FIRST_NAME)
                .from(AUTHOR)
                .where(AUTHOR.LAST_NAME.eq("von Mythenmetz"))
                .fetchOne(AUTHOR.FIRST_NAME);

        assertThat(firstName).isEqualTo("Hans");
    }
}
