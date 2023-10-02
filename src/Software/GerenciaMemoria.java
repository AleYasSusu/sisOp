package Software;

import Hardware.Memory;
import java.util.ArrayList;
import java.util.List;

public class GerenciaMemoria implements GM_Particionada, GM_Paginada {
    private List<Particao> particoesAlocadas; // Lista para rastrear partições alocadas
    private boolean[] particoesDisponiveis; // Array para controlar partições disponíveis (true) e alocadas (false)
    private int tamanhoParticao;
    private Memory memory;
    private int tamPagina;
    private int tamFrame;
    private int numeroFrames;
    private boolean[] availableFrames;
    private boolean usarPaginacao;

    public GerenciaMemoria(Memory memory, boolean usarPaginacao, int numeroParticoes) {
        this.memory = memory;
        this.usarPaginacao = usarPaginacao;
        this.tamFrame = this.tamPagina = 16;
        this.numeroFrames = this.memory.tamMem / this.tamPagina;
        this.availableFrames = initFrames(this.memory.tamMem, tamFrame);

        this.tamanhoParticao = this.memory.tamMem / numeroParticoes;
        this.particoesDisponiveis = new boolean[numeroParticoes];

        for (int i = 0; i < numeroParticoes; i++) {
            particoesDisponiveis[i] = true; // Todas as partições estão disponíveis no início
        }

        this.particoesAlocadas = new ArrayList<>();
    }

    // Inicializa o array de frames com valor TRUE
    private boolean[] initFrames(int tamMem, int pageSize) {
        boolean[] availableFrames = new boolean[(tamMem / pageSize)];
        for (int i = 0; i < availableFrames.length; i++) {
            availableFrames[i] = true;
        }
        return availableFrames;
    }

    @Override
    public boolean alocaPagina(int nroPalavras, int[] tabelaPaginas) {
        if (!usarPaginacao) {
            return false; // Paginação não está habilitada
        }

        int quantidadeDeFramesQueVaiOcupar = nroPalavras / tamFrame;
        if (nroPalavras % tamFrame != 0) {
            quantidadeDeFramesQueVaiOcupar++; // Se não for um múltiplo, precisa de mais um frame
        }

        List<Integer> paginasAlocadas = new ArrayList<>();

        for (int f = 0; f < availableFrames.length; f++) {
            if (availableFrames[f]) {
                availableFrames[f] = false;
                paginasAlocadas.add(f);

                if (paginasAlocadas.size() == quantidadeDeFramesQueVaiOcupar) {
                    // Copia os índices dos frames alocados para a tabela de páginas
                    for (int i = 0; i < paginasAlocadas.size(); i++) {
                        tabelaPaginas[i] = paginasAlocadas.get(i);
                    }
                    return true; // Alocação bem-sucedida
                }
            }
        }
        return false; // Não há frames disponíveis para alocar a quantidade desejada
    }

    @Override
        public void desalocaPagina(int[] tabelaPaginas) {
            if (!usarPaginacao) {
                return; // Paginação não está habilitada
            }

            for (int i = 0; i < tabelaPaginas.length; i++) {
                int pagina = tabelaPaginas[i];
                if (pagina >= 0 && pagina < availableFrames.length) {
                    availableFrames[pagina] = true; // Marca o frame como disponível
                }
            }
        }

    @Override
    public int alocaParticao(int tamanho) {
        for (int i = 0; i < particoesDisponiveis.length; i++) {
            if (particoesDisponiveis[i] && tamanho <= tamanhoParticao) {
                // Marca a partição como alocada
                particoesDisponiveis[i] = false;
                Particao novaParticao = new Particao(i, tamanho);
                particoesAlocadas.add(novaParticao);
                return i; // Retorna o índice da partição alocada
            }
        }
        return -1; // Não há partição disponível com tamanho suficiente
    }

    @Override
    public void desalocaParticao(int part) {
        if (part >= 0 && part < particoesAlocadas.size()) {
            Particao particao = particoesAlocadas.get(part);
            particoesDisponiveis[particao.getIndice()] = true; // Marca a partição como disponível
            particoesAlocadas.remove(part);
        }
    }

    @Override
    public boolean temEspacoParaAlocar(int tamanho) {
        if (usarPaginacao) {
            int quantidadeDeFramesQueVaiOcupar = tamanho / tamFrame;
            if (tamanho % tamFrame != 0) {
                quantidadeDeFramesQueVaiOcupar++; // Se não for um múltiplo, precisa de mais um frame
            }

            int framesDisponiveis = 0;
            for (int f = 0; f < availableFrames.length; f++) {
                if (availableFrames[f]) {
                    framesDisponiveis++;
                }
            }

            return framesDisponiveis >= quantidadeDeFramesQueVaiOcupar;
        } else {
            // Lógica para verificar espaço disponível em particionamento
            // Vamos supor que você tem uma lista de partições livres
            // Cada Particao na lista tem um tamanho e um status (livre ou alocada)
            for (Particao particao : particoesAlocadas) {
                if (particao.getTamanho() >= tamanho && particao.isLivre()) {
                    return true; // Existe uma partição livre com tamanho suficiente
                }
            }
            return false; // Não há espaço disponível em particionamento
        }
    }
    public int getTamanhoParticao() {
        return tamanhoParticao;
    }

    public int getTamPagina() {
        return tamPagina;
    }

    public int getTamFrame() {
        return tamFrame;
    }

    private class Particao {
        private int indice;
        private int tamanho;
        private boolean livre;

        public Particao(int indice, int tamanho) {
            this.indice = indice;
            this.tamanho = tamanho;
            this.livre = true; // Inicialmente, a partição é livre
        }

        public int getIndice() {
            return indice;
        }

        public int getTamanho() {
            return tamanho;
        }

        public boolean isLivre() {
            return livre;
        }
    }
}