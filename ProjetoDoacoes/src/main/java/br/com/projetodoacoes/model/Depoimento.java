package br.com.projetodoacoes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade que representa um depoimento deixado por um doador.
 *
 * CONCEITO DE OO — HERANÇA:
 * Estende EntidadeBase, herdando o atributo "id", equals() e hashCode().
 *
 * CONCEITO DE OO — INTERFACE (POLIMORFISMO):
 * Implementa Registravel, assim como Doador. O sistema pode tratar
 * Depoimento e Doador de forma uniforme como objetos Registravel,
 * chamando validar() e getTipoRegistro() sem saber qual tipo é cada um.
 *
 * CONCEITO DE OO — ENCAPSULAMENTO:
 * O setter de estrelas garante que o valor esteja sempre entre 1 e 5.
 * O setter de mensagem impede textos vazios.
 */
@Entity
@Table(name = "depoimento")
public class Depoimento extends EntidadeBase implements Registravel {

    @Column(name = "nome_doador", nullable = false, length = 100)
    private String nomeDoador;

    @Column(nullable = false, length = 400)
    private String mensagem;

    @Column
    private Integer estrelas = 5;

    @Column(nullable = false)
    private LocalDate data;

    // Relação N:1 com Beneficiario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiario_id", nullable = false)
    private Beneficiario beneficiario;

    // ── Construtores ──────────────────────────────────────────────────────
    public Depoimento() {}

    public Depoimento(String nomeDoador, String mensagem, Integer estrelas,
                      LocalDate data, Beneficiario beneficiario) {
        this.nomeDoador   = nomeDoador;
        this.mensagem     = mensagem;
        this.estrelas     = estrelas;
        this.data         = data;
        this.beneficiario = beneficiario;
    }

    // ── Getters ──────────────────────────────────────────────────────────
    public String getNomeDoador()         { return nomeDoador; }
    public String getMensagem()           { return mensagem; }
    public Integer getEstrelas()          { return estrelas; }
    public LocalDate getData()            { return data; }
    public Beneficiario getBeneficiario() { return beneficiario; }

    // ── Setters com ENCAPSULAMENTO ────────────────────────────────────────

    public void setNomeDoador(String nomeDoador) { this.nomeDoador = nomeDoador; }

    /**
     * ENCAPSULAMENTO: impede que a mensagem seja nula ou vazia.
     */
    public void setMensagem(String mensagem) {
        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("A mensagem do depoimento não pode ser vazia.");
        }
        this.mensagem = mensagem.trim();
    }

    /**
     * ENCAPSULAMENTO: garante que as estrelas estejam sempre entre 1 e 5.
     * Se o valor for inválido, aplica o padrão 5.
     */
    public void setEstrelas(Integer estrelas) {
        this.estrelas = (estrelas != null && estrelas >= 1 && estrelas <= 5) ? estrelas : 5;
    }

    public void setData(LocalDate data)               { this.data = data; }
    public void setBeneficiario(Beneficiario b)       { this.beneficiario = b; }

    // ── Implementação de Registravel (POLIMORFISMO via interface) ─────────

    /**
     * POLIMORFISMO: implementação própria do contrato Registravel.
     * Valida os dados obrigatórios antes de persistir o depoimento.
     */
    @Override
    public void validar() {
        if (nomeDoador == null || nomeDoador.trim().isEmpty())
            throw new IllegalStateException("Nome é obrigatório para registrar um depoimento.");
        if (mensagem == null || mensagem.trim().isEmpty())
            throw new IllegalStateException("Mensagem é obrigatória para registrar um depoimento.");
        if (beneficiario == null)
            throw new IllegalStateException("Beneficiário é obrigatório para registrar um depoimento.");
    }

    /**
     * POLIMORFISMO: retorna o tipo específico deste registro.
     */
    @Override
    public String getTipoRegistro() {
        return "DEPOIMENTO";
    }

    /**
     * POLIMORFISMO: implementação própria do método abstrato de EntidadeBase.
     */
    @Override
    public String getResumo() {
        return String.format("Depoimento de %s | %d estrelas | Beneficiário: %s",
                nomeDoador,
                estrelas != null ? estrelas : 5,
                beneficiario != null ? beneficiario.getNome() : "não definido");
    }
}