INSERT INTO ROLE (id, is_deleted, role_name) VALUES
  (1, FALSE, 'USER');

INSERT INTO ROLE (id, is_deleted, role_name) VALUES
  (2, FALSE, 'ADMIN');

INSERT INTO USER (id, accountnonexpired ,accountnonlocked ,credentialsnonexpired ,is_deleted ,enabled ,password ,created_date ,username ) VALUES
  (1, TRUE, TRUE, TRUE,FALSE, TRUE,'$2a$10$xJ3780pcvD/knNqsOI6cWuCpU8qIT5OGbElWPgKD/gzCLZkFKKwau',{ts '2012-09-17 18:47:52.69'},'user');
--password = password
INSERT INTO USER (id, accountnonexpired ,accountnonlocked ,credentialsnonexpired ,is_deleted ,enabled ,password ,created_date ,username ) VALUES
  (2, TRUE, TRUE, TRUE,FALSE, TRUE,'$2a$10$xJ3780pcvD/knNqsOI6cWuCpU8qIT5OGbElWPgKD/gzCLZkFKKwau',{ts '2012-09-17 18:47:52.69'},'admin');
--password = password
INSERT INTO USER_ROLE (id, IS_DELETED ,ROLE_ID ,USER_ID ) VALUES
  (1, FALSE, 1,1);

 INSERT INTO USER_ROLE (id, IS_DELETED ,ROLE_ID ,USER_ID ) VALUES
  (2, FALSE, 2,2);
