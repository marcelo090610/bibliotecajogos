package br.com.bibliotecajogos.config;

import br.com.bibliotecajogos.entity.Categoria;
import br.com.bibliotecajogos.entity.Jogo;
import br.com.bibliotecajogos.repository.CategoriaRepository;
import br.com.bibliotecajogos.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoriaRepository.count() == 0) {
            System.out.println(">>> Populando banco de dados de desenvolvimento...");

            Categoria rpg = new Categoria(); rpg.setNome("RPG");
            Categoria acao = new Categoria(); acao.setNome("Ação");
            Categoria estrategia = new Categoria(); estrategia.setNome("Estratégia");
            Categoria aventura = new Categoria(); aventura.setNome("Aventura");

            categoriaRepository.saveAll(Arrays.asList(rpg, acao, estrategia, aventura));


            Jogo jogo1 = new Jogo();
            jogo1.setTitulo("The Witcher 3: Wild Hunt");
            jogo1.setAutor("CD Projekt Red");
            jogo1.setAnoPublicacao(2015);
            jogo1.setGenero("RPG de Ação");
            jogo1.setCategoria(rpg); // FK setada
            jogo1.setFinalizado(true);
            jogo1.setUrlCapa("https://upload.wikimedia.org/wikipedia/pt/thumb/0/06/TW3_Wild_Hunt.png/270px-TW3_Wild_Hunt.png");

            Jogo jogo2 = new Jogo();
            jogo2.setTitulo("Red Dead Redemption 2");
            jogo2.setAutor("Rockstar Games");
            jogo2.setAnoPublicacao(2018);
            jogo2.setGenero("Ação-Aventura");
            jogo2.setCategoria(acao); // FK setada
            jogo2.setFinalizado(true);
            jogo2.setUrlCapa("https://upload.wikimedia.org/wikipedia/pt/e/e7/Red_Dead_Redemption_2.png");

            Jogo jogo3 = new Jogo();
            jogo3.setTitulo("Age of Empires IV");
            jogo3.setAutor("Relic Entertainment");
            jogo3.setAnoPublicacao(2021);
            jogo3.setGenero("Estratégia em Tempo Real");
            jogo3.setCategoria(estrategia); // FK setada
            jogo3.setFinalizado(false);
            jogo3.setUrlCapa("https://upload.wikimedia.org/wikipedia/pt/4/43/Capa_do_jogo_Age_of_Empres_IV.jpg");


            jogoRepository.saveAll(Arrays.asList(jogo1, jogo2, jogo3));

            System.out.println(">>> Banco de dados populado com sucesso!");
        } else {
            System.out.println(">>> O banco de dados já está populado. Nenhuma ação foi tomada.");
        }
    }
}