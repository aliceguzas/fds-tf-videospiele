package br.pucrs.fds.equipe6.tf.domain;

import java.util.ArrayList;
import java.util.List;

public class Categorias {
    private List<Categoria> categorias;

    public Categorias() {
        categorias = new ArrayList<Categoria>();

    }
    public List<Categoria> getCategorias() {
        return categorias;
    }

    public Categoria getCategoriaPorNome(String nome) {
        for (Categoria c : categorias) {
            if (c.getNome().equals(nome)) {
                return c;
            }
        }
        return null;
    }

    public void setCategorias(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
    }

    public void addCategoria(Categoria c){
        categorias.add(c);
    }

    public Categoria buscaCategoriaPorNome(String nome){
        return categorias.stream()
                .filter(c -> c.getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }
}
