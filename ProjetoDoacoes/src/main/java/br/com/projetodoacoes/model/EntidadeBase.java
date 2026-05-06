package br.com.projetodoacoes.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Classe abstrata base para todas as entidades do sistema.
 *
 * CONCEITO DE OO — HERANÇA + CLASSE ABSTRATA:
 * Centraliza o atributo "id" e o método "equals()" em um único lugar,
 * evitando repetição de código nas classes filhas (Doador, Beneficiario, Depoimento).
 * A anotação @MappedSuperclass instrui o JPA a herdar os mapeamentos
 * desta classe nas subclasses, sem criar uma tabela própria para ela.
 */
@MappedSuperclass
public abstract class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    /**
     * CONCEITO DE OO — POLIMORFISMO (método abstrato):
     * Obriga todas as subclasses a implementar getResumo(),
     * retornando uma descrição textual própria de cada entidade.
     * Cada classe fornece sua própria implementação — isso é polimorfismo.
     */
    public abstract String getResumo();

    /**
     * CONCEITO DE OO — ENCAPSULAMENTO:
     * equals() centralizado: duas entidades são iguais se têm o mesmo ID.
     * Regra de negócio protegida dentro da própria classe.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EntidadeBase other = (EntidadeBase) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}