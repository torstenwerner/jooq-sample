package example.jooq;

import example.jooq.generated.tables.Amount;
import example.jooq.generated.tables.records.AmountRecord;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static example.jooq.generated.tables.Amount.AMOUNT;
import static org.jooq.impl.DSL.sum;

@Component
public class JooqWindowExample implements CommandLineRunner {
    private final DSLContext create;

    @Autowired
    public JooqWindowExample(DSLContext create) {
        this.create = create;
    }

    @Override
    public void run(String... args) throws Exception {
        final int offset = args.length > 0 ? Integer.valueOf(args[0]) : 0;
        final int limit = args.length > 1 ? Integer.valueOf(args[1]) : Integer.MAX_VALUE;

        final Amount a = AMOUNT.as("a");

        final Field<BigDecimal> totalField = sum(a.TRANSACTION).over().
                partitionBy(a.CUSTOMER_ID).
                orderBy(a.ID.asc()).
                rowsBetweenUnboundedPreceding().
                andCurrentRow()
                .as("total");

        final Result<Record4<Integer, Integer, BigDecimal, BigDecimal>> amounts = create.
                select(a.ID, a.CUSTOMER_ID, a.TRANSACTION, totalField).
                from(a).
                orderBy(a.CUSTOMER_ID.asc(), a.ID.desc()).
                limit(offset, limit).
                fetch();

        for (Record amount : amounts) {
            final AmountRecord amountRecord = amount.into(a);
            final int id = amountRecord.getId();
            final int customerId = amountRecord.getCustomerId();
            final BigDecimal transaction = amountRecord.getTransaction();
            final BigDecimal total = amount.getValue(totalField);
            System.out.printf("amount id: %d, customer: %d, txn: %6.2f, total: %6.2f\n", id, customerId, transaction, total);
        }
    }
}
