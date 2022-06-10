create table user(
    id bigint not null,
    name varchar(255),
    email varchar(255),
    created_at timestamp,
    updated_at timestamp,
    primary key  (id)
);

create table book(
    id bigint not null,
    name varchar(255),
    category varchar(255),
    author_id bigint,
    publisher_id bigint,
    created_at timestamp,
    updated_at timestamp,
    primary key  (id)
);

create table book_review_info(
    id bigint not null,
    average_review_score real,
    review_count integer,
    book_id bigint,
    created_at timestamp,
    updated_at timestamp,
    primary key  (id)
);

create table product(
    product_id bigint not null,
    product_name varchar(255) not null,
    period integer,
    created_at timestamp,
    updated_at timestamp,
    primary key  (product_id)
);