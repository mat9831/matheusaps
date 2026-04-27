package br.com.projetodoacoes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetodoacoes.model.Beneficiario;
import br.com.projetodoacoes.model.Depoimento;
import br.com.projetodoacoes.repository.BeneficiarioRepository;
import br.com.projetodoacoes.repository.DepoimentoRepository;

@Service
public class DepoimentoService {


@Autowired
private DepoimentoRepository depoimentoRepository;

@Autowired
private BeneficiarioRepository beneficiarioRepository;

// Lista todos os depoimentos (mais recentes primeiro)
public List<Depoimento> listarTodos() {
    return depoimentoRepository.findAllByOrderByDataDesc();
}

// Lista depoimentos de um beneficiário específico
public List<Depoimento> listarPorBeneficiario(Long beneficiarioId) {
    return depoimentoRepository.findByBeneficiarioId(beneficiarioId);
}

// Salva novo depoimento
public void salvar(String nomeDoador, String mensagem, Integer estrelas, Long beneficiarioId) {
    Beneficiario beneficiario = beneficiarioRepository.findById(beneficiarioId)
            .orElseThrow(() -> new RuntimeException("Beneficiário não encontrado: " + beneficiarioId));

    Depoimento dep = new Depoimento(
            nomeDoador,
            mensagem,
            (estrelas != null && estrelas >= 1 && estrelas <= 5) ? estrelas : 5,
            LocalDate.now(),
            beneficiario
    );

    depoimentoRepository.save(dep);
}


}