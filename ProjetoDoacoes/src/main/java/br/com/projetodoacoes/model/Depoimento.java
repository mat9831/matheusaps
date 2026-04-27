package br.com.projetodoacoes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "depoimento")
public class Depoimento {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "nome_doador", nullable = false, length = 100)
private String nomeDoador;

@Column(nullable = false, length = 400)
private String mensagem;

@Column
private Integer estrelas = 5;

@Column(nullable = false)
private LocalDate data;

// ── Relação N:1 com Beneficiario ──
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "beneficiario_id", nullable = false)
private Beneficiario beneficiario;

// ── Construtores ──
public Depoimento() {}

public Depoimento(String nomeDoador, String mensagem, Integer estrelas,
                LocalDate data, Beneficiario beneficiario) {
    this.nomeDoador   = nomeDoador;
    this.mensagem     = mensagem;
    this.estrelas     = estrelas;
    this.data         = data;
    this.beneficiario = beneficiario;
}

// ── Getters e Setters ──
public Long getId() { return id; }
public void setId(Long id) { this.id = id; }

public String getNomeDoador() { return nomeDoador; }
public void setNomeDoador(String nomeDoador) { this.nomeDoador = nomeDoador; }

public String getMensagem() { return mensagem; }
public void setMensagem(String mensagem) { this.mensagem = mensagem; }

public Integer getEstrelas() { return estrelas; }
public void setEstrelas(Integer estrelas) { this.estrelas = estrelas; }

public LocalDate getData() { return data; }
public void setData(LocalDate data) { this.data = data; }

public Beneficiario getBeneficiario() { return beneficiario; }
public void setBeneficiario(Beneficiario beneficiario) { this.beneficiario = beneficiario; }


}