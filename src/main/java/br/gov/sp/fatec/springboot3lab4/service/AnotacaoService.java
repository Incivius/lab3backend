package br.gov.sp.fatec.springboot3lab4.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.fatec.springboot3lab4.entity.Anotacao;
import br.gov.sp.fatec.springboot3lab4.repository.AnotacaoRepository;

@Service
public class AnotacaoService {
    @Autowired
    private AnotacaoRepository anotacaoRepo;

    private List<Anotacao> todas() {
        return anotacaoRepo.findAll();
    }
    
    @Autowired
    private UsuarioService usuarioService;

    public Anotacao nova(Anotacao anotacao){
        if(anotacao == null ||
            anotacao.getTexto() == null ||
            anotacao.getTexto().isBlank() ||
            anotacao.getUsuario() == null ||
            anotacao.getUsuario().getId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados invalidos");
            }
            if(anotacao.getDataHora() == null) {
                anotacao.setDataHora(LocalDateTime.now());
            }
            anotacao.setUsuario(usuarioService.buscarPorId(anotacao.getUsuario().getId()));
            return anotacaoRepo.save(anotacao);
    }
}
