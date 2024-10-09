import javax.swing.*;
import java.util.Scanner;
import static java.lang.System.*;

public class Manutencao {
    enum Ordens {porRa, porNome, porCurso, porMedia}
    static Scanner leitor = new Scanner(in);
    static ManterEstudantes estuds;
    private static Ordens ordemAtual = Ordens.porRa;
     // índice resultante da pesquisa binária

    public static void main(String[] args) throws Exception {
        estuds = new ManterEstudantes();
        for (int ind=0; ind < 3; ind++)
            estuds.dados[ind] = new Estudante();
        out.println("Digite o arquivo que deseja abrir: ");
        String arq = leitor.nextLine();
        estuds.leituraDosDados(arq);
        seletorDeOpcoes();
        estuds.gravarDados(arq);
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
                case 1: incluirEstudante();break;
                case 2: listarEstudantes();break;
                case 3: excluirEstudante();break;
                case 4: listarSituacoes();break;
                case 5: digitarNotas();break;
                case 6: ordenarPorCurso();break;
                case 7: ordenarPorNome();break;
                case 8: ordenarPorMedia();break;
                case 9: estatisticas();break;
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

        Estudante novoEstud = new Estudante(curso,ra,nome);

        estuds.incluirNoFinal(novoEstud);
    }
    private static void excluirEstudante() throws Exception {
        out.println("Vamos excluir um estudante!");
        out.print("RA    : ");
        String ra = leitor.nextLine();
        Estudante umEstudante = new Estudante(" ", ra, " ");
        if (estuds.existe(umEstudante))
            estuds.excluir(estuds.posicaoAtual);
        else
        {
            estuds.excluir(estuds.getPosicaoAtual());
        }
    }
    public static void ordenarPorCurso() {
        for (int lento=0; lento < estuds.qtosDados; lento++)
            for (int rapido=lento+1; rapido < estuds.qtosDados; rapido++)
                if (estuds.dados[lento].getCurso().compareTo(estuds.dados[rapido].getCurso()) > 0)
                    estuds.trocar(lento, rapido);
        ordemAtual = Ordens.porCurso;
    }

    public void ordenarPorRa() {
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
    private static void estatisticas() {
        out.println("Estatisticas dos alunos");
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
                    "%4.1f %16s "+estuds.dados[indice]+"\n", mediaDesseEstudante,
                    situacao);
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }
    public static void digitarNotas(String raEstudante) {
        try {
            Estudante estProc = new Estudante("00", raEstudante, "A");
            if (!estuds.existe(estProc))
                out.println("Não há um(a) estudante com esse RA!");
            else {  // se RA foi encontrado, variável onde contém seu índice
                out.print("Quantidade de notas a serem digitadas: ");
                int quant = leitor.nextInt();
                leitor.nextLine();

                estuds.dados[estuds.posicaoAtual].setQuantasNotas(quant);
                double nota;
                for (int indNota = 0; indNota < quant; indNota++) {
                    do {
                        out.printf("Digite a %da. nota:", indNota + 1);
                        nota = leitor.nextDouble();
                        if (nota >= 0 && nota <= 10)
                            break;  // sai do do-while
                        out.println("Nota inválida. Digite novamente:");
                    } while (true);
                    estuds.dados[estuds.posicaoAtual].setNota(nota, indNota);
                }
            }
        }
        catch (Exception erro) {
            out.println("Não foi possivel criar objeto Estudante.");
            out.println(erro.getMessage());
        }
    }
    public void incluirEstudante(String curso, String ra, String nome) throws Exception {
        if (ordemAtual != Ordens.porRa)
            ordenarPorRa();
        Estudante umEstudante = new Estudante(curso, ra, nome);
        if (estuds.existe(umEstudante))
            JOptionPane.showMessageDialog(null,"Estudante repetido!");
        else
        {
            incluirEmOrdem(umEstudante);
        }
    }
    public void excluirEstudante(String ra) throws Exception {
        if (ordemAtual != Ordens.porRa)
            ordenarPorRa();
        Estudante umEstudante = new Estudante(" ", ra, " ");
        if (!estuds.existe(umEstudante))
            JOptionPane.showMessageDialog(null,"Estudante não encontrado!");
        else
        {
            estuds.excluir(estuds.posicaoAtual);
        }
    }
    public void incluirEmOrdem(Estudante novo) {
        if (estuds.qtosDados >= estuds.dados.length)
            estuds.expandirVetor();
        // desloco para a direita os estudantes com RA > RA do novo
        for (int indice = estuds.qtosDados; indice > estuds.posicaoAtual; indice--)
            estuds.dados[indice] = estuds.dados[indice-1];
        estuds.dados[estuds.posicaoAtual] = novo;
        estuds.qtosDados++;
    }
}

