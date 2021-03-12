package br.edu.infnet.app.vagas;

import br.edu.infnet.domain.vagas.Criterio;
import br.edu.infnet.domain.vagas.Vaga;
import br.edu.infnet.infra.vagas.VagaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/vagas"})
public class VagaController {
    
    @Autowired
    private VagaRepository vagaRepository;
    
    @GetMapping
    public ResponseEntity listarVagas() {
        
        ResponseEntity retorno = ResponseEntity.notFound().build();
        Iterable<Vaga> lista = vagaRepository.findAll();
        
        if(lista.iterator().hasNext()) {
            retorno = ResponseEntity.ok().body(lista);
        }
        return retorno;
    }
    
    @GetMapping(path = "/usuario/{idUsuario}")
    public ResponseEntity listarPorIdUsuario(@PathVariable int idUsuario) {
        
        ResponseEntity retorno = ResponseEntity.notFound().build();
        List<Vaga> lista = vagaRepository.findByIdUsuario(idUsuario);
        
        if(!lista.isEmpty()) {
            retorno = ResponseEntity.ok().body(lista);
        }
        return retorno;
    }
    
    @PostMapping
    public ResponseEntity publicarVaga(@RequestBody Vaga vaga) {
        
        ResponseEntity retorno = ResponseEntity.badRequest().build();
        List<Criterio> listaCriterio = vaga.getCriterioList();
        
        if(listaCriterio != null && !listaCriterio.isEmpty()) {
            
            for (Criterio criterio : listaCriterio) {
                criterio.setVaga(vaga);
            }
            
            Vaga gravado = vagaRepository.save(vaga);
            retorno = ResponseEntity.ok().body(gravado);
        }
        return retorno;
    }
    
}
