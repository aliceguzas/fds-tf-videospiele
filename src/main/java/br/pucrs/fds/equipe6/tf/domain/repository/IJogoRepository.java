package br.pucrs.fds.equipe6.tf.domain.repository;

import br.pucrs.fds.equipe6.tf.domain.entity.Jogo;
import java.util.List;

public interface IJogoRepository {
    Jogo save(Jogo jogo);
    Jogo findById(Integer id);
    List<Jogo> findAll();
    void deleteById(Integer id);
}