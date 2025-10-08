package br.com.bibliotecajogos.service;

import br.com.bibliotecajogos.entity.Jogo;
import br.com.bibliotecajogos.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    public List<Jogo> listarTodos(String sortBy) {
        // Garantindo que a ordenação seja sempre em ordem alfabética ascendente
        return jogoRepository.findAll(Sort.by(sortBy).ascending());
    }

    public Jogo salvar(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    public Jogo buscarPorId(Long id) {
        return jogoRepository.findById(id).orElse(null);
    }

    public void excluir(Long id) {
        jogoRepository.deleteById(id);
    }

    public List<Jogo> pesquisar(String termo, String tipoPesquisa) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos("titulo");
        }

        switch (tipoPesquisa.toLowerCase()) {
            case "titulo":
                return jogoRepository.findByTituloContainingIgnoreCase(termo);
            case "autor":
                return jogoRepository.findByAutorContainingIgnoreCase(termo);
            case "genero":
                return jogoRepository.findByGeneroContainingIgnoreCase(termo);
            default:
                return List.of();
        }
    }

    public Jogo toggleFinalizado(Long id) {
        Optional<Jogo> jogoOpt = jogoRepository.findById(id);
        if (jogoOpt.isPresent()) {
            Jogo jogo = jogoOpt.get();
            jogo.setFinalizado(!jogo.isFinalizado());
            return jogoRepository.save(jogo);
        }
        return null;
    }
}