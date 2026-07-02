package br.pucrs.fds.equipe6.tf.usecase;

import br.pucrs.fds.equipe6.tf.Contrato;
import br.pucrs.fds.equipe6.tf.Jogo;
import br.pucrs.fds.equipe6.tf.Situacao;
import br.pucrs.fds.equipe6.tf.drivers.repository.ContratoRepository;
import br.pucrs.fds.equipe6.tf.drivers.repository.JogoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtualizarSituacaoJogosService {

    private final JogoRepository jogoRepository;
    private final ContratoRepository contratoRepository;

    public AtualizarSituacaoJogosService(JogoRepository jogoRepository, ContratoRepository contratoRepository) {
        this.jogoRepository = jogoRepository;
        this.contratoRepository = contratoRepository;
    }

    public void executar() {
        List<Jogo> jogos = jogoRepository.findAll();

        for (Jogo jogo : jogos) {
            if (jogo.isSituacaoManual()) continue;

            List<Contrato> contratosDoJogo = contratoRepository.findByJogo(jogo);

            if (jogo.estaRemovido(contratosDoJogo)) {
                jogo.setSituacao(Situacao.REMOVIDO);
            } else if (jogo.estaObsoleto(contratosDoJogo)) {
                jogo.setSituacao(Situacao.OBSOLETO);
            } else if (jogo.estaContratado(contratosDoJogo)) {
                jogo.setSituacao(Situacao.CONTRATADO);
            } else {
                jogo.setSituacao(Situacao.DISPONIVEL);
            }
        }

        jogoRepository.saveAll(jogos);
    }
}