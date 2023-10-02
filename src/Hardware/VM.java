package Hardware;

import Software.*;

// ------------------------------------ V M  - constituida de CPU e MEMORIA ------------------------------------ //
// ---------------------------------------- atributos e construcao da VM --------------------------------------- //
public class VM {
  public int tamMem;
  public Word[] m;
  public Memory mem;
  public CPU cpu;
  public GerenciaProcesso gerenteProcesso;

  // vm deve ser configurada com endereço de tratamento de interrupcoes e de chamadas de sistema
  public VM(InterruptHandling ih, SysCallHandling sysCall, GM_Particionada gerenteParticionado, GM_Paginada gerentePaginado) {
    tamMem = 1024;
    mem = new Memory(tamMem);
    m = mem.m;

    // Inicialize o gerente de processos com os argumentos fornecidos
    gerenteProcesso = new GerenciaProcesso(gerenteParticionado, gerentePaginado);

    cpu = new CPU(mem, ih, sysCall, true, 16);
  }

  public boolean criaProcesso(Word[] programa, int tamanho) {
    return gerenteProcesso.criaProcesso(programa, tamanho);
  }

  public void encerraProcesso(PCB pcb) {
    gerenteProcesso.encerrarProcesso(pcb);
  }

  public void listaProcessos() {
    gerenteProcesso.listaProcessos();
  }

  public boolean existeProcesso(int pid) {
    return gerenteProcesso.existeProcesso(pid);
  }
}
