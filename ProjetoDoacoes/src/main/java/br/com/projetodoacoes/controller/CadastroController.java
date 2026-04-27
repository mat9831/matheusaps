package br.com.projetodoacoes.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.projetodoacoes.model.Beneficiario;
import br.com.projetodoacoes.service.BeneficiarioService;

//classe responsável por exibir o formulário de cadastro, incluindo a lista de beneficiários disponíveis para seleção
@Controller
public class CadastroController {

    private final BeneficiarioService beneficiarioService;

    public CadastroController(BeneficiarioService beneficiarioService) {
        this.beneficiarioService = beneficiarioService;
    }
    // Exibe o formulário de cadastro com a lista de beneficiários disponíveis
    @GetMapping("/cadastro")
    public String cadastro(
            @RequestParam(required = false) Long beneficiarioId,
            Model model) {
        List<Beneficiario> beneficiarios = beneficiarioService.listarTodos();
        model.addAttribute("beneficiarios", beneficiarios);
        model.addAttribute("beneficiarioIdSelecionado", beneficiarioId);
        return "cadastro";
    }
}