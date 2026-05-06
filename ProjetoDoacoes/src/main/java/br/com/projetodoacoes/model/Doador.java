package br.com.projetodoacoes.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade que representa uma doação realizada por um usuário.
 *
 * CONCEITO DE OO — HERANÇA:
 * Estende EntidadeBase, herdando o atributo "id", equals() e hashCode()
 * sem precisar redeclará-los.
 *
 * CONCEITO DE OO — INTERFACE (POLIMORFISMO):
 * Implementa Registravel, fornecendo sua própria versão de validar()
 * e getTipoRegistro(). O sistema pode tratar um Doador como Registravel
 * sem conhecer seus detalhes internos.
 *
 * CONCEITO DE OO — ENCAPSULAMENTO:
 * Todos os atributos são privados. Os setters protegem os dados com
 * validações: nome/email não podem ser nulos ou vazios, e o valor
 * da doação deve ser maior que zero.
 */
@Entity
@Table(name = "doador")
public class Doador extends EntidadeBase implements Registravel {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(name = "tipo_doacao")
    private String tipoDoacao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    // Relacionamento N:1 — muitos doadores para um beneficiário
    @ManyToOne
    @JoinColumn(name = "beneficiario_id")
    private Beneficiario beneficiario;

    public Doador() {}

    // ── Getters ──────────────────────────────────────────────────────────
    public String getNome()             { return nome; }
    public String getEmail()            { return email; }
    public String getTelefone()         { return telefone; }
    public String getTipoDoacao()       { return tipoDoacao; }
    public BigDecimal getValor()        { return valor; }
    public Beneficiario getBeneficiario() { return beneficiario; }

    // ── Setters com ENCAPSULAMENTO — validação dentro do setter ──────────

    /**
     * ENCAPSULAMENTO: protege o atributo nome, impedindo valores nulos ou vazios.
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do doador não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    /**
     * ENCAPSULAMENTO: protege o atributo email, impedindo valores nulos ou vazios.
     */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail do doador não pode ser vazio.");
        }
        this.email = email.trim();
    }

    /**
     * ENCAPSULAMENTO: protege o valor da doação, garantindo que seja positivo.
     */
    public void setValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da doação deve ser maior que zero.");
        }
        this.valor = valor;
    }

    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setTipoDoacao(String tipoDoacao) { this.tipoDoacao = tipoDoacao; }
    public void setBeneficiario(Beneficiario beneficiario) { this.beneficiario = beneficiario; }

    // ── Implementação de Registravel (POLIMORFISMO via interface) ─────────

    /**
     * POLIMORFISMO: implementação própria do contrato Registravel.
     * Valida os dados obrigatórios antes de persistir a doação.
     */
    @Override
    public void validar() {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalStateException("Nome é obrigatório para registrar uma doação.");
        if (email == null || email.trim().isEmpty())
            throw new IllegalStateException("E-mail é obrigatório para registrar uma doação.");
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalStateException("Valor da doação deve ser maior que zero.");
    }

    /**
     * POLIMORFISMO: retorna o tipo específico deste registro.
     */
    @Override
    public String getTipoRegistro() {
        return "DOACAO";
    }

    /**
     * POLIMORFISMO: implementação própria do método abstrato de EntidadeBase.
     * Cada classe filha descreve a si mesma de forma diferente.
     */
    @Override
    public String getResumo() {
        return String.format("Doação de %s | R$ %.2f | Beneficiário: %s",
                nome,
                valor != null ? valor : BigDecimal.ZERO,
                beneficiario != null ? beneficiario.getNome() : "não definido");
    }
}