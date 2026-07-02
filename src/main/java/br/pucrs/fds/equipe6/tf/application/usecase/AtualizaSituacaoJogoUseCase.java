package br.pucrs.fds.equipe6.tf.usecase;

import br.pucrs.fds.equipe6.tf.Jogo;
import br.pucrs.fds.equipe6.tf.Situacao;
import br.pucrs.fds.equipe6.tf.drivers.repository.JogoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtualizaSituacaoJogoUseCase {

    private final JogoRepository jogoRepository;

    public AtualizaSituacaoJogoUseCase(JogoRepository jogoRepository) {
        this.jogoRepository = jogoRepository;
    }

    public Optional<Jogo> executar(int codigo, String status) {
        Jogo jogo = jogoRepository.findById(codigo).orElse(null);
        if (jogo == null) return Optional.empty();

        Situacao situacao = Situacao.buscaPorNome(status);
        if (situacao == null) return Optional.empty();

        jogo.setSituacao(situacao);
        jogo.setSituacaoManual(true);

        return Optional.of(jogoRepository.save(jogo));
    }
}