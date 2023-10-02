package Software;

import Hardware.Memory;
import Hardware.Opcode;
import Hardware.Word;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GerenteProcessos {
    public GM_Paginada gerenciaPaginada;
    public GM_Particionada gerenciaParticionada;
    public Queue<PCB> listaPCBs;
    public int processId = 0;
    public boolean usarParticao;

    public GerenteProcessos(Memory memory, boolean usarParticao) {
        this.usarParticao = usarParticao;
        this.listaPCBs = new LinkedList<>();

        if (usarParticao) {
            this.gerenciaParticionada = new GM_Particionada(memory.tamMem, memory.tamMem / 4);
        } else {
            this.gerenciaPaginada = new GM_Paginada(memory);
        }
    }

    public PCB create(Word[] p) {
        System.out.println("Novo processo criado");
        PCB processControlBlock;

        int tamanhoAlocar = p.length;

        for (Word w : p) {
            if (w.opc.equals(Opcode.LDD) || w.opc.equals(Opcode.STD)) {
                if (w.p > tamanhoAlocar) {
                    tamanhoAlocar = w.p;
                }
            }
        }

        if (usarParticao) {
            if (gerenciaParticionada.temEspacoParaAlocar(1)) { // Alocar uma partição
                int allocatedPartition = gerenciaParticionada.alocaParticao();
                processControlBlock = new PCB(processId, null, allocatedPartition, 0); // Não há alocação de páginas, então passe null
                ++processId;

                listaPCBs.add(processControlBlock);
                return processControlBlock;
            } else {
                System.out.println("Sem espaço na memória para criar o processo de ID: " + processId);
                return null;
            }
        } else {
            int numPalavrasPorPagina = gerenciaPaginada.tamFrame;
            int numPaginasNecessarias = (int) Math.ceil((double) tamanhoAlocar / numPalavrasPorPagina);

            if (gerenciaPaginada.temEspacoParaAlocar(numPaginasNecessarias)) {
                ArrayList<Integer> paginas = new ArrayList<>();

                // Aloca uma página de cada vez até atingir o tamanho necessário
                int wordsAllocated = 0;
                while (wordsAllocated < tamanhoAlocar) {
                    ArrayList<Integer> pages = gerenciaPaginada.alocaPagina(p);
                    if (pages != null) {
                        paginas.addAll(pages);
                        wordsAllocated += pages.size() * gerenciaPaginada.tamFrame;
                    } else {
                        System.out.println("Erro ao alocar páginas para o processo de ID: " + processId);
                        return null;
                    }
                }

                processControlBlock = new PCB(processId, paginas, 0, 0); // Alocar páginas para o processo
                ++processId;

                listaPCBs.add(processControlBlock);
                return processControlBlock;
            } else {
                System.out.println("Sem espaço na memória para criar o processo de ID: " + processId);
                return null;
            }
        }
    }

    public void finish(PCB processo) {
        System.out.println("Processo encerrado: " + processo.getId());
        if (usarParticao) {
            gerenciaParticionada.desalocaParticao(processo.getAllocatedPartition());
        } else {
            gerenciaPaginada.desalocaPagina(processo.getAllocatedPages());
        }
        listaPCBs.removeIf(pcb -> pcb.getId() == processo.getId());
    }

    public PCB getProcessByID(int id) {
        for (PCB pcb : listaPCBs) {
            if (pcb.getId() == id) {
                return pcb;
            }
        }
        return null;
    }

    public void listAllProcesses() {
        System.out.println("Processos: ");
        for (PCB pcb : listaPCBs) {
            System.out.println(pcb.toString());
        }
    }

    public int getProcessLineFromMemory(int line, int processId) {
        PCB processo = getProcessByID(processId);
        if (processo != null) {
            if (usarParticao) {
                int partitionSize = gerenciaParticionada.getTamPart();
                if (partitionSize > 0) {
                    int partitionId = processo.getAllocatedPartition();
                    return partitionId * partitionSize + line;
                }
            } else {
                ArrayList<Integer> allocatedPages = processo.getAllocatedPages();
                if (allocatedPages.size() > 0) {
                    int pageId = allocatedPages.get(line / gerenciaPaginada.tamFrame);
                    return pageId * gerenciaPaginada.tamFrame + (line % gerenciaPaginada.tamFrame);
                }
            }
        }
        // Lança uma exceção se o processo não for encontrado ou não tiver páginas/partições alocadas
        throw new IllegalArgumentException("Processo não encontrado ou sem páginas/partições alocadas: " + processId);
    }

    public boolean hasProcess(int pid) {
        for (PCB pcb : listaPCBs) {
            if (pcb.getId() == pid) return true;
        }
        return false;
    }
}
