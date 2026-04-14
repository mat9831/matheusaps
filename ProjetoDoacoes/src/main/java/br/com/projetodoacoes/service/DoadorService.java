package br.com.projetodoacoes.service;

import org.springframework.stereotype.Service;

import br.com.projetodoacoes.model.Doador;
import br.com.projetodoacoes.repository.DoadorRepository;

@Service
public class DoadorService {

    private final DoadorRepository repository;

    public DoadorService(DoadorRepository repository) {
        this.repository = repository;
    }

    public Doador salvar(Doador doador) {
        return repository.save(doador);
    }
}