package br.pucrs.fds.equipe6.tf.drivers.persistence;

import br.pucrs.fds.equipe6.tf.domain.entity.Uso;
import br.pucrs.fds.equipe6.tf.domain.repository.IUsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class UsoJpaImplRepository implements IUsoRepository {
    private IUsoJpaItfRepo repository;

    @Autowired
    public UsoJpaImplRepository(IUsoJpaItfRepo repository) {
        this.repository = repository;
    }

    @Override
    public Uso save(Uso uso) {
        return repository.save(uso);
    }

    @Override
    public Uso findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Uso> findAll() {
        List<Uso> usos = new ArrayList<>();
        repository.findAll().forEach(usos::add);
        return usos;
    }

    @Override
    public List<Uso> findByContratoId(int contratoId) {
        return repository.findByContrato_Id(contratoId);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

}
