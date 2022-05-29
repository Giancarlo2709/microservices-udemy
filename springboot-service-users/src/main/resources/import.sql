INSERT INTO users (username, password, status, name, last_name, email) VALUES ('giancarlo','12345',1,'Giancarlo','Yarleque','gian@gmail.com');
INSERT INTO users (username, password, status, name, last_name, email) VALUES ('admin','12345',1,'John','Doe','admin@gmail.com');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);