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
import br.com.pesquisare.domain.exception.PesquisaNaoEncontradaException;
import br.com.pesquisare.domain.model.Pergunta;
import br.com.pesquisare.domain.repository.PerguntaRepository;
import br.com.pesquisare.domain.service.CadastroPerguntaService;

@RestController
@RequestMapping(value = "/perguntas")
public class PerguntaController {

	private PerguntaRepository perguntaRepository;
	private CadastroPerguntaService cadastroPergunta;
	
	@Autowired
	public PerguntaController(final PerguntaRepository perguntaRepository, final CadastroPerguntaService cadastroPergunta) {
		this.perguntaRepository = perguntaRepository;
		this.cadastroPergunta = cadastroPergunta;
		
	}
	
	@GetMapping
	public List<Pergunta> listar() {
		return perguntaRepository.findAll();
	}
	
	@GetMapping("/{perguntaId}")
	public Pergunta buscar(@PathVariable Long perguntaId) {
		return cadastroPergunta.buscarOuFalhar(perguntaId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pergunta adicionar(@RequestBody @Valid Pergunta pergunta) {
		try {
			return cadastroPergunta.salvar(pergunta);
		} catch (PesquisaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{perguntaId}")
	public Pergunta atualizar(@PathVariable Long perguntaId,
			@RequestBody @Valid Pergunta pergunta) {
		try {
			Pergunta perguntaAtual = cadastroPergunta.buscarOuFalhar(perguntaId);
			
			BeanUtils.copyProperties(pergunta, perguntaAtual, "id");
			
			return cadastroPergunta.salvar(perguntaAtual);
		} catch (PesquisaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
