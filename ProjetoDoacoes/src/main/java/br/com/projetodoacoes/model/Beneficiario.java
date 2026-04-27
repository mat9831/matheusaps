package br.com.projetodoacoes.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "beneficiario")
public class Beneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(name = "meta_arrecadacao", nullable = false, precision = 10, scale = 2)
    private BigDecimal metaArrecadacao;

    // EAGER carrega os doadores junto, JsonIgnore evita loop infinito no JSON
    @JsonIgnore
    @OneToMany(mappedBy = "beneficiario", fetch = FetchType.EAGER)
    private List<Doador> doacoes;

    public Beneficiario() {}

    // Soma todas as doações recebidas
    public BigDecimal getTotalRecebido() {
        if (doacoes == null || doacoes.isEmpty()) return BigDecimal.ZERO;
        return doacoes.stream()
                .map(Doador::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Percentual da meta atingido (0-100)
    public int getPercentualMeta() {
        BigDecimal total = getTotalRecebido();
        if (metaArrecadacao == null || metaArrecadacao.compareTo(BigDecimal.ZERO) == 0) return 0;
        int pct = total.multiply(BigDecimal.valueOf(100))
                .divide(metaArrecadacao, 0, RoundingMode.HALF_UP)
                .intValue();
        return Math.min(pct, 100);
    }

    // Quantidade de doações
    public int getQuantidadeDoacoes() {
        return doacoes == null ? 0 : doacoes.size();
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getArea() { return area; }
    public String getDescricao() { return descricao; }
    public BigDecimal getMetaArrecadacao() { return metaArrecadacao; }
    public List<Doador> getDoacoes() { return doacoes; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setArea(String area) { this.area = area; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setMetaArrecadacao(BigDecimal metaArrecadacao) { this.metaArrecadacao = metaArrecadacao; }
    public void setDoacoes(List<Doador> doacoes) { this.doacoes = doacoes; }
}