package br.com.projetodoacoes.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Entidade Doador representa os doadores e suas doações, incluindo o relacionamento com o beneficiário
@Entity
@Table(name = "doador")
public class Doador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getTipoDoacao() { return tipoDoacao; }
    public BigDecimal getValor() { return valor; }
    public Beneficiario getBeneficiario() { return beneficiario; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setTipoDoacao(String tipoDoacao) { this.tipoDoacao = tipoDoacao; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public void setBeneficiario(Beneficiario beneficiario) { this.beneficiario = beneficiario; }
}