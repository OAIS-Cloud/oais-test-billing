insert into currencies (id, code, name)
    values
        (nextval('currencies_SEQ'), 'USD', 'Dollar'),
        (nextval('currencies_SEQ'), 'BRL', 'Real');

insert into contracts (id, currency_id, currency_value, invoice_closing_day)
    values
        (nextval('contracts_SEQ'), 1, 0.89, 10),
        (nextval('contracts_SEQ'), 51, 1.25, 31);
