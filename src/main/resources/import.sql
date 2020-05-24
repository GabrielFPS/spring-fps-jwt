INSERT INTO users (username,password,enabled) values('gabriel','$2a$10$w3o7fyvo35Q8r6.SjCTYt.CAUc2Dwa.EbIgd2lUa5TO2lkUUGub82',1);
INSERT INTO users (username,password,enabled) values('gabo','$2a$10$HaBtTfxpOBjvJEshrFftUOTjjAHrdERu48ce4jekjKAoLn3FDT1iW',1);

INSERT INTO authorities (authority,user_id) values('ROLE_USER',1);
INSERT INTO authorities (authority,user_id) values('ROLE_USER',2);

INSERT INTO clientes (apellido,create_at, email,nombre) values ('Sagastume',sysdate(), 'gabriel.perez.zoft@gmail.com','Gabriel');
INSERT INTO clientes (apellido,create_at, email,nombre) values ('Perez',sysdate(), 'gabriel@gmail.com','Fernando');
