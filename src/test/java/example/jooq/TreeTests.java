package example.jooq;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static example.jooq.generated.Tables.COMMENT;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.select;

@SpringBootTest
public class TreeTests {

    @Autowired
    private DSLContext dc;

    @Test
    void shouldAddArticleIdToSecondLevelComments() {

        final var commentArticle = name("comment_article");
        dc.with(commentArticle)
                .as(select(COMMENT.asterisk())
                        .from(COMMENT)
                        .where(COMMENT.ARTICLE_ID.isNotNull())
                        .limit(5)
                        .unionAll(
                                select(COMMENT.ID, COMMENT.BODY, COMMENT.PARENT_ID, COMMENT.comment().ARTICLE_ID)
                                        .from(COMMENT)
                                        .where(COMMENT.comment().ARTICLE_ID.isNotNull())))
                .selectFrom(commentArticle)
                .limit(10)
                .fetch()
                .format(System.out);
    }
}
