package br.pucrs.fds.equipe6.trab1;

import br.pucrs.fds.equipe6.trab1.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * PADRÃO: Layers camada de infraestrutura.
 * Popula o banco H2 na inicialização do sistema com dados iniciais.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepo;
    private final CategoriaRepository categoriaRepo;
    private final MoedaRepository moedaRepo;
    private final JogoRepository jogoRepo;
    private final FormaPagamentoRepository formaPagamentoRepo;
    private final ContratoRepository contratoRepo;

    public DataLoader(ClienteRepository clienteRepo, CategoriaRepository categoriaRepo,
                      MoedaRepository moedaRepo, JogoRepository jogoRepo,
                      FormaPagamentoRepository formaPagamentoRepo, ContratoRepository contratoRepo) {
        this.clienteRepo = clienteRepo;
        this.categoriaRepo = categoriaRepo;
        this.moedaRepo = moedaRepo;
        this.jogoRepo = jogoRepo;
        this.formaPagamentoRepo = formaPagamentoRepo;
        this.contratoRepo = contratoRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        // --- Clientes ---
        Cliente sofia    = clienteRepo.save(new Cliente(1L, "Sofia Maldener",    "111.111.111-11", "sofia.maldener@pucrs.br",    new Date(), "senha123"));
        Cliente lucas    = clienteRepo.save(new Cliente(2L, "Lucas Pereira",     "222.222.222-22", "lucas.pereira@gmail.com",    new Date(), "lucas@456"));
        Cliente ana      = clienteRepo.save(new Cliente(3L, "Ana Beatriz Lima",  "333.333.333-33", "ana.lima@hotmail.com",        new Date(), "anab789"));
        Cliente carlos   = clienteRepo.save(new Cliente(4L, "Carlos Souza",      "444.444.444-44", "carlos.souza@outlook.com",   new Date(), "carl#321"));
        Cliente fernanda = clienteRepo.save(new Cliente(5L, "Fernanda Oliveira", "555.555.555-55", "fernanda.oli@yahoo.com",     new Date(), "fern@000"));

        // --- Categorias ---
        Categoria shooter   = categoriaRepo.save(new Categoria(1, "Shooter",   100.00));
        Categoria rpg       = categoriaRepo.save(new Categoria(2, "RPG",       200.00));
        Categoria luta      = categoriaRepo.save(new Categoria(3, "Luta",       50.50));
        Categoria simulador = categoriaRepo.save(new Categoria(4, "Simulador", 300.99));
        Categoria aventura  = categoriaRepo.save(new Categoria(5, "Aventura",  150.00));

        // --- Moedas ---
        Moeda real  = moedaRepo.save(new Moeda(1, "Real Brasileiro", "R$",  1.00));
        Moeda dolar = moedaRepo.save(new Moeda(2, "Dólar Americano", "USD", 5.80));
        Moeda euro  = moedaRepo.save(new Moeda(3, "Euro",            "EUR", 6.30));

        // --- Jogos ---
        Jogo j1  = jogoRepo.save(new Jogo(1,  "The Last of Us",      2013, 199.90, aventura,  dolar));
        Jogo j2  = jogoRepo.save(new Jogo(2,  "Red Dead Redemption", 2018, 249.99, aventura,  dolar));
        Jogo j3  = jogoRepo.save(new Jogo(3,  "God of War",          2022,   1.00, aventura,  euro));
        Jogo j4  = jogoRepo.save(new Jogo(4,  "Cyberpunk 2077",      2020,   2.00, shooter,   dolar));
        Jogo j5  = jogoRepo.save(new Jogo(5,  "Elden Ring",          2022, 349.90, rpg,       euro));
        Jogo j6  = jogoRepo.save(new Jogo(6,  "Fifa",                2016, 150.00, simulador, real));
        Jogo j7  = jogoRepo.save(new Jogo(7,  "CS 2",                2023, 349.90, shooter,   dolar));
        Jogo j8  = jogoRepo.save(new Jogo(8,  "EA FC 26",            2026, 349.90, simulador, real));
        Jogo j9  = jogoRepo.save(new Jogo(9,  "The Witcher 3",       2015, 349.90, rpg,       euro));
        Jogo j10 = jogoRepo.save(new Jogo(10, "GTA VI",              2026, 349.90, shooter,   dolar));
        Jogo j11 = jogoRepo.save(new Jogo(11, "The Sims",            2025, 349.90, simulador, real));

        // --- Formas de Pagamento ---
        FormaPagamento pix1 = formaPagamentoRepo.save(new PIX(111, 10, "111.111.111-11"));
        FormaPagamento pix2 = formaPagamentoRepo.save(new PIX(222, 20, "222.222.222-22"));
        FormaPagamento pix3 = formaPagamentoRepo.save(new PIX(333, 30, "333.333.333-33"));
        FormaPagamento pix4 = formaPagamentoRepo.save(new PIX(444, 40, "444.444.444-44"));

        // --- Contratos com Usos ---

        // Contrato ativo — God of War (Sofia)
        Contrato c1 = new Contrato(1, new Date(125, 10, 10), 3600, sofia, j3, pix1);
        Uso u1 = new Uso(1, new Date(126, 3, 1), new Date(126, 3, 1), 14, 18);
        u1.setContrato(c1);
        c1.addUso(u1);
        contratoRepo.save(c1);

        // Contrato ativo — Elden Ring (Lucas)
        Contrato c2 = new Contrato(2, new Date(126, 1, 15), 2400, lucas, j5, pix2);
        Uso u2 = new Uso(2, new Date(126, 1, 15), new Date(126, 1, 20), 9, 12);
        u2.setContrato(c2);
        c2.addUso(u2);
        contratoRepo.save(c2);

        // Contrato obsoleto — Red Dead (Ana)
        Contrato c3 = new Contrato(3, new Date(121, 2, 10), 1200, ana, j2, pix3);
        Uso u3 = new Uso(3, new Date(121, 2, 10), new Date(121, 2, 15), 19, 22);
        u3.setContrato(c3);
        c3.addUso(u3);
        contratoRepo.save(c3);

        // Contrato removido — The Last of Us (Carlos)
        Contrato c4 = new Contrato(4, new Date(118, 5, 5), 600, carlos, j1, pix4);
        Uso u4 = new Uso(4, new Date(118, 5, 5), new Date(118, 5, 8), 10, 11);
        u4.setContrato(c4);
        c4.addUso(u4);
        contratoRepo.save(c4);
    }
}
