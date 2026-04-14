package br.com.projetodoacoes.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.projetodoacoes.model.Doador;
import br.com.projetodoacoes.service.DoadorService;

@Controller
public class DoadorController {

    private final DoadorService service;

    public DoadorController(DoadorService service) {
        this.service = service;
    }

@PostMapping("/doar")
@ResponseBody
public ResponseEntity<String> salvarDoacao(Doador doador) {
    service.salvar(doador);
    return ResponseEntity.ok("ok");
}
}