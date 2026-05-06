package br.com.projetodoacoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetodoacoes.model.Beneficiario;

// Interface de repositório para a entidade Beneficiario, estendendo JpaRepository para fornecer operações CRUD básicas
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {

    // Busca todos os beneficiários de uma área específica
    List<Beneficiario> findByArea(String area);
}