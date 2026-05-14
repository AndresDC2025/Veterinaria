Create table tratamiento (
    id integer not null auto_increment,
    monto varchar(255),
    metodo varchar(255),
    estado boolean,
    idHistorial integer(255),
    primary key (id)
);