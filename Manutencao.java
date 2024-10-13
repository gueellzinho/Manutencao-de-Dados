import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

public class Manutencao {
    static Scanner leitor = new Scanner(in);
    enum Ordens {porRa, porNome, porCurso, porMedia};
    private static Ordens ordemAtual = Ordens.porRa;
    private static ManterEstudantes estuds = new ManterEstudantes();
    private static String[] disciplinas;
    private static int qtasDisci;
    private static int[] maioresAprovacoes, maioresRetencoes;
    private static double[] somasNotas;
    // índice resultante da pesquisa binária

    public static void main(String[] args) throws Exception {
        try {
            out.print("Digite o nome de um Arquivo para abrir: ");
            String arq = leitor.nextLine();
            estuds.leituraDosDados(arq);
            seletorDeOpcoes();
            estuds.gravarDados(arq);
        }
        catch (FileNotFoundException erro) {
            out.println(erro.getMessage());
        }
    }

    public static void seletorDeOpcoes() throws Exception {
        int opcao = 0;
        do {
            out.println("Opções:\n");
            out.println("0 - Terminar programa");
            out.println("1 - Incluir estudante");
            out.println("2 - Listar estudantes");
            out.println("3 - Excluir estudante");
            out.println("4 - Listar situações");
            out.println("5 - Digitar notas de estudante");
            out.println("6 - Ordenar por curso");
            out.println("7 - Ordenar por nome");
            out.println("8 - Ordenar por média");
            out.println("9 - Ordenar por RA");
            out.println("10 - Estatísticas");
            out.print("\nSua opção: ");
            opcao = leitor.nextInt();
            leitor.nextLine();      // necessário após nextInt() para poder ler strings a seguir
            switch(opcao) {
                case 1: incluirEstudante();break;
                case 2: estuds.listarEstudantes();break;
                case 3: excluir();break;
                case 4: estuds.listarSituacoes();break;
                case 5: digitaNotas();break;
                case 6: ordenarPorCurso();break;
                case 7: ordenarPorNome();break;
                case 8: ordenarPorMedia();break;
                case 9: ordenarPorRa();break;
                case 10: estatisticas();break;
            }
        }
        while (opcao != 0);
    }


    private static void incluirEstudante() throws Exception {
        out.println("Vamos incluir um estudante!");

        out.println("Incluir Estudante\n");
        out.print("Curso : ");
        String curso = leitor.nextLine();
        out.print("RA    : ");
        String ra = leitor.nextLine();
        out.print("Nome  : ");
        String nome = leitor.nextLine();

        estuds.incluirEstudante(curso,ra,nome);
    }

    private static void excluir() throws Exception {
        out.println("Excluir Estudante\n");
        out.print("RA    : ");
        String ra = leitor.nextLine();

        estuds.excluirEstudante(ra);
    }

    private static void digitaNotas() {
        out.println("Digitação de notas de estudante:\n");
        out.print("Digite o RA do(a) estudante desejado(a): ");
        String raEstudante = leitor.nextLine();

        estuds.digitarNotas(raEstudante);
    }

    private static void lerDisciplinas(String arq){
        disciplinas = new String[15];
        qtasDisci = 0;
        int posicaoAtual = 0;
        try{
            BufferedReader arquivoDeEntrada = new BufferedReader(
                    new FileReader(arq));
            String linhaDoArquivo = "";
            try{
                boolean parar = false;
                while(!parar){
                    linhaDoArquivo = arquivoDeEntrada.readLine();
                    if(linhaDoArquivo != null){
                        disciplinas[posicaoAtual] = linhaDoArquivo;
                        qtasDisci++;
                        posicaoAtual++;
                    }
                    else
                        parar = true;
                }
                arquivoDeEntrada.close();
            }
            catch(IOException erroIO){
                out.println(erroIO.getMessage());
            }
        }
        catch(FileNotFoundException erro){
            out.println(erro.getMessage());
        }
    }

    private static void estatisticas(){
        out.println("Estatisticas dos alunos");
        out.print("Digite o nome do arquivo para ser lido: ");
        String nomeArquivo = leitor.nextLine();
        lerDisciplinas(nomeArquivo);
        maioresAprovacoes = new int[15];
        maioresRetencoes = new int[15];
        somasNotas = new double[15];
        int indMaisAprov = 0, indMaisReten = 0, indEstudMaior = 0, indDisciMaior = 0;
        double mediaEstud, mediaDisci, maiorEstud = 0, menorDisci = 10;
        for(int indEstu = 0; indEstu < estuds.qtosDados; indEstu++){
            maisAprovacoes(estuds.dados[indEstu]);
            for(int indAprov = 0; indAprov < qtasDisci; indAprov++){
                if(maioresAprovacoes[indAprov] > indMaisAprov)
                    indMaisAprov = indAprov;
            }

            maisRetencoes(estuds.dados[indEstu]);
            for(int indReten = 0; indReten < qtasDisci; indReten++){
                if(maioresRetencoes[indReten] > indMaisReten)
                    indMaisReten = indReten;
            }

            mediaEstud = estudMaiorMedia(estuds.dados[indEstu]);
            if(mediaEstud > maiorEstud){
                maiorEstud = mediaEstud;
                indEstudMaior = indEstu;
            }

            mediaDisciplinas(estuds.dados[indEstu]);
        }
        out.println("Disciplina com mais aprovacoes: " + disciplinas[indMaisAprov]);
        out.println("Disciplina com mais retencoes: " + disciplinas[indMaisReten]);
        out.println("Estudante com maior media:" + estuds.dados[indEstudMaior].getNome());
        out.println("Maior nota do estudante com maior media: " + notaAlta(estuds.dados[indEstudMaior]));
        out.println("Menor nota do estudante com maior media: " + notaBaixa(estuds.dados[indEstudMaior]));
        for(int i = 0; i < qtasDisci; i++){
            out.print("Media " + disciplinas[i] + ": ");
            out.println(somasNotas[i] / estuds.qtosDados);

        }
    }

    private static void maisAprovacoes(Estudante estudAtual){
        double[] notaAtual = estudAtual.getNotas();
        for(int indNotas=0; indNotas < estudAtual.getQuantasNotas(); indNotas++){
            if(notaAtual[indNotas] >= 5)
                maioresAprovacoes[indNotas]++;
        }
    }

    private static void maisRetencoes(Estudante estudAtual){
        double[] notaAtual = estudAtual.getNotas();
        for(int indNotas=0; indNotas < estudAtual.getQuantasNotas(); indNotas++){
            if(notaAtual[indNotas] < 5)
                maioresRetencoes[indNotas]++;
        }
    }

    private static double estudMaiorMedia(Estudante estudAtual){
        double mediaAtual = estudAtual.mediaDasNotas();
        return mediaAtual;
    }

    private static double notaAlta(Estudante estudMedia){
        double[] notas = estudMedia.getNotas();
        double maiorNota = 0;
        for(int i = 0; i < estudMedia.getQuantasNotas(); i++){
            if(notas[i] > maiorNota)
                maiorNota = notas[i];
        }
        return maiorNota;
    }

    private static double notaBaixa(Estudante estudMedia){
        double[] notas = estudMedia.getNotas();
        double menorNota = 10;
        for(int i = 0; i < estudMedia.getQuantasNotas(); i++){
            if(notas[i] < menorNota)
                menorNota = notas[i];
        }
        return menorNota;
    }

    public static void mediaDisciplinas(Estudante estudAtual){
        double[] notas = estudAtual.getNotas();
        for(int indNota = 0; indNota < qtasDisci; indNota++)
            somasNotas[indNota] += notas[indNota];
    }

    public static void ordenarPorCurso() {
        for (int lento=0; lento < estuds.qtosDados; lento++)
            for (int rapido=lento+1; rapido < estuds.qtosDados; rapido++)
                if (estuds.dados[lento].getCurso().compareTo(estuds.dados[rapido].getCurso()) > 0)
                    estuds.trocar(lento, rapido);
        ordemAtual = Ordens.porCurso;
    }

    public static void ordenarPorRa() {
        for (int lento=0; lento < estuds.qtosDados; lento++)
            for (int rapido=lento+1; rapido < estuds.qtosDados; rapido++)
                if (estuds.dados[lento].getRa().compareTo(estuds.dados[rapido].getRa()) > 0)
                    estuds.trocar(lento, rapido);
        ordemAtual = Ordens.porRa;
    }

    public static void ordenarPorNome() {
        for (int lento=0; lento < estuds.qtosDados; lento++)
            for (int rapido=lento+1; rapido < estuds.qtosDados; rapido++)
                if (estuds.dados[lento].getNome().compareTo(estuds.dados[rapido].getNome()) > 0)
                    estuds.trocar(lento, rapido);
        ordemAtual = Ordens.porNome;
    }

    public static void ordenarPorMedia() {
        for (int lento=0; lento < estuds.qtosDados; lento++) {
            double mediaAtual = estuds.dados[lento].mediaDasNotas();
            for (int rapido=lento+1; rapido < estuds.qtosDados; rapido++)
                if (mediaAtual > estuds.dados[rapido].mediaDasNotas())
                    estuds.trocar(lento, rapido);
            ordemAtual = Ordens.porMedia;
        }
    }
}