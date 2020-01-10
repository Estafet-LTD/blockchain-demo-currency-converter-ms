create table EXCHANGE_RATE (CURRENCY varchar(255) not null, RATE float8 not null, primary key (CURRENCY));
create table MESSAGE_EVENT (TOPIC_ID varchar(255) not null, MESSAGE_REFERENCE varchar(255) not null, VERSION int4, primary key (TOPIC_ID));
