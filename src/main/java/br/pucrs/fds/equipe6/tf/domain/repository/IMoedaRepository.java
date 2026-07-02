package br.pucrs.fds.equipe6.tf.domain.repository;

import br.pucrs.fds.equipe6.tf.domain.entity.Moeda;
import java.util.List;

public interface IMoedaRepository {
    Moeda save(Moeda moeda);
    Moeda findById(Integer id);
    List<Moeda> findAll();
    void deleteById(Integer id);
    void delete(Moeda moeda);
}