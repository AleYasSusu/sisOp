package Sistema;

import Hardware.VM;
import Hardware.Word;
import Programas.Programas;
import Software.InterruptHandling;
import Software.PCB;
import Software.Processo;
import Software.SysCallHandling;

// -------------------  S I S T E M A -------------------------------------------------------------------- //

public class Sistema{   // a VM com tratamento de interrupções
    public VM vm;
    public InterruptHandling ih;
    public SysCallHandling sysCall;
    public static Programas progs;

    public Sistema() { // a VM com tratamento de interrupções
        ih = new InterruptHandling();
        sysCall = new SysCallHandling();
        vm = new VM(ih, sysCall);

        sysCall.setVM(vm);
    }
// -------------------  S I S T E M A - FIM---------------------------------------------------------------- //

// ------------------ U T I L I T A R I O S   D O   S I S T E M A ----------------------------------------- //
// ------------------ load é invocado a partir de requisição do usuário ----------------------------------- //

	public int carregaPrograma(Word[] programa){
		return vm.criaProcesso(programa).getId();
	}

	public void encerraProcesso (PCB processo){
		vm.encerraProcesso(processo);
	}

	public void encerraProcessoById (int processId){
		vm.encerraProcesso(vm.gerenteProcesso.getProcessByID(processId));
	}

	public void carregaAndExecutaPrograma(Word[] programa){
		int pid = carregaPrograma(programa);
		runByProcessId(pid);
	}

	public void runByProcessId(int pid) {
		Processo processo = vm.gerenteProcesso.getProcessByID(pid);
		if (processo != null) {
			int inicioMemoria = processo.getTabelaPaginas()[0] * vm.gerenteProcesso.getTamPagina();
			int fimMemoria = (processo.getTabelaPaginas()[processo.getTabelaPaginas().length - 1] + 1) * vm.gerenteProcesso.getTamPagina() - 1;
			int programCounter = getProgramCounterByProcessId(pid);

			vm.cpu.setContext(inicioMemoria, fimMemoria, programCounter, processo.getTabelaPaginas());
			vm.cpu.run();
		} else {
			System.out.println("Processo com ID " + pid + " não encontrado.");
		}
	}

	public int getProgramCounterbyProcessId(int pid){
		return vm.gerenteProcesso.getProcessByID(pid).pc;
	}

	public void listarProcessos(){
		vm.listaProcessos();
	}

	public void changeDebug(boolean newValue){
		vm.cpu.setDebug(newValue);
	}

	public void dumpMemoria(int posicaoInicial, int posicaoFinal){
		vm.mem.dump(posicaoInicial, posicaoFinal);
	}

	public void dumpPCB(int processId){
		System.out.println(vm.gerenteProcesso.getProcessByID(processId).toString());
	}

	public boolean existeProcesso(int pid){
		return vm.existeProcesso(pid);
	}
}
