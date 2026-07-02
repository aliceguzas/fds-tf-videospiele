package br.pucrs.fds.equipe6.tf.domain;

import br.pucrs.fds.equipe6.tf.domain.entity.Situacao;

import java.util.ArrayList;
import java.util.List;

public class Jogos {

    private List<Jogo> jogos;

    public Jogos() {
        jogos = new ArrayList<Jogo>();
    }

    public List<Jogo> getJogos() {
        return this.jogos;
    }

    public void setJogos(ArrayList<Jogo> jogos) {
        this.jogos = jogos;
    }

    public void addJogo(Jogo j){
        jogos.add(j);
    }

    public Jogo buscaJogoCod(int cod){
                        return jogos
                        .stream()
                        .filter(c -> c.getCod() == cod)
                        .findFirst()
                        .orElse(null);
    }

    public void atualizarSituacaoJogos(Contratos contratos){
        for(Jogo jogo : jogos) {
            if (jogo.isSituacaoManual()) continue;

            // passa a lista interna, não o objeto Contratos
            List<Contrato> lista = contratos.getContratos()
                    .stream()
                    .filter(c -> c.getJogo().equals(jogo))
                    .toList();

            if(jogo.estaRemovido(lista)) {
                jogo.setSituacao(Situacao.REMOVIDO);
            }
            else if(jogo.estaObsoleto(lista)) {
                jogo.setSituacao(Situacao.OBSOLETO);
            }
            else if(jogo.estaContratado(lista)) {
                jogo.setSituacao(Situacao.CONTRATADO);
            }
            else {
                jogo.setSituacao(Situacao.DISPONIVEL);
            }
        }
    }

    public List<Jogo> consultaJogos(String s) {

        Situacao situacao = Situacao.buscaPorNome(s);

        return jogos.stream()
                    .filter(j -> j.getSituacao() == situacao)
                    .toList();
    }
}
