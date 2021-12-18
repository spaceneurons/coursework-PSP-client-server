create table if not exists "user"
(
	id serial not null
		constraint users_pk
			primary key,
	login varchar,
	password varchar,
	role varchar
);

create unique index if not exists users_login_uindex
	on "user" (login);

create table if not exists client
(
	id serial not null
		constraint clients_pk
			primary key,
	name varchar not null,
	surname varchar,
	telephone varchar,
	email varchar,
	user_id integer
		constraint clients_users_id_fk
			references "user"
);

create unique index if not exists clients_id_uindex
	on client (id);

create table if not exists employee
(
	id serial not null
		constraint employee_pk
			primary key,
	name varchar,
	surname varchar
);

create unique index if not exists employee_id_uindex
	on employee (id);

create table if not exists service
(
	id serial not null
		constraint services_pk
			primary key,
	name varchar,
	price double precision,
	time integer
);

create unique index if not exists services_id_uindex
	on service (id);

create table if not exists employeeservice
(
	employee_id integer not null
		constraint employeeservice_employee_id_fk
			references employee
				on update cascade on delete cascade,
	service_id integer not null
		constraint employeeservice_services_id_fk
			references service
				on update cascade on delete cascade,
	id serial not null
		constraint employeeservice_pk
			primary key
);

create unique index if not exists employeeservice_id_uindex
	on employeeservice (id);

create table if not exists appointment
(
	id serial not null
		constraint appointments_pk
			primary key,
	client_id integer
		constraint appointments_clients_id_fk
			references client
				on update cascade on delete cascade,
	endtime time,
	dat date,
	starttime time,
	empl_serv_id integer
		constraint appointments_employeeservice_id_fk
			references employeeservice
				on update cascade on delete cascade
);

create unique index if not exists appointments_id_uindex
	on appointment (id);
