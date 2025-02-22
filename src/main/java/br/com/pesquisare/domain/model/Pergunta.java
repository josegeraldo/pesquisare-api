package br.com.pesquisare.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pergunta {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String descricao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Pesquisa pesquisa;
	
	@OneToOne
	@JoinColumn(name = "TIPO_RESPOSTA_ID", referencedColumnName = "ID")
	private TipoResposta tipoPesquisa;

}
