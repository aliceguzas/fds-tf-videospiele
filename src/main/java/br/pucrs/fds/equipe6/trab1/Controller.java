package br.pucrs.fds.equipe6.trab1;

import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.pucrs.fds.equipe6.trab1.repository.*;
import br.pucrs.fds.equipe6.trab1.usecase.*;

/**
 * PADRÃO: MVC Controller: recebe requisições HTTP e delega para UseCases.
 * PADRÃO: Layers: camada de apresentação.
 * PADRÃO: Clean Architecture: Controller depende de UseCases, nunca acessa
 *   repositórios diretamente exceto quando não há UseCase dedicado.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/acmespiele")
public class Controller {

    // REPOSITORIES
    private final ClienteRepository clienteRepository;
    private final JogoRepository jogoRepository;
    private final ContratoRepository contratoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;

    // USECASES
    private final CalculaValorContratoUseCase calculaValorContratoUseCase;
    private final ConsultarJogosPorSituacaoUseCase consultarJogosPorSituacaoUseCase;
    private final AtualizaSituacaoJogoUseCase atualizarSituacaoJogoUseCase;
    private final ConsultaCobrancaClienteUseCase consultaCobrancaClienteUseCase;
    private final AtualizarSituacaoJogosService atualizarSituacaoJogosService;

    // UPLOADS
    private final UploadClientesService uploadClientesService;
    private final UploadJogosService uploadJogosService;
    private final UploadContratosService uploadContratosService;
    private final UploadMoedasService uploadMoedasService;
    private final UploadUsosService uploadUsosService;
    private final UploadFormasPagamentoService uploadFormasPagamentoService;
    private final UploadCategoriasService uploadCategoriasService;

    public Controller(
        ClienteRepository clienteRepository,
        JogoRepository jogoRepository,
        ContratoRepository contratoRepository,
        CategoriaRepository categoriaRepository,
        FormaPagamentoRepository formaPagamentoRepository,
        ConsultarJogosPorSituacaoUseCase consultarJogosPorSituacaoUseCase,
        AtualizaSituacaoJogoUseCase atualizarSituacaoJogoUseCase,
        CalculaValorContratoUseCase calculaValorContratoUseCase,
        ConsultaCobrancaClienteUseCase consultaCobrancaClienteUseCase,
        AtualizarSituacaoJogosService atualizarSituacaoJogosService,
        UploadClientesService uploadClientesService,
        UploadJogosService uploadJogosService,
        UploadContratosService uploadContratosService,
        UploadMoedasService uploadMoedasService,
        UploadUsosService uploadUsosService,
        UploadFormasPagamentoService uploadFormasPagamentoService,
        UploadCategoriasService uploadCategoriasService
    ) {
        this.clienteRepository = clienteRepository;
        this.jogoRepository = jogoRepository;
        this.contratoRepository = contratoRepository;
        this.categoriaRepository = categoriaRepository;
        this.formaPagamentoRepository = formaPagamentoRepository;
        this.consultarJogosPorSituacaoUseCase = consultarJogosPorSituacaoUseCase;
        this.atualizarSituacaoJogoUseCase = atualizarSituacaoJogoUseCase;
        this.calculaValorContratoUseCase = calculaValorContratoUseCase;
        this.consultaCobrancaClienteUseCase = consultaCobrancaClienteUseCase;
        this.atualizarSituacaoJogosService = atualizarSituacaoJogosService;
        this.uploadClientesService = uploadClientesService;
        this.uploadJogosService = uploadJogosService;
        this.uploadContratosService = uploadContratosService;
        this.uploadMoedasService = uploadMoedasService;
        this.uploadUsosService = uploadUsosService;
        this.uploadFormasPagamentoService = uploadFormasPagamentoService;
        this.uploadCategoriasService = uploadCategoriasService;
    }

    // endpoint 0: Upload de arquivos CSV
    @PostMapping("/upload/clientes")
    public ResponseEntity<Boolean> uploadClientes(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(uploadClientesService.executar(file));
    }

    @PostMapping("/upload/jogos")
    public ResponseEntity<Boolean> uploadJogos(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(uploadJogosService.executar(file));
    }

    @PostMapping("/upload/contratos")
    public ResponseEntity<Boolean> uploadContratos(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(uploadContratosService.executar(file));
    }

    @PostMapping("/upload/moedas")
    public ResponseEntity<Boolean> uploadMoedas(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(uploadMoedasService.executar(file));
    }

    @PostMapping("/upload/usos")
    public ResponseEntity<Boolean> uploadUsos(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(uploadUsosService.executar(file));
    }

    @PostMapping("/upload/formaspagamento")
    public ResponseEntity<Boolean> uploadFormasPagamento(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(uploadFormasPagamentoService.executar(file));
    }

    @PostMapping("/upload/categorias")
    public ResponseEntity<Boolean> uploadCategorias(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(uploadCategoriasService.executar(file));
    }

    // endpoint 1: Consultar todos os clientes
    @GetMapping("/consulta/listaclientes")
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    // endpoint 2: Consultar todos os jogos cadastrados
    @GetMapping("/consulta/listajogos")
    public List<Jogo> getJogos() {
        return jogoRepository.findAll();
    }

    // endpoint 3: Consultar todas as formas de pagamento cadastradas
    @GetMapping("/consulta/listaformaspagamentos")
    public List<FormaPagamento> getFormasPagamento() {
        return formaPagamentoRepository.findAll();
    }

    // endpoint 4: Consultar todos os contratos, clientes, jogos e usos
    // CORRIGIDO 
    @GetMapping("/consulta/listacontratos")
    public List<ContratoRespostaDTO> consultarContratos() {
        return contratoRepository.findAll()
                .stream()
                .map(ContratoRespostaDTO::new)
                .toList();
    }

    // endpoint 5: Consultar jogos por situação
    @GetMapping("/consulta/jogossituacao/{situacao}")
    public List<Jogo> consultaJogoPorSituacao(@PathVariable String situacao) {
        return consultarJogosPorSituacaoUseCase.executar(situacao);
    }

    // endpoint 6: Cadastrar novo contrato
    // CORRIGIDO — estava retornando contratos por CPF em vez de cadastrar
    @PostMapping("/cadastro/cadcontrato")
    public boolean cadastrarContrato(@RequestBody @Valid CriaContratoDTO dto) {
        if (contratoRepository.existsById(dto.getId())) return false;

        Cliente cliente = clienteRepository.findByCPF(dto.getCpf()).orElse(null);
        Jogo jogo = jogoRepository.findById(dto.getCodigoJogo()).orElse(null);
        FormaPagamento forma = formaPagamentoRepository.findById(dto.getNum()).orElse(null);

        if (cliente == null || jogo == null || forma == null) return false;

        // regra TF: jogo exclusivo — só 1 contrato ativo por vez
        List<Contrato> contratosDoJogo = contratoRepository.findByJogo(jogo);
        boolean jogoAtivo = contratosDoJogo.stream()
                .anyMatch(c -> !c.isCancelado() && c.getDataFim().after(new Date()));
        if (jogoAtivo) return false;

        contratoRepository.save(new Contrato(
            dto.getId(), dto.getData(), dto.getPeriodo(), cliente, jogo, forma));
        return true;
    }

    // endpoint 7: Cadastrar novo uso de contrato
    // CORRIGIDO 
    @PostMapping("/cadastro/caduso")
    public boolean cadastrarUso(@RequestBody UsoDTO dto) {
        Contrato contrato = contratoRepository.findById(dto.getIdContrato()).orElse(null);
        if (contrato == null) return false;

        boolean duplicado = contrato.getUsos().stream()
                .anyMatch(u -> u.getNumero() == dto.getNumero());
        if (duplicado) return false;

        Uso novoUso = new Uso(
            dto.getNumero(), dto.getDataInicio(), dto.getDataFim(),
            dto.getHorarioInicio(), dto.getHorarioFim()
        );
        novoUso.setContrato(contrato);
        contrato.addUso(novoUso);
        contratoRepository.save(contrato);
        return true;
    }

    // endpoint 8: Calcular valor total de um contrato
    @GetMapping("/financeiro/consultatotalcontrato")
    public double calculaValorContrato(@RequestParam int id) {
        return calculaValorContratoUseCase.executar(id);
    }

    // endpoint 9: Calcular cobrança total de um cliente
    @GetMapping("/financeiro/consultatotalcliente")
    public double consultaCobrancaPorCpf(@RequestParam String cpf) {
        return consultaCobrancaClienteUseCase.executar(cpf);
    }

    // endpoint 10: Alterar situação de um jogo
    // CORRIGIDO — erro de sintaxe: codigo.status -> (codigo, status)
    @PutMapping("/cadastro/atualizajogo/{codigo}/situacao/{status}")
    public ResponseEntity<Jogo> alteraSituacaoJogo(@PathVariable int codigo, @PathVariable String status) {
        return atualizarSituacaoJogoUseCase.executar(codigo, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // endpoint 11: Cancelar contrato logicamente
    // CORRIGIDO 
    @DeleteMapping("/cadastro/cancelacontrato")
    public boolean cancelarContrato(@RequestBody int id) {
        Contrato contrato = contratoRepository.findById(id).orElse(null);
        if (contrato == null) return false;
        contrato.cancelar();
        contratoRepository.save(contrato);
        atualizarSituacaoJogosService.executar();
        return true;
    }
}
