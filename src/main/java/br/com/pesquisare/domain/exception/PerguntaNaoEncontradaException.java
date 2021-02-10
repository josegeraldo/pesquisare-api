package br.com.pesquisare.domain.exception;

public class PerguntaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PerguntaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public PerguntaNaoEncontradaException(Long perguntaId) {
		this(String.format("Não existe um cadastro de pergunta com código %d", perguntaId));
	}
}
