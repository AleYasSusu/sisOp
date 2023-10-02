package Software;

import Hardware.VM;
import Hardware.Word;

public class SysCallHandling {
    private VM vm;

    public void setVM(VM _vm) {
        vm = _vm;
    }

    public void handle() {   // apenas avisa - todas interrupcoes neste momento finalizam o programa
        System.out.println("Chamada de Sistema com op  /  par:  " + vm.cpu.reg[8] + " / " + vm.cpu.reg[9]);
    }

    private void loadProgram(Word[] p, Word[] m) {
        for (int i = 0; i < p.length; i++) {
            m[i].opc = p[i].opc;
            m[i].r1 = p[i].r1;
            m[i].r2 = p[i].r2;
            m[i].p = p[i].p;
        }
    }

    private void loadProgram(Word[] p) {
        loadProgram(p, vm.m);
    }

    private void loadAndExec(Word[] p, int pid) {
        loadProgram(p);    // carga do programa na memoria
        System.out.println("---------------------------------- programa carregado na memoria");
        vm.mem.dump(0, p.length);            // dump da memoria nestas posicoes
        vm.cpu.setContext(0, vm.tamMem - 1, 0, vm.gerenteProcessos.getProcessByID(pid).getAllocatedPages());      // seta estado da cpu ]
        System.out.println("---------------------------------- inicia execucao ");
        vm.cpu.run();                                // cpu roda programa ate parar
        System.out.println("---------------------------------- memoria após execucao ");
        vm.mem.dump(0, p.length);            // dump da memoria com resultado
    }
}
