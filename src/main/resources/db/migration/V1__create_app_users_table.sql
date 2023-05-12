CREATE TABLE app_users (id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(200) NOT NULL,
                        password VARCHAR(200) NOT NULL);

CREATE TABLE app_users_roles (user_id BIGINT NOT NULL,
                              role VARCHAR(255) NOT NULL,
                              CONSTRAINT fk_user_role FOREIGN KEY (user_id) REFERENCES app_users (id));