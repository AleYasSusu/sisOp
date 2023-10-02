package Software;

import Hardware.Word;

public class Processo {
    private int id;
    private Word[] programa;
    private int particao;
    private int[] tabelaPaginas;
    private int tamanho;

    public Processo(int id, Word[] programa, int particao, int tamanho) {
        this.id = id;
        this.programa = programa;
        this.particao = particao;
        this.tamanho = tamanho;
    }

    public Processo(int id, Word[] programa, int[] tabelaPaginas) {
        this.id = id;
        this.programa = programa;
        this.tabelaPaginas = tabelaPaginas;
        this.tamanho = tabelaPaginas.length;
    }

    public int getId() {
        return id;
    }

    public Word[] getPrograma() {
        return programa;
    }

    public int getParticao() {
        return particao;
    }

    public int[] getTabelaPaginas() {
        return tabelaPaginas;
    }

    public int getTamanho() {
        return tamanho;
    }
}
