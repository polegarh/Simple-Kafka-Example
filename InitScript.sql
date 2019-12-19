drop table Customer;

create table Customer(
customer_id number(10) primary key,
customer_first_name varchar(20),
customer_last_name varchar(20),
phone_number number(10)
);

drop table Product;

create table Product(
product_id number(10) primary key,
product_type varchar(20),
product_version varchar(25),
product_price varchar(15)
);

drop table Sales;

create table Sales(
transaction_id number(10) primary key,
customer_id number(10) references Customer(customer_id) on delete cascade,
product_id number(10) references Product(product_id) on delete cascade,
timestamp_sales varchar(35),
total_amount varchar(25),
total_quantity number(10)
);


drop table Refund;

create table Refund(
refund_id number(10) primary key,
transaction_id number(10) references Sales(transaction_id) on delete cascade,
customer_id number(10) references Customer(customer_id) on delete cascade,
product_id number(10) references Product(product_id) on delete cascade,
time_stamp_refund varchar(35),
refund_amount varchar(15),
refund_quantity number(10)
);