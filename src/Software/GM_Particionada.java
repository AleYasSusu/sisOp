package Software;
public class GM_Particionada {
    private int[] partitions;
    private int tamPart;

    public GM_Particionada(int tamMem, int tamPart) {
        this.tamPart = tamPart;
        int numPartitions = tamMem / tamPart;
        partitions = new int[numPartitions];
        // Inicializa as partições como livres (por exemplo, -1 para indicar livre)
        for (int i = 0; i < numPartitions; i++) {
            partitions[i] = -1;
        }
    }
    public int getTamPart() {
        return tamPart;
    }

    public int alocaParticao() {
        // Encontra uma partição livre
        for (int i = 0; i < partitions.length; i++) {
            if (partitions[i] == -1) {
                partitions[i] = i; // Marca a partição como alocada
                return i; // Retorna o número da partição alocada
            }
        }
        return -1; // Não há partições livres
    }

    public void desalocaParticao(int part) {
        if (part >= 0 && part < partitions.length) {
            partitions[part] = -1; // Marca a partição como livre
        }
    }

    public boolean temEspacoParaAlocar(int tamanhoAlocar) {
        int numParticoesLivres = 0;
        for (int i = 0; i < partitions.length; i++) {
            if (partitions[i] == -1) {
                numParticoesLivres++;
                if (numParticoesLivres >= tamanhoAlocar) {
                    return true; // Espaço suficiente para alocar
                }
            }
        }
        return false; // Não há espaço suficiente para alocar
    }
}
