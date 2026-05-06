package br.com.projetodoacoes.service;

import org.springframework.stereotype.Service;

import br.com.projetodoacoes.model.Beneficiario;
import br.com.projetodoacoes.model.Doador;
import br.com.projetodoacoes.repository.BeneficiarioRepository;
import br.com.projetodoacoes.repository.DoadorRepository;

/**
 * Serviço responsável pelas regras de negócio da entidade Doador.
 *
 * CONCEITO DE OO — POLIMORFISMO via interface Registravel:
 * O método salvar() chama doador.validar() — definido na interface Registravel.
 * O service não precisa conhecer os detalhes de validação da classe Doador;
 * ele apenas sabe que qualquer Registravel pode ser validado antes de salvar.
 */
@Service
public class DoadorService {

    private final DoadorRepository repository;
    private final BeneficiarioRepository beneficiarioRepository;

    public DoadorService(DoadorRepository repository,
                        BeneficiarioRepository beneficiarioRepository) {
        this.repository = repository;
        this.beneficiarioRepository = beneficiarioRepository;
    }

    /**
     * Vincula o beneficiário ao doador, chama validar() via interface Registravel
     * e persiste a doação no banco.
     *
     * POLIMORFISMO: doador.validar() executa a implementação específica de Doador,
     * mas o service só precisa conhecer o contrato de Registravel.
     */
    public Doador salvar(Doador doador, Long beneficiarioId) {
        if (beneficiarioId != null) {
            Beneficiario beneficiario = beneficiarioRepository.findById(beneficiarioId)
                .orElseThrow(() -> new RuntimeException(
                    "Beneficiário não encontrado: " + beneficiarioId));
            doador.setBeneficiario(beneficiario);
        }

        // POLIMORFISMO: chama o método da interface sem conhecer os detalhes de Doador
        doador.validar();

        // Log usando getResumo() — método abstrato de EntidadeBase implementado por Doador
        System.out.println("[REGISTRO] " + doador.getTipoRegistro()
                + " | " + doador.getResumo());

        return repository.save(doador);
    }
}