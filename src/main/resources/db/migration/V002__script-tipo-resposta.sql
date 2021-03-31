CREATE TABLE IF NOT EXISTS tipo_resposta (
  id BIGINT(20) NOT NULL auto_increment,
  descricao VARCHAR(50) NOT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB;


ALTER TABLE pergunta 
ADD COLUMN tipo_resposta_id BIGINT(20) NULL AFTER descricao,
ADD CONSTRAINT fk_pergunta_tipo_resposta
  FOREIGN KEY (pesquisa_id)
  REFERENCES pergunta (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



