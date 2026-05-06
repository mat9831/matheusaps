package br.com.projetodoacoes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetodoacoes.model.Beneficiario;
import br.com.projetodoacoes.model.Depoimento;
import br.com.projetodoacoes.repository.BeneficiarioRepository;
import br.com.projetodoacoes.repository.DepoimentoRepository;

/**
 * Serviço responsável pelas regras de negócio da entidade Depoimento.
 *
 * CONCEITO DE OO — POLIMORFISMO via interface Registravel:
 * Assim como DoadorService chama doador.validar(), este service chama
 * dep.validar() — o mesmo contrato, com implementação diferente.
 * Isso demonstra polimorfismo: o mesmo método (validar) se comporta
 * de forma diferente dependendo do objeto (Doador ou Depoimento).
 */
@Service
public class DepoimentoService {

    @Autowired
    private DepoimentoRepository depoimentoRepository;

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    /** Lista todos os depoimentos do mais recente ao mais antigo. */
    public List<Depoimento> listarTodos() {
        return depoimentoRepository.findAllByOrderByDataDesc();
    }

    /** Lista depoimentos de um beneficiário específico. */
    public List<Depoimento> listarPorBeneficiario(Long beneficiarioId) {
        return depoimentoRepository.findByBeneficiarioId(beneficiarioId);
    }

    /**
     * Cria e salva um novo depoimento.
     *
     * POLIMORFISMO: dep.validar() executa a implementação específica de Depoimento,
     * mas segue o mesmo contrato da interface Registravel usado em DoadorService.
     */
    public void salvar(String nomeDoador, String mensagem,
                    Integer estrelas, Long beneficiarioId) {

        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiarioId)
                .orElseThrow(() -> new RuntimeException(
                    "Beneficiário não encontrado: " + beneficiarioId));

        Depoimento dep = new Depoimento(
                nomeDoador,
                mensagem,
                (estrelas != null && estrelas >= 1 && estrelas <= 5) ? estrelas : 5,
                LocalDate.now(),
                beneficiario
        );

        // POLIMORFISMO: mesmo contrato de Registravel, implementação de Depoimento
        dep.validar();

        // Log usando getResumo() — herdado de EntidadeBase, implementado por Depoimento
        System.out.println("[REGISTRO] " + dep.getTipoRegistro()
                + " | " + dep.getResumo());

        depoimentoRepository.save(dep);
    }
}