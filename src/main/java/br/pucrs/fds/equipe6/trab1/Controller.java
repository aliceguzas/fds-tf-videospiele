package br.pucrs.fds.equipe6.trab1;

import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acmespiele")
public class Controller {
    private Clientela clientes;
    private Jogos jogos;
    private Contratos contratos;
    private Categorias categorias;
    private FormasPagamento formasPagamento;

    public Controller() {
        clientes = new Clientela();
        clientes.addCliente(new Cliente("Sofia Maldener",    "111.111.111-11", "sofia.maldener@pucrs.br",    new Date(), "senha123"));
        clientes.addCliente(new Cliente("Lucas Pereira",     "222.222.222-22", "lucas.pereira@gmail.com",    new Date(), "lucas@456"));
        clientes.addCliente(new Cliente("Ana Beatriz Lima",  "333.333.333-33", "ana.lima@hotmail.com",        new Date(), "anab789"));
        clientes.addCliente(new Cliente("Carlos Souza",      "444.444.444-44", "carlos.souza@outlook.com",   new Date(), "carl#321"));
        clientes.addCliente(new Cliente("Fernanda Oliveira", "555.555.555-55", "fernanda.oli@yahoo.com",     new Date(), "fern@000"));

        categorias = new Categorias();
        categorias.addCategoria(new Categoria(1, "Shooter",   100.00));
        categorias.addCategoria(new Categoria(2, "RPG",       200.00));
        categorias.addCategoria(new Categoria(3, "Luta",       50.50));
        categorias.addCategoria(new Categoria(4, "Simulador", 300.99));
        categorias.addCategoria(new Categoria(5, "Aventura",  150.00));

        
        Moeda real  = new Moeda(1, "Real Brasileiro", "R$",  1.00);
        Moeda dolar = new Moeda(2, "Dólar Americano", "USD", 5.80);
        Moeda euro  = new Moeda(3, "Euro",            "EUR", 6.30);

        jogos = new Jogos();
        jogos.addJogo(new Jogo(1,  "The Last of Us",      2013, 199.90, categorias.getCategoriaPorNome("Aventura"),  dolar));
        jogos.addJogo(new Jogo(2,  "Red Dead Redemption", 2018, 249.99, categorias.getCategoriaPorNome("Aventura"),  dolar));
        jogos.addJogo(new Jogo(3,  "God of War",          2022,   1.00, categorias.getCategoriaPorNome("Aventura"),  euro));
        jogos.addJogo(new Jogo(4,  "Cyberpunk 2077",      2020,   2.00, categorias.getCategoriaPorNome("Shooter"),   dolar));
        jogos.addJogo(new Jogo(5,  "Elden Ring",          2022, 349.90, categorias.getCategoriaPorNome("RPG"),       euro));
        jogos.addJogo(new Jogo(6,  "Fifa",                2016, 150.00, categorias.getCategoriaPorNome("Simulador"), real));
        jogos.addJogo(new Jogo(7,  "CS 2",                2023, 349.90, categorias.getCategoriaPorNome("Shooter"),   dolar));
        jogos.addJogo(new Jogo(8,  "EA FC 26",            2026, 349.90, categorias.getCategoriaPorNome("Simulador"), real));
        jogos.addJogo(new Jogo(9,  "The Witcher 3",       2015, 349.90, categorias.getCategoriaPorNome("RPG"),       euro));
        jogos.addJogo(new Jogo(10, "GTA VI",              2026, 349.90, categorias.getCategoriaPorNome("Shooter"),   dolar));
        jogos.addJogo(new Jogo(11, "The Sims",            2025, 349.90, categorias.getCategoriaPorNome("Simulador"), real));

        formasPagamento = new FormasPagamento();
        formasPagamento.addFormaPagamento(new FormaPagamento(11111, 11));
        formasPagamento.addFormaPagamento(new FormaPagamento(22222, 22));

        contratos = new Contratos();

        // Contrato ativo — God of War
        contratos.addContrato(new Contrato(
            1,
            new Date(125, 10, 10), // nov 2025
            3600,
            clientes.getClientes().get(0),
            jogos.getJogos().get(2),
            new Uso(1, new Date(126, 3, 1), new Date(126, 3, 1), 14, 18)
        ));

        // Contrato ativo — Elden Ring
        contratos.addContrato(new Contrato(
            2,
            new Date(126, 1, 15), // fev 2026
            2400,
            clientes.getClientes().get(1),
            jogos.getJogos().get(4),
            new Uso(2, new Date(126, 1, 15), new Date(126, 1, 20), 9, 12)
        ));

        // Contrato obsoleto — Red Dead
        contratos.addContrato(new Contrato(
            3,
            new Date(121, 2, 10), // mar 2021
            1200,
            clientes.getClientes().get(2),
            jogos.getJogos().get(1),
            new Uso(3, new Date(121, 2, 10), new Date(121, 2, 15), 19, 22)
        ));

        // Contrato removido — The Last of Us
        contratos.addContrato(new Contrato(
            4,
            new Date(118, 5, 5), // jun 2018
            600,
            clientes.getClientes().get(3),
            jogos.getJogos().get(0),
            new Uso(4, new Date(118, 5, 5), new Date(118, 5, 8), 10, 11)
        ));

        // corrigido problema 1 — atualiza a situação dos jogos já na inicialização
        jogos.atualizarSituacaoJogos(contratos);
    }

    // endpoint 1: Consultar todos os clientes
    @GetMapping("/consulta/listaclientes")
    public List<Cliente> getClientes() {
        return clientes.getClientes();
    }

    // endpoint 2: Consultar todos os jogos cadastrados
    @GetMapping("/consulta/listajogos")
    public List<Jogo> getJogos() {
        return jogos.getJogos();
    }

    // endpoint 3: Consultar todas as formas de pagamento cadastradas
    // não existia no T1 (novo)
    @GetMapping("/consulta/listaformaspagamentos")
    public List<FormaPagamento> getFormasPagamento() {
        return formasPagamento.getFormasPagamento();
    }

    // endpoint 4: Consultar todos os contratos, clientes, jogos e usos 
    @GetMapping("/consulta/listacontratos")
    public List<ContratoRespostaDTO> consultarContratos() {
        return contratos.getContratos()
                .stream()
                .map(ContratoRespostaDTO::new)
                .toList();
    }

    // endpoint 5: Consultar jogos por situação 
    @GetMapping("/consulta/jogossituacao/{situacao}")
    public List<Jogo> consultaJogoPorSituacao(@PathVariable String situacao) {
        jogos.atualizarSituacaoJogos(contratos);
        return jogos.consultaJogos(situacao);
    }

    // endpoint 6: Cadastrar novo contrato
    // agora recebe FormasPagamento e valida forma de pagamento + jogo exclusivo (tf)
    @PostMapping("/cadastro/cadcontrato")
    public boolean cadastrarContrato(@RequestBody @Valid CriaContratoDTO contratoDTO) {
        return contratos.addContratoValidado(contratoDTO, clientes, jogos, formasPagamento);
    }

    // endpoint 7: Cadastrar novo uso de contrato
    @PostMapping("/cadastro/caduso")
    public boolean cadastrarUso(@RequestBody UsoDTO usoDTO) {
        Contrato contrato = contratos.buscarContratoPorId(usoDTO.getIdContrato());
        if (contrato == null) return false;

        // verifica número de uso duplicado
        List<Uso> usos = contrato.getUsos();
        for (Uso uso : usos) {
            if (uso.getNumero() == usoDTO.getNumero()) return false;
        }

        contrato.addUso(new Uso(
            usoDTO.getNumero(),
            usoDTO.getDataInicio(),
            usoDTO.getDataFim(),
            usoDTO.getHorarioInicio(),
            usoDTO.getHorarioFim()
        ));
        return true;
    }

    // endpoint 8: Calcular valor total de um contrato
    // agora: rota nova /financeiro/... 
    @GetMapping("/financeiro/consultatotalcontrato")
    public double calculaValorContrato(@RequestParam int id) {
        double valorTotal = 0;

        Contrato c = contratos.buscarContratoPorId(id);
        if (c == null) return 0;

        double valorBase    = c.getJogo().getCategoria().getValorMinimo();
        double valorMinuto  = c.getJogo().getValorMinuto();
        double taxaParaReal = c.getJogo().getMoeda().getTaxaParaReal();

        for (Uso u : c.getUsos()) {
            long minutos = u.getDuracaoMinutos();
            double valorEmMoeda = valorBase + (valorMinuto * minutos);
            valorTotal += valorEmMoeda * taxaParaReal; // converte para R$
        }

        return valorTotal;
    }

    // endpoint 9: Calcular cobrança total de um cliente
    // agora: rota nova /financeiro/...
    @GetMapping("/financeiro/consultatotalcliente")
    public double consultaCobrancaPorCpf(@RequestParam String cpf) {
        double valorTotal = 0;
        List<Contrato> contratosCliente = contratos.getContratosPorCpf(cpf);

        for (Contrato c : contratosCliente) {
            double valorBase    = c.getJogo().getCategoria().getValorMinimo();
            double valorMinuto  = c.getJogo().getValorMinuto();
            double taxaParaReal = c.getJogo().getMoeda().getTaxaParaReal();

            for (Uso u : c.getUsos()) {
                long minutos = u.getDuracaoMinutos();
                double valorEmMoeda = valorBase + (minutos * valorMinuto);
                double valorEmReal  = valorEmMoeda * taxaParaReal;

                if (valorEmReal > 500) {
                    valorTotal += valorEmReal * 0.97; // 3% de desconto
                } else {
                    valorTotal += valorEmReal;
                }
            }
        }
        return valorTotal;
    }

    // endpoint 10: Alterar situação de um jogo
    @PutMapping("/cadastro/atualizajogo/{codigo}/situacao/{status}")
    public ResponseEntity<Jogo> alteraSituacaoJogo(@PathVariable int codigo, @PathVariable String status) {
        Jogo j = jogos.buscaJogoCod(codigo);
        if (j == null) return ResponseEntity.notFound().build();

        Situacao situacao = Situacao.buscaPorNome(status);
        if (situacao == null) return ResponseEntity.badRequest().build();

        j.setSituacao(situacao);
        j.setSituacaoManual(true); // impede sobrescrita automática pelo endpoint 5
        return ResponseEntity.ok(j);
    }

    // endpoint 11: Cancelar contrato logicamente
    @DeleteMapping("/cadastro/cancelacontrato")
    public boolean cancelarContrato(@RequestBody int id) {
        Contrato contrato = contratos.buscarContratoPorId(id);
        if (contrato == null) return false;
        contrato.cancelar();

        jogos.atualizarSituacaoJogos(contratos);
        return true;
    }
}
