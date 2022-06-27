package example.jooq;

import example.jooq.generated.tables.Amount;
import example.jooq.generated.tables.records.AmountRecord;
import org.jooq.Record;
import org.jooq.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static example.jooq.generated.tables.Amount.AMOUNT;
import static org.jooq.impl.DSL.sum;

@SpringBootTest
public class JooqWindowTests {
    @Test
    void shouldFindAllAuthors(@Autowired DSLContext create) {
        final int offset = 0;
        final int limit = Integer.MAX_VALUE;

        final Amount a = AMOUNT.as("a");

        final Field<BigDecimal> totalField = sum(a.AMOUNT_).over().
                partitionBy(a.CUSTOMER_ID).
                orderBy(a.ID.asc()).
                rowsBetweenUnboundedPreceding().
                andCurrentRow()
                .as("total");

        final Result<Record4<Integer, Integer, BigDecimal, BigDecimal>> amounts = create.
                select(a.ID, a.CUSTOMER_ID, a.AMOUNT_, totalField).
                from(a).
                orderBy(a.CUSTOMER_ID.asc(), a.ID.desc()).
                limit(offset, limit).
                fetch();

        for (Record amount : amounts) {
            final AmountRecord amountRecord = amount.into(a);
            final int id = amountRecord.getId();
            final int customerId = amountRecord.getCustomerId();
            final BigDecimal transaction = amountRecord.getAmount();
            final BigDecimal total = amount.getValue(totalField);
            System.out.printf("amount id: %d, customer: %d, txn: %6.2f, total: %6.2f\n", id, customerId, transaction, total);
        }
    }
}
