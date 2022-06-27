package example.jooq;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static example.jooq.generated.Tables.COMMENT;
import static org.jooq.impl.DSL.*;

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

    @Test
    void shouldAddArticleIdToAllComments() {

        final var commentArticle = name("comment_article");
        dc.withRecursive(commentArticle)
                .as(select(COMMENT.asterisk())
                        .from(COMMENT)
                        .where(COMMENT.ARTICLE_ID.isNotNull())
                        .unionAll(
                                select(COMMENT.ID, COMMENT.BODY, COMMENT.PARENT_ID, field(commentArticle.append("article_id")))
                                        .from(COMMENT)
                                        .join(commentArticle)
                                        .on(COMMENT.PARENT_ID.eq(field(commentArticle.append("id"), long.class)))))
                .selectFrom(commentArticle)
                .limit(1000)
                .fetch()
                .format(System.out);
    }
}
