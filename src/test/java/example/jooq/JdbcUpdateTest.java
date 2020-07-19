package example.jooq;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

/**
 * Using jdbc for statements with output parameters is not easy. See https://stackoverflow.com/a/10465239/1581276
 * for an example.
 */
@SpringBootTest
public class JdbcUpdateTest {
    @Test
    void shouldUpdateUsingPlainJdbc(
            @Value("${spring.datasource.url}") String dataSourceUrl, @Autowired DataSource dataSource,
            @Value("classpath:/update-author-oracle.sql") Resource sql) throws SQLException, IOException {

        assumeThat(dataSourceUrl).contains(":oracle:");

        final String sqlText = IOUtils.toString(sql.getInputStream(), UTF_8);

        try (final Connection connection = dataSource.getConnection();
             final CallableStatement statement = connection.prepareCall(sqlText)) {

            connection.setAutoCommit(false);
            statement.setQueryTimeout(30); // seconds

            statement.setString(1, "Hans");
            statement.setString(2, "von Mythenmetz");
            statement.registerOutParameter(3, Types.INTEGER);
            statement.registerOutParameter(4, -10); // OracleTypes.CURSOR
            statement.registerOutParameter(5, -10); // OracleTypes.CURSOR

            final boolean result = statement.execute();

            assertThat(result).isFalse();

            final int updatedRows = statement.getInt(3);
            assertThat(updatedRows).isEqualTo(1);
            final ResultSet firstName = (ResultSet) statement.getObject(4);
            assertThat(firstName.next()).isTrue();
            assertThat(firstName.getString(1)).isEqualTo("Hans");
            final ResultSet lastName = (ResultSet) statement.getObject(5);
            assertThat(lastName.next()).isTrue();
            assertThat(lastName.getString(1)).isEqualTo("von Mythenmetz");

            connection.rollback();
            connection.setAutoCommit(true);
        }
    }
}
