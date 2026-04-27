package br.com.projetodoacoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projetodoacoes.model.Depoimento;

// Interface de repositório para a entidade Depoimento, estendendo JpaRepository para fornecer operações CRUD
// básicas e métodos personalizados para buscar depoimentos por beneficiário e ordenar por data
@Repository
public interface DepoimentoRepository extends JpaRepository<Depoimento, Long> {


// Busca todos os depoimentos de um beneficiário específico
List<Depoimento> findByBeneficiarioId(Long beneficiarioId);

// Busca todos ordenados do mais recente pro mais antigo
List<Depoimento> findAllByOrderByDataDesc();


}