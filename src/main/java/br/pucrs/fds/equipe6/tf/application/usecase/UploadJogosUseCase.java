package br.pucrs.fds.equipe6.tf.application.usecase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.pucrs.fds.equipe6.tf.domain.entity.Categoria;
import br.pucrs.fds.equipe6.tf.domain.entity.Jogo;
import br.pucrs.fds.equipe6.tf.domain.entity.Moeda;
import br.pucrs.fds.equipe6.tf.domain.repository.ICategoriaRepository;
import br.pucrs.fds.equipe6.tf.domain.repository.IJogoRepository;
import br.pucrs.fds.equipe6.tf.domain.repository.IMoedaRepository;

@Service
public class UploadJogosUseCase {

    private static final Logger log = LoggerFactory.getLogger(UploadJogosUseCase.class);

    private final IJogoRepository jogoRepository;
    private final ICategoriaRepository categoriaRepository;
    private final IMoedaRepository moedaRepository;

    public UploadJogosUseCase(IJogoRepository jogoRepository,
                               ICategoriaRepository categoriaRepository,
                               IMoedaRepository moedaRepository) {
        this.jogoRepository = jogoRepository;
        this.categoriaRepository = categoriaRepository;
        this.moedaRepository = moedaRepository;
    }

    @Transactional
    public boolean executar(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.warn("Arquivo vazio ou nulo recebido no upload de jogos");
            return false;
        }

        int linhasLidas = 0;
        int linhasSalvas = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean first = true;

            while ((line = reader.readLine()) != null) {
                // pula header
                if (first) {
                    first = false;
                    continue;
                }
                if (line.isBlank()) {
                    continue;
                }

                linhasLidas++;

                try {
                    String[] data = line.split(";");
                    if (data.length < 6) {
                        log.warn("Linha ignorada (colunas insuficientes): {}", line);
                        continue;
                    }

                    int cod = Integer.parseInt(data[0].trim());
                    String nome = data[1].trim();
                    int ano = Integer.parseInt(data[2].trim());
                    double valorMinuto = Double.parseDouble(data[3].trim().replace(",", "."));
                    int codCategoria = Integer.parseInt(data[4].trim());
                    int codMoeda = Integer.parseInt(data[5].trim());

                    if (jogoRepository.existsById(cod)) {
                        log.info("Jogo cod={} já existe, pulando", cod);
                        continue;
                    }

                    Categoria categoria = categoriaRepository.findById(codCategoria);
                    Moeda moeda = moedaRepository.findById(codMoeda);

                    if (categoria == null) {
                        log.warn("Categoria não encontrada: cod={} (linha: {})", codCategoria, line);
                        continue;
                    }
                    if (moeda == null) {
                        log.warn("Moeda não encontrada: cod={} (linha: {})", codMoeda, line);
                        continue;
                    }

                    Jogo jogo = new Jogo(cod, nome, ano, valorMinuto, categoria, moeda);
                    jogoRepository.save(jogo);
                    linhasSalvas++;

                } catch (Exception ex) {
                    log.error("Erro ao processar linha '{}': {}", line, ex.getMessage());
                }
            }

            log.info("Upload finalizado: {} linhas lidas, {} jogos salvos", linhasLidas, linhasSalvas);
            return true;

        } catch (IOException e) {
            log.error("Erro de I/O ao ler arquivo de upload", e);
            return false;
        }
    }
}