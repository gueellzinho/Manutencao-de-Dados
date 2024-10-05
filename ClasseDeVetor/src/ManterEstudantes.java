import javax.swing.*;

import java.io.*;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class ManterEstudantes {
    enum Ordens {porRa, porNome, porCurso, porMedia};
    private static Ordens ordemAtual = Ordens.porRa;
    private static Estudante[] estud;
    private static int quantosEstudantes;
    static int onde;
    static Scanner leitor = new Scanner(in);
    private static BufferedReader arquivoDeEntrada;
    private static BufferedWriter arquivoDeSaida;

    public ManterEstudantes(){
        estud = new Estudante[50];
        for (int ind=0; ind < 3; ind++)
            estud[ind] = new Estudante();
        quantosEstudantes = 0;
    }
    public static boolean preencherVetorPorArquivo() {
        boolean continuarPrograma = true;
        try {
            arquivoDeEntrada = new BufferedReader(
                    new FileReader("c:\\temp\\dadosEstudantes.txt")
            );
            String linhaDoArquivo = "";
            try
            {
                boolean parar = false;
                while (! parar)
                {
                    Estudante novoEstudante = new Estudante();
                    try
                    {
                        if (novoEstudante.leuLinhaDoArquivo(arquivoDeEntrada) ) {
                            estud[quantosEstudantes] = novoEstudante;
                            quantosEstudantes++;
                        }
                        else
                            parar = true;
                    }
                    catch (Exception erroDeLeitura)
                    {
                        out.println(erroDeLeitura.getMessage());
                        parar = true;
                    }
                }
                arquivoDeEntrada.close();
            }
            catch (IOException erroDeIO)
            {
                out.println(erroDeIO.getMessage());
                continuarPrograma = false;
            }
        }
        catch (FileNotFoundException erro) {
            out.println(erro.getMessage());
            continuarPrograma = false;
        }
        return continuarPrograma;
    }

    public static void salvarVetorNoArquivo() throws IOException {
        arquivoDeSaida = new BufferedWriter(
                new FileWriter("c:\\temp\\dadosEstudantes.txt"));
        if (ordemAtual != Ordens.porRa)
            ordenarPorRa();
        for (int indice=0; indice < quantosEstudantes; indice++)
            arquivoDeSaida.write(estud[indice].formatoDeArquivo());
        arquivoDeSaida.close();
    }

    public static boolean existeEstudante(Estudante estProcurado) {
        int inicio = 0;
        int fim = quantosEstudantes - 1;
        boolean achou = false;
        while (! achou && inicio <= fim) {
            onde = (inicio + fim) / 2;
            String raDoMeioDoTrechoDoVetor = estud[onde].getRa();
            String raDoProcurado = estProcurado.getRa();
            if (raDoMeioDoTrechoDoVetor.compareTo(raDoProcurado) == 0)
                achou = true;
            else
            if (raDoProcurado.compareTo(raDoMeioDoTrechoDoVetor) < 0)
                fim = onde - 1;
            else
                inicio = onde + 1;
        }
        if (!achou)
            onde = inicio;
        return achou;
    }

    public static void incluirEstudante(String curso, String ra, String nome) throws Exception {
        if (ordemAtual != Ordens.porRa)
            ordenarPorRa();
        Estudante umEstudante = new Estudante(curso, ra, nome);
        if (existeEstudante(umEstudante))
            JOptionPane.showMessageDialog(null,"Estudante repetido!");
        else
        {
            incluirEmOrdem(umEstudante);
        }
    }
    public static void excluirEstudante(String ra) throws Exception {
        if (ordemAtual != Ordens.porRa)
            ordenarPorRa();
        Estudante umEstudante = new Estudante(" ", ra, " ");
        if (!existeEstudante(umEstudante))
            JOptionPane.showMessageDialog(null,"Estudante não encontrado!");
        else
        {
            excluir(onde);
        }
    }
    private static void expandirVetor() {
        Estudante[] novoVetor = new Estudante[estud.length * 2];
        for (int indice=0; indice<quantosEstudantes; indice++)
            novoVetor[indice] = estud[indice];
        estud = novoVetor;
    }
    private static void incluirEmOrdem(Estudante novo) {
        if (quantosEstudantes >= estud.length)
            expandirVetor();
        // desloco para a direita os estudantes com RA > RA do novo
        for (int indice = quantosEstudantes; indice > onde; indice--)
            estud[indice] = estud[indice-1];
        estud[onde] = novo;
        quantosEstudantes++;
    }



    private static void excluir(int indiceDeExclusao) {
        quantosEstudantes--;
        for (int indice=indiceDeExclusao; indice < quantosEstudantes; indice++)
            estud[indice] = estud[indice+1];
    }

    public static void listarEstudantes() {
        out.println("\n\nListagem de Estudantes\n");
        int contLinha = 0;  // contador de linhas
        for (int ind = 0; ind < quantosEstudantes; ind++)
        {
            out.println(estud[ind]);

            if (++contLinha >= 20) {
                out.print("\n\nTecle [Enter] para prosseguir: ");
                leitor.nextLine();
                contLinha = 0;
            }
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }

    public static void listarSituacoes() {
        out.println("\n\nSituação estudantil\n");
        String situacao = "";
        for (int indice = 0; indice < quantosEstudantes; indice++)
        {
            double mediaDesseEstudante = estud[indice].mediaDasNotas();
            if (mediaDesseEstudante < 5)
                situacao = "Não promovido(a)";
            else
                situacao = "Promovido(a)    ";

            out.printf(
                    "%4.1f %16s "+estud[indice]+"\n", mediaDesseEstudante,
                    situacao);
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }
    private static void trocar(int indMaior, int indMenor) {
        Estudante auxiliar = estud[indMaior];
        estud[indMaior] = estud[indMenor];
        estud[indMenor] = auxiliar;
    }
    public static void ordenarPorCurso() {
        for (int lento=0; lento < quantosEstudantes; lento++)
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (estud[lento].getCurso().compareTo(estud[rapido].getCurso()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porCurso;
    }

    public static void ordenarPorRa() {
        for (int lento=0; lento < quantosEstudantes; lento++)
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (estud[lento].getRa().compareTo(estud[rapido].getRa()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porRa;
    }

    public static void ordenarPorNome() {
        for (int lento=0; lento < quantosEstudantes; lento++)
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (estud[lento].getNome().compareTo(estud[rapido].getNome()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porNome;
    }

    public static void ordenarPorMedia() {
        for (int lento=0; lento < quantosEstudantes; lento++) {
            double mediaAtual = estud[lento].mediaDasNotas();
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (mediaAtual > estud[rapido].mediaDasNotas())
                    trocar(lento, rapido);
            ordemAtual = Ordens.porMedia;
        }
    }
    static void digitarNotas(String raEstudante) {
        try {
            Estudante estProc = new Estudante("00", raEstudante, "A");
            if (!existeEstudante(estProc))
                out.println("Não há um(a) estudante com esse RA!");
            else {  // se RA foi encontrado, variável onde contém seu índice
                out.print("Quantidade de notas a serem digitadas: ");
                int quant = leitor.nextInt();
                leitor.nextLine();

                estud[onde].setQuantasNotas(quant);
                double nota;
                for (int indNota = 0; indNota < quant; indNota++) {
                    do {
                        out.printf("Digite a %da. nota:", indNota + 1);
                        nota = leitor.nextDouble();
                        if (nota >= 0 && nota <= 10)
                            break;  // sai do do-while
                        out.println("Nota inválida. Digite novamente:");
                    } while (true);
                    estud[onde].setNota(nota, indNota);
                }
            }
        }
        catch (Exception erro) {
            out.println("Não foi possivel criar objeto Estudante.");
            out.println(erro.getMessage());
        }
    }
}
