
create table pesquisa (
	id bigint not null auto_increment,
	titulo varchar(45) not null,
	descricao varchar(255) not null,
	data_cadastro datetime not null,
	
	ativo tinyint(1) not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8;

create table pergunta (
	id bigint not null auto_increment,
	pesquisa_id bigint not null,
	descricao varchar(255) not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8;

alter table pergunta add constraint fk_pergunta_pesquisa
foreign key (pesquisa_id) references pesquisa (id);


