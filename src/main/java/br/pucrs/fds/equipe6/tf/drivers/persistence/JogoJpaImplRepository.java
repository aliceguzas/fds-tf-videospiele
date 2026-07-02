package br.pucrs.fds.equipe6.tf.drivers.persistence;

import br.pucrs.fds.equipe6.tf.domain.entity.Jogo;
import br.pucrs.fds.equipe6.tf.domain.repository.IJogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class JogoJpaImplRepository implements IJogoRepository {
    private IJogoJpaItfRepo repository;

    @Autowired
    public JogoJpaImplRepository(IJogoJpaItfRepo repository) {
        this.repository = repository;
    }

    @Override
    public Jogo save(Jogo jogo) {
        return repository.save(jogo);
    }

    @Override
    public Jogo findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Jogo> findAll() {
        List<Jogo> jogos = new ArrayList<>();
        repository.findAll().forEach(jogos::add);
        return jogos;
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
