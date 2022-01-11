/*tbl_tipos_identificacion*/
create table tbl_tipos_identificacion
(
secuencia_tipo_identificacion int primary key auto_increment,
nombre varchar(50) not null,
es_activo  bool not null
);

/*tbl_generos*/
create table tbl_generos
(
secuencia_genero int primary key auto_increment,
nombre varchar(50) not null,
esActivo  bool not null
);

/*tbl_empresas*/
create table tbl_empresas
(
secuencia_empresa int primary key auto_increment,
nombre varchar(50) not null,
descripcion varchar(100) not null,
esActivo  bool not null
);

/*tbl_personas*/
create table tbl_personas
(
secuencia_persona int primary key auto_increment,
secuencia_tipo_identificacion int not null,
numero_identificacion varchar(50) not null,
primer_nombre varchar(50) not null,
segundo_nombre varchar(50),
primer_apellido varchar(50) not null,
segundo_apellido varchar(50),
fecha_nacimiento date not null,
fecha_ingreso date not null,
usuario_ingreso varchar(50) not null,
fecha_modificacion date,
usuario_modificacion varchar(50) ,
email varchar(50) not null,
secuencia_genero int not null,
direccion_domicilio varchar(500),
telefono_movil varchar(10) not null,
telefono_fijo varchar(10) ,
es_activo  bool not null
);
ALTER TABLE tbl_personas ADD FOREIGN KEY (secuencia_tipo_identificacion) REFERENCES tbl_tipos_identificacion(secuencia_tipo_identificacion);
ALTER TABLE tbl_personas ADD FOREIGN KEY (secuencia_genero) REFERENCES tbl_generos(secuencia_genero);

/*tbl_usuarios*/
create table tbl_usuarios
(
secuencia_usuario int primary key auto_increment,
user varchar(50) not null,
password varchar(50) not null,
fecha_ingreso date not null,
usuario_ingreso varchar(50) not null,
fecha_modificacion date,
usuario_modificacion varchar(50) ,
secuencia_persona int not null,
es_activo  bool not null
);
ALTER TABLE tbl_usuarios ADD FOREIGN KEY (secuencia_persona) REFERENCES tbl_personas(secuencia_persona);

select *from tbl_usuarios;
select *from tbl_modulos;


/*tbl_generos*/
create table tbl_modulos_x_usuario
(
secuencia_usuario int not null,
secuencia_modulo int not null,
esActivo  varchar(1) not null,
esSelect varchar(1) default 'S',
);


