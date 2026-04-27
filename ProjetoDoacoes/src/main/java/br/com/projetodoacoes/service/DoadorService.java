package br.com.projetodoacoes.service;

import org.springframework.stereotype.Service;

import br.com.projetodoacoes.model.Beneficiario;
import br.com.projetodoacoes.model.Doador;
import br.com.projetodoacoes.repository.BeneficiarioRepository;
import br.com.projetodoacoes.repository.DoadorRepository;

@Service
public class DoadorService {

    private final DoadorRepository repository;
    private final BeneficiarioRepository beneficiarioRepository;

    public DoadorService(DoadorRepository repository, BeneficiarioRepository beneficiarioRepository) {
        this.repository = repository;
        this.beneficiarioRepository = beneficiarioRepository;
    }

    public Doador salvar(Doador doador, Long beneficiarioId) {
        if (beneficiarioId != null) {
            Beneficiario beneficiario = beneficiarioRepository.findById(beneficiarioId)
                .orElseThrow(() -> new RuntimeException("Beneficiário não encontrado: " + beneficiarioId));
            doador.setBeneficiario(beneficiario);
        }
        return repository.save(doador);
    }
}