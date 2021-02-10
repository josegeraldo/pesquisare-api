package br.com.pesquisare.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pesquisare.domain.model.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

}
