package br.com.pesquisare.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pesquisare.domain.exception.PesquisaNaoEncontradaException;
import br.com.pesquisare.domain.model.Pesquisa;
import br.com.pesquisare.domain.repository.PesquisaRepository;

@Service
public class CadastroPesquisaService {

	private PesquisaRepository pesquisaRepository;
	
	@Autowired
	public CadastroPesquisaService(final PesquisaRepository pesquisaRepository) {
		this.pesquisaRepository = pesquisaRepository;
	}
	
	@Transactional
	public Pesquisa salvar(Pesquisa pesquisa) {
		return pesquisaRepository.save(pesquisa);
	}
	
	public Pesquisa buscarOuFalhar(Long pesquisaId) {
		return pesquisaRepository.findById(pesquisaId)
			.orElseThrow(() -> new PesquisaNaoEncontradaException(pesquisaId));
	}
}
