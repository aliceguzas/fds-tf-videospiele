package br.pucrs.fds.equipe6.tf.drivers.persistence;

import br.pucrs.fds.equipe6.tf.domain.entity.Cliente;
import br.pucrs.fds.equipe6.tf.domain.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class ClienteJpaImplRepository implements IClienteRepository {
    private IClienteJpaItfRepo repository;

    @Autowired
    public ClienteJpaImplRepository(IClienteJpaItfRepo repository) {
        this.repository = repository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public Cliente findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Optional<Cliente> findByCPF(String cpf) {
        return repository.findByCPF(cpf);
    }

    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        repository.findAll().forEach(clientes::add);
        return clientes;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
