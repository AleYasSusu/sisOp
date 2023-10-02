package Software;

public interface GM_Particionada {
    int alocaParticao(int tamanho);
     void desalocaParticao(int endereco);
    // Verifica se há espaço disponível para alocar
    boolean temEspacoParaAlocar(int tamanho);

    int getTamanhoParticao();
    int getTamFrame();

}
