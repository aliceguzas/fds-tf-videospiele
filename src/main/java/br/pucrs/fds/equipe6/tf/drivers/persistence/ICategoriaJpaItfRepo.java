package br.pucrs.fds.equipe6.tf.drivers.persistence;

import br.pucrs.fds.equipe6.tf.domain.entity.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface ICategoriaJpaItfRepo extends CrudRepository<Categoria, Integer> {
}
