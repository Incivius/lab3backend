package br.gov.sp.fatec.springboot3lab4.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.fatec.springboot3lab4.entity.Autorizacao;
import br.gov.sp.fatec.springboot3lab4.entity.Usuario;
import br.gov.sp.fatec.springboot3lab4.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springboot3lab4.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private AutorizacaoRepository autoRepo;

    public List<Usuario> buscarTodos(){
        return usuarioRepo.findAll();
    }

    public Usuario novo(Usuario usuario){
        if (usuario == null ||
                usuario.getName() == null ||
                usuario.getName().isBlank() ||
                usuario.getSenha() == null ||
                usuario.getSenha().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(usuario.getAutorizacoes() != null &&
            !usuario.getAnotacoes().isEmpty()){
                Set<Autorizacao> autorizacoes = new HashSet<Autorizacao>();
                for(Autorizacao auto: usuario.getAutorizacoes()){
                    autorizacoes.add(buscarAutorizacaoPorId(auto.getId()));
                }
                usuario.setAutorizacoes(autorizacoes);
        }
        return usuarioRepo.save(usuario);
    }
    public Autorizacao buscarAutorizacaoPorId(Long id){
        Optional<Autorizacao> autoOp = autoRepo.findById(id);
        if(autoOp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return autoOp.get();
    }



    public Usuario buscarPorId(Long id){
        Optional<Usuario> usuarioOp = usuarioRepo.findById(id);
        if(usuarioOp.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return usuarioOp.get();
    }
}
