INSERT INTO auth.users (active, created_at,updated_at,username,password) VALUES(true, now(), now(),'admin','$2a$10$jwdkhEwPP1hln.wDm0AEiuf8UcexF5jj/fDsM2vMV9H.Prcxcg./6');

INSERT INTO auth.modules (name) values('Usuarios');

INSERT INTO auth.modules (name) values('Roles');

INSERT INTO auth.actions (name,id_module) values ('Crear Usuarios',1);
INSERT INTO auth.actions (name,id_module) values ('Editar Usuarios',1);
INSERT INTO auth.actions (name,id_module) values ('Eliminar Usuarios',1);

INSERT INTO auth.actions (name,id_module) values ('Crear Roles',2);
INSERT INTO auth.actions (name,id_module) values ('Editar Roles',2);
INSERT INTO auth.actions (name,id_module) values ('Eliminar Roles',2);
