package br.pucrs.fds.equipe6.tf.domain.repository;

import br.pucrs.fds.equipe6.tf.domain.entity.Categoria;
import java.util.List;

public interface ICategoriaRepository {
    Categoria save(Categoria categoria);
    Categoria findById(Integer id);
    List<Categoria> findAll();
    void deleteById(Integer id);
}