import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

public class Manutencao {
    static Scanner leitor = new Scanner(in);
    enum Ordens {porRa, porNome, porCurso, porMedia}
    private static Ordens ordemAtual = Ordens.porRa;
    private static ManterEstudantes estuds = new ManterEstudantes();
    private static String[] disciplinas;
    private static int qtasDisci, indEstudMenorNota, indEstudMaiorNota;
    private static int[] maioresAprovacoes, maioresRetencoes;
    private static double[] somasNotas;
    private static double notaMaisBaixa, notaMaisAlta;

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
                case 2: listarEstudantes();break;
                case 3: excluir();break;
                case 4: listarSituacoes();break;
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

    public static void listarSituacoes() {
        out.println("\n\nSituação estudantil\n");
        String situacao = "";
        for (int indice = 0; indice < estuds.qtosDados; indice++)
        {
            double mediaDesseEstudante = estuds.dados[indice].mediaDasNotas();
            if (mediaDesseEstudante < 5)
                situacao = "Não promovido(a)";
            else
                situacao = "Promovido(a)    ";

            out.printf(
                    "%4.1f %16s "+ estuds.dados[indice]+"\n", mediaDesseEstudante,
                    situacao);
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }

    public static void listarEstudantes() {
        out.println("\n\nListagem de Estudantes\n");
        int contLinha = 0;  // contador de linhas
        for (int ind = 0; ind < estuds.qtosDados; ind++)
        {
            out.println(estuds.dados[ind]);

            if (++contLinha >= 20) {
                out.print("\n\nTecle [Enter] para prosseguir: ");
                leitor.nextLine();
                contLinha = 0;
            }
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
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
        out.println("\nEstatisticas dos alunos");
        out.print("Digite o nome do arquivo para ser lido: ");
        String nomeArquivo = leitor.nextLine();
        lerDisciplinas(nomeArquivo);

        int indMaisAprov = 0, indMaisReten = 0, indEstudMaiorMedia = 0, indDisciMenorMedia = 0;
        double maiorMediaTemp, maiorMediaEstud = 0, menorMediaDisci = 10;
        maioresAprovacoes = new int[15];
        maioresRetencoes = new int[15];
        somasNotas = new double[15];

        for(int indEstud = 0; indEstud < estuds.qtosDados; indEstud++)
            maisAprovacoes(estuds.dados[indEstud]);
        for (int indAprov = 0; indAprov < qtasDisci; indAprov++) {
            if (maioresAprovacoes[indAprov] >= indMaisAprov)
                indMaisAprov = indAprov;
        }
        out.printf("\nDisciplina com mais aprovações: %s", disciplinas[indMaisAprov]);

        for(int indEstud = 0; indEstud < estuds.qtosDados; indEstud++)
            maisRetencoes(estuds.dados[indEstud]);
        for (int indReten = 0; indReten < qtasDisci; indReten++) {
            if (maioresRetencoes[indReten] >= indMaisReten)
                indMaisReten = indReten;
        }
        out.printf("\nDisciplina com mais retenções: %s\n", disciplinas[indMaisReten]);

        for(int indEstud = 0; indEstud < estuds.qtosDados; indEstud++) {
            maiorMediaTemp = estudMaiorMedia(estuds.dados[indEstud]);
            if (maiorMediaTemp > maiorMediaEstud) {
                maiorMediaEstud = maiorMediaTemp;
                indEstudMaiorMedia = indEstud;
            }
        }
        out.printf("\nEstudante com maior média: %s", estuds.dados[indEstudMaiorMedia].getNome());
        out.printf("\nMaior nota desse estudante: %f", notaAlta(estuds.dados[indEstudMaiorMedia]));
        out.printf("\nMenor nota desse estudante: %f\n", notaBaixa(estuds.dados[indEstudMaiorMedia]));

        for(int indEstud = 0; indEstud < estuds.qtosDados; indEstud++){
            mediaDisciplinas(estuds.dados[indEstud]);
            for(int i = 0; i < qtasDisci; i++){
                if((somasNotas[i] / estuds.qtosDados) < menorMediaDisci){
                    indDisciMenorMedia = i;
                }
            }
        }
        notaMaisAlta = 0;
        notaMaisBaixa = 10;
        for(int indEstud = 0; indEstud < estuds.qtosDados; indEstud++){
            estudMenorNota(estuds.dados[indEstud], indDisciMenorMedia, indEstud);
            estudMaiorNota(estuds.dados[indEstud], indDisciMenorMedia, indEstud);
        }
        for(int i = 0; i < qtasDisci; i++)
            out.printf("\nMédia %s: %f", disciplinas[i], (somasNotas[i] / estuds.qtosDados));
        out.printf("\n\nDisciplina com menor média: %s", disciplinas[indDisciMenorMedia]);
        out.printf("\n%s teve a menor nota em %s", estuds.dados[indEstudMenorNota].getNome().trim(), disciplinas[indDisciMenorMedia]);
        out.printf("\n%s teve a maior nota em %s", estuds.dados[indEstudMaiorNota].getNome().trim(), disciplinas[indDisciMenorMedia]);

        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }

    private static void maisAprovacoes(Estudante estudAtual){
        double[] notaAtual = estudAtual.getNotas();
        int qtasNotas = estudAtual.getQuantasNotas();
        for(int indNotas=0; indNotas < qtasNotas; indNotas++){
            if(notaAtual[indNotas] >= 5)
                maioresAprovacoes[indNotas]++;
        }
    }

    private static void maisRetencoes(Estudante estudAtual){
        double[] notaAtual = estudAtual.getNotas();
        int qtasNotas = estudAtual.getQuantasNotas();
        for(int indNotas=0; indNotas < qtasNotas; indNotas++){
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
        for(int indNota = 0; indNota < qtasDisci; indNota++){
            somasNotas[indNota] += notas[indNota];
        }
    }

    public static void estudMenorNota(Estudante estudAtual, int indDisci, int indEstud){
        double[] notas = estudAtual.getNotas();
        if(notas[indDisci] <= notaMaisBaixa){
            notaMaisBaixa = notas[indDisci];
            indEstudMenorNota = indEstud;
        }
    }

    private static void estudMaiorNota(Estudante estudAtual, int indDisci, int indEstud){
        double[] notas = estudAtual.getNotas();
        if(notas[indDisci] >= notaMaisAlta){
            notaMaisAlta = notas[indDisci];
            indEstudMaiorNota = indEstud;
        }
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