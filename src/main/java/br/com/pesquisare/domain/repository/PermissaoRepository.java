package br.com.pesquisare.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pesquisare.domain.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
