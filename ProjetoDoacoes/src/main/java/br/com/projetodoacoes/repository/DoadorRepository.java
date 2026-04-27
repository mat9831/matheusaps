package br.com.projetodoacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetodoacoes.model.Doador;

// Interface de repositório para a entidade Doador, estendendo JpaRepository para fornecer operações CRUD básicas
public interface DoadorRepository extends JpaRepository<Doador, Long> {
}