Create table facturacion (
    id integer not null auto_increment,
    monto varchar(255),
    metodo varchar(255),
    estado boolean,
    idCita integer not null(255),
    primary key (id)
);