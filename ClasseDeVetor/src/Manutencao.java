import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

public class Manutencao {
    static Scanner leitor = new Scanner(in);
    static boolean continuarPrograma;
     // índice resultante da pesquisa binária

    public static void main(String[] args) throws Exception {
        continuarPrograma = ManterEstudantes.preencherVetorPorArquivo();
        if (continuarPrograma) {
            seletorDeOpcoes();
            ManterEstudantes.salvarVetorNoArquivo();
        }
        out.println("\nPrograma encerrado.");
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
            out.println("9 - Estatísticas");
            out.print("\nSua opção: ");
            opcao = leitor.nextInt();
            leitor.nextLine();      // necessário após nextInt() para poder ler strings a seguir
            switch(opcao) {
                case 1: inclui();
                case 2: listaEstud();
                case 3: excluir();
                case 4: listaSit();
                case 5: digitaNotas();
                case 6: ordenarCurso();
                case 7: ordenarNome();
                case 8: ordenarMedia();
                case 9: estatisticas();
            }
        }
        while (opcao != 0);
    }


    private static void inclui() throws Exception {
        out.println("Vamos incluir um estudante!");

        out.println("Incluir Estudante\n");
        out.print("Curso : ");
        String curso = leitor.nextLine();
        out.print("RA    : ");
        String ra = leitor.nextLine();
        out.print("Nome  : ");
        String nome = leitor.nextLine();

        ManterEstudantes.incluirEstudante(curso,ra,nome);
    }

    private static void listaEstud() {
        out.println("Vamos listar os estudantes!");
        ManterEstudantes.listarEstudantes();
    }

    private static void listaSit() {
        out.println("Vamos listar as Situações dos Alunos!");
        ManterEstudantes.listarSituacoes();
    }
    private static void excluir() throws Exception {
        out.println("Excluir Estudante\n");
        out.print("RA    : ");
        String ra = leitor.nextLine();

        ManterEstudantes.excluirEstudante(ra);
    }
    private static void ordenarCurso() {
        out.println("Estudante ordenados por curso:");

        ManterEstudantes.ordenarPorCurso();
    }
    private static void ordenarNome() {
        out.println("Estudante ordenados por curso:");

        ManterEstudantes.ordenarPorNome();
    }
    private static void ordenarMedia() {
        out.println("Estudante ordenados por curso:");

        ManterEstudantes.ordenarPorMedia();
    }
    private static void digitaNotas() {
        out.println("Digitação de notas de estudante:\n");
        out.print("Digite o RA do(a) estudante desejado(a): ");
        String raEstudante = leitor.nextLine();

        ManterEstudantes.digitarNotas(raEstudante);
    }
    private static void estatisticas() {
        out.println("Estatisticas dos alunos");
    }
}

