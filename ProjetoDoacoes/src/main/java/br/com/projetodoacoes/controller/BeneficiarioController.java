package br.com.projetodoacoes.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.projetodoacoes.model.Beneficiario;
import br.com.projetodoacoes.service.BeneficiarioService;

@Controller
public class BeneficiarioController {

    private final BeneficiarioService service;

    public BeneficiarioController(BeneficiarioService service) {
        this.service = service;
    }

    @GetMapping("/beneficiarios")
    public String listarBeneficiarios(Model model) {
        List<Beneficiario> lista = service.listarTodos();
        model.addAttribute("beneficiarios", lista);
        return "beneficiarios";
    }
}