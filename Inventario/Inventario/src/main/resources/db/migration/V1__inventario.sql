Create table inventario (
    id integer not null auto_increment,
    nombre varchar(255),
    stock integer(255), 
    /* fecha Date, */
    proveedor varchar(255),
    insumoId integer,
    primary key (id)
);