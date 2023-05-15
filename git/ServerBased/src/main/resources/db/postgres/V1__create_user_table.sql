-- This is now deprecated and not in use, but if you want to, this is how user table can be created
-- Jooq is also configured to be integrated easily

CREATE TABLE IF NOT EXISTS users
(
    username                  varchar(50)   not null,
    email                     varchar(100)  not null,
    password                  varchar(100)  not null,
    enabled                   boolean       not null default false,
    created_at                timestamp     not null,
    updated_at                timestamp     not null,
    last_logged_in_at         timestamp     not null,
    primary key(username)
);

CREATE UNIQUE INDEX idx_users_username ON users(username);
CREATE UNIQUE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username_password ON users(username, password);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_role_id              SERIAL PRIMARY KEY,
    username                  varchar(50)   not null,
    role                      varchar(20)   not null,
    unique(username, role),
    foreign key (username) references users (username)
);
