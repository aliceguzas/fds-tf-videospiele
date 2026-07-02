package br.pucrs.fds.equipe6.tf.domain.entity;

public enum Situacao {
    DISPONIVEL("disponivel"),
    CONTRATADO("contratado"),
    OBSOLETO("obsoleto"),
    REMOVIDO("removido"),
    BLOQUEADO("bloqueado"); 

    private String nome;

    private  Situacao(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public static Situacao buscaPorNome(String nome) {
        for (Situacao sit : Situacao.values()) {
            if (sit.getNome().equals(nome))
                return sit;
        }
        return null;
    }
}
