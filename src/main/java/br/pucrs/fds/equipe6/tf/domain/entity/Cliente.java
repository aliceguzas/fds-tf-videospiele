package br.pucrs.fds.equipe6.tf.domain.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Cliente {

    @Id
    private long cod; // id para o cliente
    private String nome;

    @Column(unique = true)
    private String CPF;

    private String email;

    @Temporal(TemporalType.DATE)
    private Date nascimento;
    private String password;

    public Cliente(){

    }

    public Cliente(long cod, String nome, String CPF, String email, Date nascimento, String password) {
        this.cod = cod;
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.nascimento = nascimento;
        this.password = password;
    }

    public long getCod() { return cod; }
    public void setCod(long cod) { this.cod = cod; }

    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCPF() { return this.CPF; }
    public void setCPF(String CPF) { this.CPF = CPF; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public Date getNascimento() { return this.nascimento; }
    public void setNascimento(Date nascimento) { this.nascimento = nascimento; }

    @JsonIgnore
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
}
