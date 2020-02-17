drop table if exists transfer_account;

create table transfer_account
(
    id    int primary key auto_increment,
    name  varchar(255) unique not null,
    money float default null
);

insert into transfer_account (id, name, money)
values (1, 'jack.zhang', 1000);
insert into transfer_account (id, name, money)
values (2, 'kevin.yu', 1000);

select *
from transfer_account;
