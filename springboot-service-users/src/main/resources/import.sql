INSERT INTO users (username, password, status, name, last_name, email) VALUES ('giancarlo','$2a$10$buU53tfnykk4elyO9spXMebqGGJvrHKLxLBGXStGdp4pmAHJ5u1Ce',1,'Giancarlo','Yarleque','gian@gmail.com');
INSERT INTO users (username, password, status, name, last_name, email) VALUES ('admin','$2a$10$rn7Ua8szzbJXSAdU34VwseTV21nmJS4aMSu6EPDSYhejlti9KlqQO',1,'John','Doe','admin@gmail.com');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);