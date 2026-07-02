package br.pucrs.fds.equipe6.tf.usecase;

import br.pucrs.fds.equipe6.tf.Jogo;
import br.pucrs.fds.equipe6.tf.Situacao;
import br.pucrs.fds.equipe6.tf.drivers.repository.JogoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarJogosPorSituacaoUseCase {

    private final JogoRepository jogoRepository;
    private final AtualizarSituacaoJogosService atualizarSituacaoJogosService;

    public ConsultarJogosPorSituacaoUseCase(JogoRepository jogoRepository,
                                             AtualizarSituacaoJogosService atualizarSituacaoJogosService) {
        this.jogoRepository = jogoRepository;
        this.atualizarSituacaoJogosService = atualizarSituacaoJogosService;
    }

    public List<Jogo> executar(String nomeSituacao) {
        atualizarSituacaoJogosService.executar();

        Situacao situacao = Situacao.buscaPorNome(nomeSituacao);

        return jogoRepository.findAll()
                .stream()
                .filter(j -> j.getSituacao() == situacao)
                .toList();
    }
}