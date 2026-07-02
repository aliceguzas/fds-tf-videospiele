package br.pucrs.fds.equipe6.tf.domain.repository;

import br.pucrs.fds.equipe6.tf.domain.entity.Uso;
import java.util.List;

public interface IUsoRepository {
    Uso save(Uso uso);
    Uso findById(Integer id);
    List<Uso> findAll();
    List<Uso> findByContratoId(int contratoId);
    void deleteById(Integer id);
}