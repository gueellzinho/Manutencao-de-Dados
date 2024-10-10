import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

public class Manutencao {
    static Scanner leitor = new Scanner(in);
    enum Ordens {porRa, porNome, porCurso, porMedia};
    private static Ordens ordemAtual = Ordens.porRa;
    private static ManterEstudantes estuds = new ManterEstudantes();
    // índice resultante da pesquisa binária

    public static void main(String[] args) throws Exception {
        try {
            out.println("Digite o nome de um Arquivo para abrir: ");
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
    private static void estatisticas() {
        out.println("Estatisticas dos alunos");
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
