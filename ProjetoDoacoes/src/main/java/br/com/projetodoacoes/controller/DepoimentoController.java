package br.com.projetodoacoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.projetodoacoes.model.Beneficiario;
import br.com.projetodoacoes.model.Depoimento;
import br.com.projetodoacoes.repository.BeneficiarioRepository;
import br.com.projetodoacoes.service.DepoimentoService;

@Controller
public class DepoimentoController {


@Autowired
private DepoimentoService depoimentoService;

@Autowired
private BeneficiarioRepository beneficiarioRepository;

// ── GET /depoimentos  →  página pública de listagem ──
@GetMapping("/depoimentos")
public String listar(Model model) {
    List<Depoimento> depoimentos = depoimentoService.listarTodos();
    model.addAttribute("depoimentos", depoimentos);
    model.addAttribute("total", depoimentos.size());

    // Média de estrelas
    double media = depoimentos.stream()
            .mapToInt(d -> d.getEstrelas() != null ? d.getEstrelas() : 5)
            .average()
            .orElse(0.0);
    model.addAttribute("mediaEstrelas", String.format("%.1f", media));

    // Qtd de beneficiários distintos mencionados
    long totalBenef = depoimentos.stream()
            .map(d -> d.getBeneficiario().getId())
            .distinct()
            .count();
    model.addAttribute("totalBeneficiarios", totalBenef);

    return "depoimentos";
}

// ── GET /depoimento  →  formulário de envio ──
@GetMapping("/usuario_depoimento")
public String formulario(Model model) {
    List<Beneficiario> beneficiarios = beneficiarioRepository.findAll();
    model.addAttribute("beneficiarios", beneficiarios);
    model.addAttribute("sucesso", false);
    return "usuario_depoimento";
}

// ── POST /depoimento  →  salva e redireciona ──
@PostMapping("/usuario_depoimento")
public String salvar(
        @RequestParam String nomeDoador,
        @RequestParam String mensagem,
        @RequestParam(required = false) Integer estrelas,
        @RequestParam Long beneficiarioId,
        Model model
) {
    depoimentoService.salvar(nomeDoador, mensagem, estrelas, beneficiarioId);

    // Recarrega formulário com flag de sucesso
    List<Beneficiario> beneficiarios = beneficiarioRepository.findAll();
    model.addAttribute("beneficiarios", beneficiarios);
    model.addAttribute("sucesso", true);
    return "usuario_depoimento";
}


}