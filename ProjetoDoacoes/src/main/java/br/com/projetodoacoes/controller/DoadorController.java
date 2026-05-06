package br.com.projetodoacoes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.projetodoacoes.model.Doador;
import br.com.projetodoacoes.service.DoadorService;

//classe responsável por receber os dados do formulário de doação e salvar a doação, associando-a ao beneficiário selecionado
@Controller
public class DoadorController {

    private final DoadorService service;

    public DoadorController(DoadorService service) {
        this.service = service;
    }
// Recebe os dados do formulário de doação e salva a doação, associando-a ao beneficiário selecionado
    @PostMapping("/doar")
    @ResponseBody
    public ResponseEntity<String> salvarDoacao(
            @ModelAttribute Doador doador,
            @RequestParam(name = "beneficiarioId", required = false) Long beneficiarioId) {
                
// Passa o doador e o ID do beneficiário para o service resolver a associação
        service.salvar(doador, beneficiarioId); // passa os dois para o service resolver
        return ResponseEntity.ok("ok");
    }
}