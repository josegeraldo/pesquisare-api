package br.com.pesquisare.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pesquisare.domain.exception.NegocioException;
import br.com.pesquisare.domain.exception.TipoRespostaNaoEncontradaException;
import br.com.pesquisare.domain.model.TipoResposta;
import br.com.pesquisare.domain.repository.TipoRespostaRepository;
import br.com.pesquisare.domain.service.CadastroTipoRespostaService;

@RestController
@RequestMapping(value = "/tipos-respostas")
public class TipoRespostaController {

	private TipoRespostaRepository tipoRespostaRepository;
	private CadastroTipoRespostaService cadastroTipoRespostaService;
	
	@Autowired
	public TipoRespostaController(final TipoRespostaRepository tipoRespostaRepository, final CadastroTipoRespostaService cadastroTipoRespostaService) {
		this.tipoRespostaRepository = tipoRespostaRepository;
		this.cadastroTipoRespostaService = cadastroTipoRespostaService;
	}
	
	@GetMapping
	public List<TipoResposta> listar() {
		return tipoRespostaRepository.findAll();
	}
	
	@GetMapping("/{tipoPesquisaId}")
	public TipoResposta buscar(@PathVariable Long tipoPesquisaId) {
		return cadastroTipoRespostaService.buscarOuFalhar(tipoPesquisaId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TipoResposta adicionar(@RequestBody @Valid TipoResposta tipoPesquisa) {
		try {
			return cadastroTipoRespostaService.salvar(tipoPesquisa);
		} catch (TipoRespostaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{tipoPesquisaId}")
	public TipoResposta atualizar(@PathVariable Long tipoPesquisaId,
			@RequestBody @Valid TipoResposta tipoPesquisa) {
		try {
			TipoResposta tipoPesquisaAtual = cadastroTipoRespostaService.buscarOuFalhar(tipoPesquisaId);
			
			BeanUtils.copyProperties(tipoPesquisa, tipoPesquisaAtual, "id");
			
			return cadastroTipoRespostaService.salvar(tipoPesquisaAtual);
		} catch (TipoRespostaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
