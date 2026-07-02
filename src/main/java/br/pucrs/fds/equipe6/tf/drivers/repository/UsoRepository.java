package br.pucrs.fds.equipe6.trab1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucrs.fds.equipe6.trab1.Uso;

public interface UsoRepository extends JpaRepository<Uso, Integer> {

    List<Uso> findByContrato_Id(int contratoId);
}