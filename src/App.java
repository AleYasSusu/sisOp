import Programas.Programas;
import Sistema.Sistema;

import java.util.Scanner;

public class App {
	public static Programas progs;

	public static void main(String[] args) {
		progs = new Programas();
		Sistema s = new Sistema();
		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.println("\nEscolha uma opção:");
			System.out.println("1 - Cria");
			System.out.println("2 - Lista Processos");
			System.out.println("3 - Dump");
			System.out.println("4 - Desaloca");
			System.out.println("5 - DumpM");
			System.out.println("6 - Executa");
			System.out.println("7 - Trace On");
			System.out.println("8 - Trace Off");
			System.out.println("9 - Exit");

			System.out.print("Digite uma opção: ");
			int opcao = in.nextInt();

			switch (opcao) {
				case 1:
					System.out.println("\nEscolha o programa:");
					System.out.println("1 - Fibonacci");
					System.out.println("2 - FibonacciTRAP");
					System.out.println("3 - Fatorial");
					System.out.println("4 - FatorialTRAP");
					System.out.println("5 - ProgMinimo");
					System.out.println("6 - PB");
					System.out.println("7 - PC");
					System.out.println("9 - Exit");
					System.out.print("Programa: ");
					int programaSelecionado = in.nextInt();
					int id;

					switch (programaSelecionado) {
						case 1:
							id = s.carregaPrograma(progs.fibonacci10);
							System.out.println("Processo criado - Identificador do Processo: " + (id < 0 ? "Não foi possível criar o processo." : id));
							break;
						case 2:
							id = s.carregaPrograma(progs.fibonacciTRAP);
							System.out.println("Processo criado - Identificador do Processo: " + (id < 0 ? "Não foi possível criar o processo." : id));
							break;
						case 3:
							id = s.carregaPrograma(progs.fatorial);
							System.out.println("Processo criado - Identificador do Processo: " + (id < 0 ? "Não foi possível criar o processo." : id));
							break;
						case 4:
							id = s.carregaPrograma(progs.fatorialTRAP);
							System.out.println("Processo criado - Identificador do Processo: " + (id < 0 ? "Não foi possível criar o processo." : id));
							break;
						case 5:
							id = s.carregaPrograma(progs.progMinimo);
							System.out.println("Processo criado - Identificador do Processo: " + (id < 0 ? "Não foi possível criar o processo." : id));
							break;
						case 6:
							id = s.carregaPrograma(progs.PB);
							System.out.println("Processo criado - Identificador do Processo: " + (id < 0 ? "Não foi possível criar o processo." : id));
							break;
						case 7:
							id = s.carregaPrograma(progs.PC);
							System.out.println("Processo criado - Identificador do Processo: " + (id < 0 ? "Não foi possível criar o processo." : id));
							break;
						default:
							break;
					}
					break;

				case 2:
					s.listarProcessos();
					break;

				case 3:
					System.out.print("\nProcess ID: ");
					int pid = in.nextInt();
					if (s.existeProcesso(pid))
						s.dumpPCB(pid);
					else
						System.out.println("Processo não encontrado.");
					break;

				case 4:
					System.out.print("\nProcess ID: ");
					int pid2 = in.nextInt();
					if (s.existeProcesso(pid2))
						s.encerraProcessoById(pid2);
					else
						System.out.println("Processo não encontrado.");
					break;

				case 5:
					System.out.print("\nInicio: ");
					int beginning = in.nextInt();
					System.out.print("Fim: ");
					int end = in.nextInt();
					if (beginning < 0 || beginning > s.vm.tamMem || end < 0 || end > s.vm.tamMem || beginning > end) {
						System.out.println("Endereços inválidos.");
					} else
						s.dumpMemoria(beginning, end);
					break;

				case 6:
					System.out.print("\nProcess ID: ");
					int pid3 = in.nextInt();
					if (s.existeProcesso(pid3))
						s.runByProcessId(pid3);
					else
						System.out.println("Processo não encontrado.");
					break;

				case 7:
					s.changeDebug(true);
					System.out.println("Debug On");
					break;

				case 8:
					s.changeDebug(false);
					System.out.println("Debug Off");
					break;

				case 9:
					System.exit(0);
					break;

				default:
					System.out.println("Opção inválida.");
					break;
			}
		}
	}
}
