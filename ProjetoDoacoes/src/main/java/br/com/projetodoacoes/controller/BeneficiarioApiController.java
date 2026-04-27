package br.com.projetodoacoes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetodoacoes.model.Beneficiario;
import br.com.projetodoacoes.service.BeneficiarioService;

@RestController
@RequestMapping("/api")
public class BeneficiarioApiController {

    private final BeneficiarioService service;

    public BeneficiarioApiController(BeneficiarioService service) {
        this.service = service;
    }

    @GetMapping("/beneficiarios")
    public List<Beneficiario> listar(
            @RequestParam(required = false) String area) {
        return (area != null && !area.isEmpty())
                ? service.listarPorArea(area)
                : service.listarTodos();
    }
}