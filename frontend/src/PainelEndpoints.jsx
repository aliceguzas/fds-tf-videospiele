import { useState, useRef } from "react";

const BASE_URL_DEFAULT = "http://localhost:8080/acmespiele";

const rosa = {
  page: { fontFamily: "sans-serif", maxWidth: "1100px", margin: "0 auto", padding: "1.5rem", background: "#fff0f5", minHeight: "100vh" },
  header: { background: "linear-gradient(135deg, #f472b6, #ec4899)", borderRadius: "16px", padding: "1.5rem 2rem", marginBottom: "1.5rem", display: "flex", alignItems: "center", justifyContent: "space-between", flexWrap: "wrap", gap: "12px" },
  title: { margin: 0, color: "#fff", fontSize: "1.5rem", fontWeight: 700 },
  subtitle: { color: "#fce7f3", fontSize: "13px", margin: "4px 0 0" },
  urlInput: { height: "36px", borderRadius: "8px", border: "none", padding: "0 12px", fontSize: "13px", width: "280px", background: "#fce7f3", color: "#831843" },
  status: { fontSize: "13px", marginBottom: "1rem", minHeight: "18px", padding: "6px 12px", borderRadius: "8px", background: "#fce7f3" },
  grid: { display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(300px, 1fr))", gap: "16px" },
  card: { background: "#fff", borderRadius: "16px", border: "1.5px solid #fbcfe8", padding: "1.25rem", boxShadow: "0 2px 8px #fce7f322" },
  cardTitle: { fontWeight: 600, margin: "0 0 12px", color: "#be185d", fontSize: "15px", display: "flex", alignItems: "center", gap: "6px" },
  col: { display: "flex", flexDirection: "column", gap: "8px" },
  divider: { marginTop: "12px", paddingTop: "12px", borderTop: "1px solid #fbcfe8", display: "flex", flexDirection: "column", gap: "8px" },
  label: { fontSize: "12px", color: "#9d174d", fontWeight: 500 },
  input: { height: "36px", borderRadius: "8px", border: "1.5px solid #fbcfe8", padding: "0 10px", fontSize: "14px", outline: "none", background: "#fff0f5" },
  select: { height: "36px", borderRadius: "8px", border: "1.5px solid #fbcfe8", padding: "0 10px", fontSize: "14px", background: "#fff0f5" },
  button: { height: "36px", borderRadius: "8px", border: "1.5px solid #f9a8d4", background: "#fff0f5", cursor: "pointer", fontSize: "14px", color: "#be185d", fontWeight: 500, transition: "background 0.15s" },
  buttonPrimary: { height: "36px", borderRadius: "8px", border: "none", background: "linear-gradient(135deg, #f472b6, #ec4899)", cursor: "pointer", fontSize: "14px", fontWeight: 600, color: "#fff" },
  buttonDanger: { height: "36px", borderRadius: "8px", border: "1.5px solid #fca5a5", color: "#dc2626", background: "#fff0f5", cursor: "pointer", fontSize: "14px", fontWeight: 500 },
  buttonUpload: { height: "36px", borderRadius: "8px", border: "none", background: "#fbcfe8", cursor: "pointer", fontSize: "14px", fontWeight: 600, color: "#9d174d" },
  output: { background: "#fff0f5", borderRadius: "12px", padding: "1rem", fontSize: "12px", fontFamily: "monospace", overflowX: "auto", minHeight: "80px", maxHeight: "360px", overflowY: "auto", margin: 0, whiteSpace: "pre-wrap", wordBreak: "break-word", border: "1.5px solid #fbcfe8", color: "#831843" },
};

export default function PainelEndpoints() {
  const [baseUrl, setBaseUrl] = useState(BASE_URL_DEFAULT);
  const [status, setStatus] = useState("Nenhuma chamada feita ainda.");
  const [statusError, setStatusError] = useState(false);
  const [output, setOutput] = useState("");

  const [contrato, setContrato] = useState({ id: "", data: "", periodo: "", cpf: "", codigoJogo: "", num: "" });
  const [uso, setUso] = useState({ idContrato: "", numero: "", dataInicio: "", dataFim: "", horarioInicio: "", horarioFim: "" });
  const [cancelId, setCancelId] = useState("");
  const [jogoCodigo, setJogoCodigo] = useState("");
  const [jogoStatus, setJogoStatus] = useState("disponivel");
  const [situacaoConsulta, setSituacaoConsulta] = useState("disponivel");
  const [contratoIdFin, setContratoIdFin] = useState("");
  const [cpfFin, setCpfFin] = useState("");

  // refs para uploads
  const refClientes = useRef(); const refJogos = useRef(); const refContratos = useRef();
  const refMoedas = useRef(); const refUsos = useRef(); const refFormas = useRef(); const refCategorias = useRef();

  async function callApi(method, path, body) {
    const url = baseUrl.replace(/\/$/, "") + path;
    setStatus(`${method} ${path} ...`);
    setStatusError(false);
    try {
      const opts = { method, headers: { "Content-Type": "application/json" } };
      if (body !== undefined) opts.body = JSON.stringify(body);
      const res = await fetch(url, opts);
      const text = await res.text();
      let data; try { data = JSON.parse(text); } catch { data = text; }
      setStatus(`✅ ${method} ${path} → ${res.status}`);
      setOutput(typeof data === "string" ? data : JSON.stringify(data, null, 2));
    } catch (err) {
      setStatusError(true);
      setStatus("❌ Falha ao conectar. Backend está rodando na porta 8081?");
      setOutput(String(err));
    }
  }

  async function uploadCsv(path, fileRef) {
    const file = fileRef.current?.files[0];
    if (!file) { setStatus("❌ Selecione um arquivo CSV primeiro."); return; }
    const url = baseUrl.replace(/\/$/, "") + path;
    setStatus(`POST ${path} ...`);
    try {
      const form = new FormData();
      form.append("file", file);
      const res = await fetch(url, { method: "POST", body: form });
      const text = await res.text();
      setStatus(`✅ Upload ${path} → ${res.status}`);
      setOutput(text);
    } catch (err) {
      setStatusError(true);
      setStatus("❌ Erro no upload.");
      setOutput(String(err));
    }
  }

  return (
    <div style={rosa.page}>
      {/* Header */}
      <div style={rosa.header}>
        <div>
          <h1 style={rosa.title}>🎮 ACMEWebbasierteVideospiele</h1>
          <p style={rosa.subtitle}>Painel de testes dos endpoints</p>
        </div>
        <input style={rosa.urlInput} value={baseUrl} onChange={e => setBaseUrl(e.target.value)} />
      </div>

      {/* Status */}
      <div style={{ ...rosa.status, color: statusError ? "#dc2626" : "#9d174d" }}>{status}</div>

      <div style={rosa.grid}>

        {/* Upload CSV */}
        <div style={rosa.card}>
          <p style={rosa.cardTitle}>📂 Upload CSV</p>
          <div style={rosa.col}>
            {[
              { label: "Clientes", ref: refClientes, path: "/upload/clientes" },
              { label: "Categorias", ref: refCategorias, path: "/upload/categorias" },
              { label: "Moedas", ref: refMoedas, path: "/upload/moedas" },
              { label: "Jogos", ref: refJogos, path: "/upload/jogos" },
              { label: "Formas Pagamento", ref: refFormas, path: "/upload/formaspagamento" },
              { label: "Contratos", ref: refContratos, path: "/upload/contratos" },
              { label: "Usos", ref: refUsos, path: "/upload/usos" },
            ].map(({ label, ref, path }) => (
              <div key={path} style={{ display: "flex", gap: "8px", alignItems: "center" }}>
                <label style={{ ...rosa.label, width: "120px", flexShrink: 0 }}>{label}</label>
                <input type="file" accept=".csv" ref={ref} style={{ fontSize: "12px", flex: 1 }} />
                <button style={rosa.buttonUpload} onClick={() => uploadCsv(path, ref)}>↑</button>
              </div>
            ))}
          </div>
        </div>

        {/* Consultas */}
        <div style={rosa.card}>
          <p style={rosa.cardTitle}>🔍 Consultas</p>
          <div style={rosa.col}>
            <button style={rosa.button} onClick={() => callApi("GET", "/consulta/listaclientes")}>Listar clientes</button>
            <button style={rosa.button} onClick={() => callApi("GET", "/consulta/listajogos")}>Listar jogos</button>
            <button style={rosa.button} onClick={() => callApi("GET", "/consulta/listaformaspagamentos")}>Listar formas de pagamento</button>
            <button style={rosa.button} onClick={() => callApi("GET", "/consulta/listacontratos")}>Listar contratos</button>
          </div>
          <div style={rosa.divider}>
            <label style={rosa.label}>Situação do jogo</label>
            <select style={rosa.select} value={situacaoConsulta} onChange={e => setSituacaoConsulta(e.target.value)}>
              <option value="disponivel">Disponível</option>
              <option value="contratado">Contratado</option>
              <option value="bloqueado">Bloqueado</option>
              <option value="obsoleto">Obsoleto</option>
              <option value="removido">Removido</option>
            </select>
            <button style={{ ...rosa.button, width: "100%" }} onClick={() => callApi("GET", `/consulta/jogossituacao/${situacaoConsulta}`)}>
              Consultar jogos por situação
            </button>
          </div>
        </div>

        {/* Cadastrar contrato */}
        <div style={rosa.card}>
          <p style={rosa.cardTitle}>📝 Cadastrar contrato</p>
          <div style={rosa.col}>
            <input style={rosa.input} placeholder="id" type="number" value={contrato.id} onChange={e => setContrato({ ...contrato, id: e.target.value })} />
            <input style={rosa.input} placeholder="data (AAAA-MM-DD)" value={contrato.data} onChange={e => setContrato({ ...contrato, data: e.target.value })} />
            <input style={rosa.input} placeholder="periodo (dias)" type="number" value={contrato.periodo} onChange={e => setContrato({ ...contrato, periodo: e.target.value })} />
            <input style={rosa.input} placeholder="cpf do cliente" value={contrato.cpf} onChange={e => setContrato({ ...contrato, cpf: e.target.value })} />
            <input style={rosa.input} placeholder="codigo do jogo" type="number" value={contrato.codigoJogo} onChange={e => setContrato({ ...contrato, codigoJogo: e.target.value })} />
            <input style={rosa.input} placeholder="num da forma de pagamento" type="number" value={contrato.num} onChange={e => setContrato({ ...contrato, num: e.target.value })} />
            <button style={rosa.buttonPrimary} onClick={() => callApi("POST", "/cadastro/cadcontrato", { id: Number(contrato.id), data: contrato.data, periodo: Number(contrato.periodo), cpf: contrato.cpf, codigoJogo: Number(contrato.codigoJogo), num: Number(contrato.num) })}>
              Cadastrar contrato
            </button>
          </div>
        </div>

        {/* Cadastrar uso */}
        <div style={rosa.card}>
          <p style={rosa.cardTitle}>🕹️ Cadastrar uso</p>
          <div style={rosa.col}>
            <input style={rosa.input} placeholder="id do contrato" type="number" value={uso.idContrato} onChange={e => setUso({ ...uso, idContrato: e.target.value })} />
            <input style={rosa.input} placeholder="numero do uso" type="number" value={uso.numero} onChange={e => setUso({ ...uso, numero: e.target.value })} />
            <input style={rosa.input} placeholder="data inicio (AAAA-MM-DD)" value={uso.dataInicio} onChange={e => setUso({ ...uso, dataInicio: e.target.value })} />
            <input style={rosa.input} placeholder="data fim (AAAA-MM-DD)" value={uso.dataFim} onChange={e => setUso({ ...uso, dataFim: e.target.value })} />
            <input style={rosa.input} placeholder="horario inicio (0-23)" type="number" value={uso.horarioInicio} onChange={e => setUso({ ...uso, horarioInicio: e.target.value })} />
            <input style={rosa.input} placeholder="horario fim (0-23)" type="number" value={uso.horarioFim} onChange={e => setUso({ ...uso, horarioFim: e.target.value })} />
            <button style={rosa.buttonPrimary} onClick={() => callApi("POST", "/cadastro/caduso", { idContrato: Number(uso.idContrato), numero: Number(uso.numero), dataInicio: uso.dataInicio, dataFim: uso.dataFim, horarioInicio: Number(uso.horarioInicio), horarioFim: Number(uso.horarioFim) })}>
              Cadastrar uso
            </button>
          </div>
        </div>

        {/* Cancelar contrato */}
        <div style={rosa.card}>
          <p style={rosa.cardTitle}>🚫 Cancelar contrato</p>
          <div style={rosa.col}>
            <input style={rosa.input} placeholder="id do contrato" type="number" value={cancelId} onChange={e => setCancelId(e.target.value)} />
            <button style={rosa.buttonDanger} onClick={() => callApi("DELETE", "/cadastro/cancelacontrato", Number(cancelId))}>
              Cancelar contrato
            </button>
          </div>
        </div>

        {/* Atualizar situação */}
        <div style={rosa.card}>
          <p style={rosa.cardTitle}>⚙️ Atualizar situação de jogo</p>
          <div style={rosa.col}>
            <input style={rosa.input} placeholder="codigo do jogo" type="number" value={jogoCodigo} onChange={e => setJogoCodigo(e.target.value)} />
            <select style={rosa.select} value={jogoStatus} onChange={e => setJogoStatus(e.target.value)}>
              <option value="disponivel">Disponível</option>
              <option value="contratado">Contratado</option>
              <option value="bloqueado">Bloqueado</option>
              <option value="obsoleto">Obsoleto</option>
              <option value="removido">Removido</option>
            </select>
            <button style={rosa.buttonPrimary} onClick={() => callApi("PUT", `/cadastro/atualizajogo/${jogoCodigo}/situacao/${jogoStatus}`)}>
              Atualizar situação
            </button>
          </div>
        </div>

        {/* Financeiro */}
        <div style={rosa.card}>
          <p style={rosa.cardTitle}>💰 Financeiro</p>
          <div style={rosa.col}>
            <input style={rosa.input} placeholder="id do contrato" type="number" value={contratoIdFin} onChange={e => setContratoIdFin(e.target.value)} />
            <button style={rosa.button} onClick={() => callApi("GET", `/financeiro/consultatotalcontrato?id=${contratoIdFin}`)}>
              Total do contrato (R$)
            </button>
          </div>
          <div style={rosa.divider}>
            <input style={rosa.input} placeholder="cpf do cliente" value={cpfFin} onChange={e => setCpfFin(e.target.value)} />
            <button style={rosa.button} onClick={() => callApi("GET", `/financeiro/consultatotalcliente?cpf=${cpfFin}`)}>
              Total do cliente (R$)
            </button>
          </div>
        </div>

      </div>

      {/* Output */}
      <div style={{ marginTop: "1.5rem" }}>
        <p style={{ ...rosa.cardTitle, marginBottom: "8px" }}>📋 Resposta</p>
        <pre style={rosa.output}>{output || "Nenhuma chamada feita ainda."}</pre>
      </div>
    </div>
  );
}
