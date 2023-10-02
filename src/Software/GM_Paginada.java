package Software;

public interface GM_Paginada {

    boolean alocaPagina(int nroPalavras, int[] tabelaPaginas);

    void desalocaPagina(int[] tabelaPaginas);
    // Verifica se há espaço disponível para alocar
    boolean temEspacoParaAlocar(int tamanho);

    int getTamPagina();

}