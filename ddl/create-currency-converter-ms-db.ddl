create sequence EXCHANGE_RATE_ID_SEQ start 1 increment 1;
create table EXCHANGE_RATE (EXCHANGE_RATE_ID int4 not null, FROM_CURRENCY varchar(255) not null, RATE float8 not null, TO_CURRENCY varchar(255) not null, primary key (EXCHANGE_RATE_ID));
create table MESSAGE_EVENT (TOPIC_ID varchar(255) not null, MESSAGE_REFERENCE varchar(255) not null, VERSION int4, primary key (TOPIC_ID));
