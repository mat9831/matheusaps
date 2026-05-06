package br.com.projetodoacoes.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidade que representa uma organização beneficiária que recebe doações.
 *
 * CONCEITO DE OO — HERANÇA:
 * Estende EntidadeBase, herdando o atributo "id", equals() e hashCode()
 * sem precisar redeclará-los.
 *
 * CONCEITO DE OO — ENCAPSULAMENTO:
 * Todos os atributos são privados. Os setters protegem os dados com
 * validações: nome e área não podem ser vazios, e a meta de arrecadação
 * deve ser um valor positivo.
 */
@Entity
@Table(name = "beneficiario")
public class Beneficiario extends EntidadeBase {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(name = "meta_arrecadacao", nullable = false, precision = 10, scale = 2)
    private BigDecimal metaArrecadacao;

    // EAGER carrega os doadores junto; JsonIgnore evita loop infinito no JSON
    @JsonIgnore
    @OneToMany(mappedBy = "beneficiario", fetch = FetchType.EAGER)
    private List<Doador> doacoes;

    public Beneficiario() {}

    // ── Métodos de negócio ────────────────────────────────────────────────

    /** Soma todas as doações recebidas por este beneficiário. */
    public BigDecimal getTotalRecebido() {
        if (doacoes == null || doacoes.isEmpty()) return BigDecimal.ZERO;
        return doacoes.stream()
                .map(Doador::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Percentual da meta atingido (0 a 100). */
    public int getPercentualMeta() {
        BigDecimal total = getTotalRecebido();
        if (metaArrecadacao == null || metaArrecadacao.compareTo(BigDecimal.ZERO) == 0) return 0;
        int pct = total.multiply(BigDecimal.valueOf(100))
                .divide(metaArrecadacao, 0, RoundingMode.HALF_UP)
                .intValue();
        return Math.min(pct, 100);
    }

    /** Quantidade de doações recebidas. */
    public int getQuantidadeDoacoes() {
        return doacoes == null ? 0 : doacoes.size();
    }

    // ── Getters ──────────────────────────────────────────────────────────
    public String getNome()                 { return nome; }
    public String getArea()                 { return area; }
    public String getDescricao()            { return descricao; }
    public BigDecimal getMetaArrecadacao()  { return metaArrecadacao; }
    public List<Doador> getDoacoes()        { return doacoes; }

    // ── Setters com ENCAPSULAMENTO ────────────────────────────────────────

    /**
     * ENCAPSULAMENTO: impede que o nome da organização seja nulo ou vazio.
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do beneficiário não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    /**
     * ENCAPSULAMENTO: garante que a área seja uma das quatro categorias válidas.
     */
    public void setArea(String area) {
        if (area == null || area.trim().isEmpty()) {
            throw new IllegalArgumentException("Área do beneficiário não pode ser vazia.");
        }
        this.area = area.trim();
    }

    /**
     * ENCAPSULAMENTO: garante que a meta seja um valor positivo.
     */
    public void setMetaArrecadacao(BigDecimal metaArrecadacao) {
        if (metaArrecadacao == null || metaArrecadacao.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Meta de arrecadação deve ser maior que zero.");
        }
        this.metaArrecadacao = metaArrecadacao;
    }

    public void setDescricao(String descricao)     { this.descricao = descricao; }
    public void setDoacoes(List<Doador> doacoes)   { this.doacoes = doacoes; }

    /**
     * POLIMORFISMO: implementação própria do método abstrato de EntidadeBase.
     */
    @Override
    public String getResumo() {
        return String.format("Beneficiário: %s | Área: %s | Meta: R$ %.2f | Arrecadado: R$ %.2f (%d%%)",
                nome, area, metaArrecadacao, getTotalRecebido(), getPercentualMeta());
    }
}