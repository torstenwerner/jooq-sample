select id, customer_id, amount, sum(amount) over w as total from amount
  window w as (partition by customer_id order by id rows between unbounded preceding and current row)
  order by customer_id, id;
