package br.pucrs.fds.equipe6.tf.domain;


import java.util.ArrayList;
import java.util.List;

public class Clientela {

    private List<Cliente> clientes;


    public Clientela() {
        clientes= new ArrayList<Cliente>();

    }
    public List<Cliente> getClientes() {
        return this.clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void addCliente(Cliente c){
        clientes.add(c);
    }

    public Cliente buscarClienteCPF(String cpf){

            return clientes.stream()
                    .filter(c -> c.getCPF().equals(cpf))
                    .findFirst()
                    .orElse(null);
    }

    public Cliente buscarClientePorCod(long cod) {
        return clientes.stream()
                .filter(c -> c.getCod() == cod)
                .findFirst()
                .orElse(null);
    }

}
