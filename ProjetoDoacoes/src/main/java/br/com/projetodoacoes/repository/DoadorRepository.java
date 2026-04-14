package br.com.projetodoacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetodoacoes.model.Doador;

public interface DoadorRepository extends JpaRepository<Doador, Long> {
}