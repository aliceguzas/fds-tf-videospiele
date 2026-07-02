package br.pucrs.fds.equipe6.tf.drivers.persistence;

import br.pucrs.fds.equipe6.tf.domain.entity.Jogo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IJogoJpaItfRepo extends CrudRepository<Jogo,Integer> {
}
