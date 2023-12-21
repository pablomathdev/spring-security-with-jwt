INSERT INTO tb_user (cpf,rg,first_name,last_name,email,phone,birth_date,password) VALUES (0030243345,564357,'James','Smith','jamessmith@email.com','95991456443','2001-09-12','$2a$10$/C9YlFGBfZuUQOcsNtEXnuE27wI23aUqi9ttlop82oOaWPmmzXN4O');
INSERT INTO tb_user (cpf,rg,first_name,last_name,email,phone,birth_date,password) VALUES (0098989989,534357,'Alex','Wayne','alexwayne@email.com','95991456789','1999-02-04','$2a$10$aK.sCMvE1IDy4YAXpHjTpu.PkCwzVlHRuXGKWnI72seBB3al4tDsG');


INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');


INSERT INTO tb_user_role (user_id,role_id) VALUES (1,1);
INSERT INTO tb_user_role (user_id,role_id) VALUES (2,1);
INSERT INTO tb_user_role (user_id,role_id) VALUES (2,2);