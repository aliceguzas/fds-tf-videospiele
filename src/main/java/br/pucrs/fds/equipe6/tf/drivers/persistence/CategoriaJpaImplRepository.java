package br.pucrs.fds.equipe6.tf.drivers.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import br.pucrs.fds.equipe6.tf.domain.entity.Categoria;
import br.pucrs.fds.equipe6.tf.domain.repository.ICategoriaRepository;

@Repository
@Primary
public class CategoriaJpaImplRepository implements ICategoriaRepository {
    private ICategoriaJpaItfRepo repository;

    @Autowired
    public CategoriaJpaImplRepository(ICategoriaJpaItfRepo repository) {
        this.repository = repository;
    }

    @Override
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    @Override
    public Categoria findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Categoria> findAll() {
        List<Categoria> categorias = new ArrayList<>();
        repository.findAll().forEach(categorias::add);
        return categorias;
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(int num) {
        return repository.existsById(num);
    }

}
