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

import br.com.pesquisare.domain.model.Pesquisa;
import br.com.pesquisare.domain.repository.PesquisaRepository;
import br.com.pesquisare.domain.service.CadastroPesquisaService;

@RestController
@RequestMapping(value = "/pesquisas")
public class PesquisaController {

	private PesquisaRepository pesquisaRepository;
	private CadastroPesquisaService cadastroPesquisa;
	
	@Autowired
	public PesquisaController(final PesquisaRepository pesquisaRepository, final CadastroPesquisaService cadastroPesquisa) {
		this.pesquisaRepository = pesquisaRepository;
		this.cadastroPesquisa = cadastroPesquisa;
		
	}
	
	@GetMapping
	public List<Pesquisa> listar() {
		return pesquisaRepository.findAll();
	}
	
	@GetMapping("/{pesquisaId}")
	public Pesquisa buscar(@PathVariable Long pesquisaId) {
		return cadastroPesquisa.buscarOuFalhar(pesquisaId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pesquisa adicionar(@RequestBody @Valid Pesquisa pesquisa) {
		return cadastroPesquisa.salvar(pesquisa);
	}
	
	@PutMapping("/{pesquisaId}")
	public Pesquisa atualizar(@PathVariable Long pesquisaId,
			@RequestBody @Valid Pesquisa pesquisa) {
		
		Pesquisa pesquisaAtual = cadastroPesquisa.buscarOuFalhar(pesquisaId);
		
		BeanUtils.copyProperties(pesquisa, pesquisaAtual, "id", "dataCadastro");
		
		return cadastroPesquisa.salvar(pesquisaAtual);
	}
}
