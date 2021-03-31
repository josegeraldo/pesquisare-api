package br.com.pesquisare.domain.exception;

public class TipoRespostaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public TipoRespostaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public TipoRespostaNaoEncontradaException(Long tipoRespostaId) {
		this(String.format("Não existe um cadastro de tipo de resposta com código %d", tipoRespostaId));
	}
}
