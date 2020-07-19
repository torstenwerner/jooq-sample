package example.jooq;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@SpringBootTest
public class JdbcUpdateTest {
    @Test
    void shouldUpdateUsingPlainJdbc(
            @Value("${spring.datasource.url}") String dataSourceUrl, @Autowired DataSource dataSource,
            @Value("classpath:/update-author-oracle.sql") Resource sql) throws SQLException, IOException {

        assumeThat(dataSourceUrl).contains(":oracle:");

        final String sqlText = IOUtils.toString(sql.getInputStream(), UTF_8);

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(sqlText)) {

            connection.setAutoCommit(false);
            statement.setQueryTimeout(30); // seconds

            statement.setString(1, "Hans");
            statement.setString(2, "von Mythenmetz");
            final long updatedRows = statement.executeLargeUpdate();

            assertThat(updatedRows).isEqualTo(1);

            connection.rollback();
            connection.setAutoCommit(true);
        }
    }
}
