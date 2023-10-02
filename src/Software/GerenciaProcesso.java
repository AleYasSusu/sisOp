package Software;
import Hardware.Word;

import java.util.ArrayList;
import java.util.List;

public class GerenciaProcesso {
    private List<Processo> processos;
    private GM_Particionada gerenteParticionado;
    private GM_Paginada gerentePaginado;
    private int nextProcessId;

    public GerenciaProcesso(GM_Particionada gerenteParticionado, GM_Paginada gerentePaginado) {
        this.processos = new ArrayList<>();
        this.gerenteParticionado = gerenteParticionado;
        this.gerentePaginado = gerentePaginado;
        this.nextProcessId = 1;
    }

    public boolean criaProcesso(Word[] programa, int tamanho) {
        if (gerenteParticionado != null) {
            int particao = gerenteParticionado.alocaParticao(tamanho);
            if (particao != -1) {
                Processo novoProcesso = new Processo(nextProcessId++, programa, particao, tamanho);
                processos.add(novoProcesso);
                return true;
            }
        } else if (gerentePaginado != null) {
            int[] tabelaPaginas = new int[tamanho];
            if (gerentePaginado.alocaPagina(tamanho, tabelaPaginas)) {
                Processo novoProcesso = new Processo(nextProcessId++, programa, tabelaPaginas);
                processos.add(novoProcesso);
                return true;
            }
        }
        return false; // Não foi possível alocar memória para o processo
    }

    public void desalocaProcesso(int processoId) {
        Processo processoParaRemover = null;
        for (Processo processo : processos) {
            if (processo.getId() == processoId) {
                processoParaRemover = processo;
                break;
            }
        }

        if (processoParaRemover != null) {
            if (gerenteParticionado != null) {
                gerenteParticionado.desalocaParticao(processoParaRemover.getParticao());
            } else if (gerentePaginado != null) {
                gerentePaginado.desalocaPagina(processoParaRemover.getTabelaPaginas());
            }
            processos.remove(processoParaRemover);
        }
    }

    public List<Processo> getProcessos() {
        return processos;
    }

    public Processo getProcessByID(int processId) {
        for (Processo processo : processos) {
            if (processo.getId() == processId) {
                return processo;
            }
        }
        return null; // Retorna null se o processo com o ID especificado não for encontrado
    }

    // Método para listar os processos
    public void listaProcessos() {
        for (Processo processo : processos) {
            System.out.println("ID: " + processo.getId());
            System.out.println("Programa: " + processo.getPrograma());
            if (gerenteParticionado != null) {
                System.out.println("Partição: " + processo.getParticao());
            } else if (gerentePaginado != null) {
                System.out.println("Tabela de Páginas: " + java.util.Arrays.toString(processo.getTabelaPaginas()));
            }
            System.out.println("Tamanho: " + processo.getTamanho());
            System.out.println("--------------");
        }
    }

    // Método para executar um processo pelo ID
    public void executaProcesso(int id) {
        for (Processo processo : processos) {
            if (processo.getId() == id) {
                // Lógica para executar o processo aqui
                System.out.println("Executando o processo ID: " + id);
                return;
            }
        }
        System.out.println("Processo com ID " + id + " não encontrado.");
    }

    // Método para fazer dump de um processo pelo ID
    public void dumpProcesso(int id) {
        for (Processo processo : processos) {
            if (processo.getId() == id) {
                System.out.println("Dump do Processo ID: " + id);
                System.out.println("Programa: " + processo.getPrograma());
                if (gerenteParticionado != null) {
                    System.out.println("Partição: " + processo.getParticao());
                } else if (gerentePaginado != null) {
                    System.out.println("Tabela de Páginas: " + java.util.Arrays.toString(processo.getTabelaPaginas()));
                }
                System.out.println("Tamanho: " + processo.getTamanho());
                // Adicione mais informações de dump se necessário
                System.out.println("--------------");
                return;
            }
        }
        System.out.println("Processo com ID " + id + " não encontrado.");

    }

    public void executa(int id) {
        if (id >= 1 && id <= processos.size()) {
            // Lógica para executar o processo aqui
            System.out.println("Executando o processo ID: " + id);
        } else {
            System.out.println("Processo com ID " + id + " não encontrado.");
        }
    }

    public boolean existeProcesso(int pid) {
        for (Processo processo : getProcessos()) {
            if (processo.getId() == pid) {
                return true;
            }
        }
        return false;
    }
    public void encerrarProcesso(PCB pcb) {
        if (pcb != null) {
            int processoId = pcb.getId();

            // Procura o processo pelo PCB
            Processo processoParaEncerrar = null;
            for (Processo processo : processos) {
                if (processo.getId() == processoId) {
                    processoParaEncerrar = processo;
                    break;
                }
            }

            if (processoParaEncerrar != null) {
                // Desaloca memória associada ao processo
                if (gerenteParticionado != null) {
                    gerenteParticionado.desalocaParticao(processoParaEncerrar.getParticao());
                } else if (gerentePaginado != null) {
                    gerentePaginado.desalocaPagina(processoParaEncerrar.getTabelaPaginas());
                }

                // Remove o processo da lista de processos
                processos.remove(processoParaEncerrar);

                System.out.println("Processo ID: " + processoId + " encerrado.");
            } else {
                System.out.println("Processo não encontrado com o PCB fornecido.");
            }
        } else {
            System.out.println("PCB inválido. Não é possível encerrar o processo.");
        }
    }
}