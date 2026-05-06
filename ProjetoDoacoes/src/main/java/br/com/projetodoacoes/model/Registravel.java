package br.com.projetodoacoes.model;

/**
 * CONCEITO DE OO — INTERFACE:
 * Define um contrato que qualquer classe "registrável" no sistema deve cumprir.
 * Tanto Doador (que registra uma doação) quanto Depoimento (que registra um
 * comentário) precisam ter um registro no sistema — por isso ambos implementam
 * esta interface.
 *
 * Isso garante que o sistema pode tratar Doador e Depoimento de forma
 * uniforme sempre que precisar apenas de "algo registrável", sem conhecer
 * os detalhes de cada classe — isso é polimorfismo via interface.
 */
public interface Registravel {

    /**
     * Executa a lógica de validação antes de persistir o registro.
     * Cada classe implementa suas próprias regras de negócio.
     * @throws IllegalStateException se os dados estiverem inválidos.
     */
    void validar();

    /**
     * Retorna o tipo do registro como texto, útil para logs e relatórios.
     * Exemplo: "DOACAO" ou "DEPOIMENTO".
     */
    String getTipoRegistro();
}