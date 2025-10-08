package br.com.bibliotecajogos.controller;

import br.com.bibliotecajogos.entity.Jogo;
import br.com.bibliotecajogos.service.JogoService;
import br.com.bibliotecajogos.repository.CategoriaRepository; // Necessário para o formulário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping({"/", "/jogos"})
    public String listarJogos(Model model, @RequestParam(defaultValue = "titulo") String sortBy) {
        model.addAttribute("jogos", jogoService.listarTodos(sortBy));
        return "jogos";
    }

    @GetMapping("/jogos/novo")
    public String novoJogoForm(Model model) {
        model.addAttribute("jogo", new Jogo());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "formulario-jogo";
    }

    @PostMapping("/jogos")
    public String salvarJogo(@ModelAttribute("jogo") Jogo jogo, RedirectAttributes redirectAttributes) {
        jogoService.salvar(jogo);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Jogo salvo com sucesso!");
        return "redirect:/jogos";
    }

    @GetMapping("/jogos/editar/{id}")
    public String editarJogoForm(@PathVariable Long id, Model model) {
        Jogo jogoExistente = jogoService.buscarPorId(id);
        if (jogoExistente == null) {
            return "redirect:/jogos";
        }

        model.addAttribute("jogo", jogoExistente);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "formulario-jogo";
    }

    @GetMapping("/jogos/excluir/{id}")
    public String excluirJogo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        jogoService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Jogo excluído com sucesso!");
        return "redirect:/jogos";
    }

    @GetMapping("/jogos/pesquisar")
    public String pesquisarJogos(@RequestParam(required = false) String termo,
                                 @RequestParam(required = false) String tipo,
                                 Model model) {
        if (termo == null || termo.trim().isEmpty()) {
            return "redirect:/jogos";
        }

        model.addAttribute("jogos", jogoService.pesquisar(termo, tipo));
        model.addAttribute("termoPesquisado", termo);
        model.addAttribute("tipoPesquisa", tipo);
        return "jogos";
    }
}