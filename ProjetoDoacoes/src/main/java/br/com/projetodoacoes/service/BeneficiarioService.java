package br.com.projetodoacoes.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import br.com.projetodoacoes.model.Beneficiario;
import br.com.projetodoacoes.repository.BeneficiarioRepository;

// Serviço para gerenciar beneficiários, incluindo cadastro inicial e consultas.
@Service
public class BeneficiarioService implements CommandLineRunner {

    private final BeneficiarioRepository repository;

    public BeneficiarioService(BeneficiarioRepository repository) {
        this.repository = repository;
    }

    public List<Beneficiario> listarTodos() {
        return repository.findAll();
    }

    public Beneficiario buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Beneficiario> listarPorArea(String area) {
        return repository.findByArea(area);
    }
// Método para cadastrar beneficiários iniciais ao iniciar a aplicação, garantindo que haja dados para testes e demonstração.
@Override
public void run(String... args) {
    if (repository.count() == 0) {
        repository.saveAll(List.of(

        //  saúde
            criarBeneficiario(
                "Hospital das Clínicas",
                "Saúde",
                "Apoio à saúde pública no combate às consequências da pandemia, garantindo acesso a tratamentos," +
                "medicamentos e cirurgias para populações em situação de vulnerabilidade." +
                "A iniciativa busca reduzir os impactos da desigualdade no acesso à saúde, intensificados pela COVID-19." +
                "promovendo atendimento digno e inclusivo.",
                new BigDecimal("1000000.00")
            ),
// educação
            criarBeneficiario(
                "ONG Gerando Falcões",
                "Educação",
                "A Gerando Falcões atua na transformação social por meio da educação em comunidades vulneráveis," +
                "oferecendo bolsas de estudo, capacitação e inclusão digital. Diante dos impactos da pandemia, " +
                "o projeto busca reduzir a desigualdade educacional e criar oportunidades reais para jovens em situação de pobreza." +
                "Sua doação custeia bolsas de estudo e materiais escolares.",
                new BigDecimal("500000.00")
            ),
            // meio ambiente
            criarBeneficiario(
                "Instituto Socioambiental",
                "Meio Ambiente",
                "Preservação dos recursos naturais e da biodiversidade brasileira, " +
                "em linha com os Objetivos de Desenvolvimento Sustentável da ONU Agenda 2030. " +
                "Sua doação sustenta projetos de conservação e monitoramento ambiental.",
                new BigDecimal("500000.00")
            ),
// Assistência Social
            criarBeneficiario(
                "Ação da Cidadania",
                "Assistência Social",
                "Combate à fome e à insegurança alimentar que afeta milhões de brasileiros, " +
                "com foco em trabalhadores informais, mulheres, crianças e comunidades indígenas " +
                "em situação de extrema pobreza agravada pela pandemia.",
                new BigDecimal("1000000.00")
            )
        ));

        System.out.println(">>> 4 beneficiários cadastrados com sucesso!");
    }
}
    private Beneficiario criarBeneficiario(String nome, String area, String descricao, BigDecimal meta) {
        Beneficiario b = new Beneficiario();
        b.setNome(nome);
        b.setArea(area);
        b.setDescricao(descricao);
        b.setMetaArrecadacao(meta);
        return b;
    }
}