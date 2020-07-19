declare
    OUT_FIRST_NAME    dbms_sql.varchar2_table;
    OUT_LAST_NAME     dbms_sql.varchar2_table;
    CURSOR_FIRST_NAME sys_refcursor;
    CURSOR_LAST_NAME  sys_refcursor;
begin
    update AUTHOR
    set FIRST_NAME = ?
    where LAST_NAME = ?
    returning FIRST_NAME, LAST_NAME bulk collect into OUT_FIRST_NAME, OUT_LAST_NAME;
    ? := sql%rowcount;
    open CURSOR_FIRST_NAME for select * from table(OUT_FIRST_NAME);
    open CURSOR_LAST_NAME for select * from table(OUT_LAST_NAME);
    ? := CURSOR_FIRST_NAME;
    ? := CURSOR_LAST_NAME;
end;
