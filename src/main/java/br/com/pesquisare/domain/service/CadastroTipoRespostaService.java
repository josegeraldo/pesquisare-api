package br.com.pesquisare.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pesquisare.domain.exception.TipoRespostaNaoEncontradaException;
import br.com.pesquisare.domain.model.TipoResposta;
import br.com.pesquisare.domain.repository.TipoRespostaRepository;

@Service
public class CadastroTipoRespostaService {
	
	private TipoRespostaRepository tipoRespostaRepository;

	@Autowired
	public CadastroTipoRespostaService(final TipoRespostaRepository tipoRespostaRepository) {
		this.tipoRespostaRepository = tipoRespostaRepository;
	}
	
	@Transactional
	public TipoResposta salvar(TipoResposta tipoResposta) {
		return tipoRespostaRepository.save(tipoResposta);
	}
	
	public TipoResposta buscarOuFalhar(Long tipoPesquisaId) {
		return tipoRespostaRepository.findById(tipoPesquisaId)
			.orElseThrow(() -> new TipoRespostaNaoEncontradaException(tipoPesquisaId));
	}
}
