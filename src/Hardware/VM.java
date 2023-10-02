package Hardware;

import Software.GerenteProcessos;
import Software.InterruptHandling;
import Software.SysCallHandling;
import Software.PCB;

public class VM {
    public int tamMem;
    public Word[] m;
    public Memory mem;
    public CPU cpu;
    public GerenteProcessos gerenteProcessos; // Renomeada para a classe correta

    // vm deve ser configurada com endereço de tratamento de interrupcoes e de chamadas de sistema
    public VM(InterruptHandling ih, SysCallHandling sysCall){
        tamMem = 1024;
        mem = new Memory(tamMem);
        m = mem.m;
        gerenteProcessos = new GerenteProcessos(mem, false); // Use a classe GerenteProcessos correta

        cpu = new CPU(mem, ih, sysCall, true);  // debug true liga debug
    }

    public PCB criaProcesso(Word[] p){
        return gerenteProcessos.create(p);
    }

    public void encerraProcesso(PCB pcb){
        gerenteProcessos.finish(pcb);
    }

    public void listaProcessos(){
        gerenteProcessos.listAllProcesses();
    }

    public boolean existeProcesso(int pid){
        return gerenteProcessos.hasProcess(pid);
    }
}
