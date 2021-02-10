package br.com.pesquisare.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pesquisare.domain.exception.PerguntaNaoEncontradaException;
import br.com.pesquisare.domain.model.Pergunta;
import br.com.pesquisare.domain.model.Pesquisa;
import br.com.pesquisare.domain.repository.PerguntaRepository;

@Service
public class CadastroPerguntaService {

	private PerguntaRepository perguntaRepository;
	private CadastroPesquisaService cadastroPesquisa;
	
	@Autowired
	public CadastroPerguntaService(final PerguntaRepository perguntaRepository, final CadastroPesquisaService cadastroPesquisa) {
		this.perguntaRepository = perguntaRepository;
		this.cadastroPesquisa = cadastroPesquisa;
	}
	
	@Transactional
	public Pergunta salvar(Pergunta pergunta) {
		Long pesquisaId = pergunta.getPesquisa().getId();

		Pesquisa pesquisa = cadastroPesquisa.buscarOuFalhar(pesquisaId);
		
		pergunta.setPesquisa(pesquisa);
		
		return perguntaRepository.save(pergunta);
	}
	
	public Pergunta buscarOuFalhar(Long perguntaId) {
		return perguntaRepository.findById(perguntaId)
			.orElseThrow(() -> new PerguntaNaoEncontradaException(perguntaId));
	}
	
}
