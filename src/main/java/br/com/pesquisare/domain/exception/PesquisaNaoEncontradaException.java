package br.com.pesquisare.domain.exception;

public class PesquisaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PesquisaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public PesquisaNaoEncontradaException(Long pesquisaId) {
		this(String.format("Não existe um cadastro de pesquisa com código %d", pesquisaId));
	}
}
