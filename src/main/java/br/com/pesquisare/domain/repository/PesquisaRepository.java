package br.com.pesquisare.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pesquisare.domain.model.Pesquisa;

public interface PesquisaRepository extends JpaRepository<Pesquisa, Long> {

}
