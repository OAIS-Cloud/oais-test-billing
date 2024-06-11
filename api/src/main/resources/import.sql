insert into currencies (id, code, name)
    values
        (nextval('currencies_SEQ'), 'USD', 'Dollar'),
        (nextval('currencies_SEQ'), 'BRL', 'Real');

insert into contracts (id, currency_id, currency_value, invoice_closing_day, created_at)
    values
        (nextval('contracts_SEQ'), 1, 0.12, 10, '2024-06-3T20:37:35.247Z'),
        (nextval('contracts_SEQ'), 1, 0.89, 10, '2024-06-3T22:33:35.247Z'),
        (nextval('contracts_SEQ'), 51, 0.32, 15, '2024-06-5T10:12:35.247Z'),
        (nextval('contracts_SEQ'), 1, 3, 12, '2024-06-6T10:37:35.247Z'),
        (nextval('contracts_SEQ'), 51, 1.1, 3, '2024-06-10T12:32:35.247Z'),
        (nextval('contracts_SEQ'), 51, 1.25, 31, '2024-06-10T12:37:35.247Z');
