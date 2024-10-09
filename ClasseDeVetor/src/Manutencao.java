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
        ManterEstudantes.gravarDados();
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
                case 1: inclui();break;
                case 2: listaEstud();break;
                case 3: excluir();break;
                case 4: listaSit();break;
                case 5: digitaNotas();break;
                case 6: ordenarCurso();break;
                case 7: ordenarNome();break;
                case 8: ordenarMedia();break;
                case 9: estatisticas();break;
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
    public void ordenarPorCurso() {
        for (int lento=0; lento < qtosDados; lento++)
            for (int rapido=lento+1; rapido < qtosDados; rapido++)
                if (dados[lento].getCurso().compareTo(dados[rapido].getCurso()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porCurso;
    }

    public void ordenarPorRa() {
        for (int lento=0; lento < qtosDados; lento++)
            for (int rapido=lento+1; rapido < qtosDados; rapido++)
                if (dados[lento].getRa().compareTo(dados[rapido].getRa()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porRa;
    }

    public void ordenarPorNome() {
        for (int lento=0; lento < qtosDados; lento++)
            for (int rapido=lento+1; rapido < qtosDados; rapido++)
                if (dados[lento].getNome().compareTo(dados[rapido].getNome()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porNome;
    }

    public void ordenarPorMedia() {
        for (int lento=0; lento < qtosDados; lento++) {
            double mediaAtual = dados[lento].mediaDasNotas();
            for (int rapido=lento+1; rapido < qtosDados; rapido++)
                if (mediaAtual > dados[rapido].mediaDasNotas())
                    trocar(lento, rapido);
            ordemAtual = Ordens.porMedia;
        }
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
    public void listarEstudantes() {
        out.println("\n\nListagem de Estudantes\n");
        int contLinha = 0;  // contador de linhas
        for (int ind = 0; ind < qtosDados; ind++)
        {
            out.println(dados[ind]);

            if (++contLinha >= 20) {
                out.print("\n\nTecle [Enter] para prosseguir: ");
                leitor.nextLine();
                contLinha = 0;
            }
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }

    public void listarSituacoes() {
        out.println("\n\nSituação estudantil\n");
        String situacao = "";
        for (int indice = 0; indice < qtosDados; indice++)
        {
            double mediaDesseEstudante = dados[indice].mediaDasNotas();
            if (mediaDesseEstudante < 5)
                situacao = "Não promovido(a)";
            else
                situacao = "Promovido(a)    ";

            out.printf(
                    "%4.1f %16s "+dados[indice]+"\n", mediaDesseEstudante,
                    situacao);
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }
    public void digitarNotas(String raEstudante) {
        try {
            Estudante estProc = new Estudante("00", raEstudante, "A");
            if (!existe(estProc))
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
    public void incluirEstudante(String curso, String ra, String nome) throws Exception {
        if (ordemAtual != Ordens.porRa)
            ordenarPorRa();
        Estudante umEstudante = new Estudante(curso, ra, nome);
        if (existe(umEstudante))
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
        if (!existe(umEstudante))
            JOptionPane.showMessageDialog(null,"Estudante não encontrado!");
        else
        {
            excluir(posicaoAtual);
        }
    }
    public void expandirVetor() {
        Estudante[] novoVetor = new Estudante[dados.length * 2];
        for (int indice=0; indice<qtosDados; indice++)
            novoVetor[indice] = dados[indice];
        dados = novoVetor;
    }
    public void incluirEmOrdem(Estudante novo) {
        if (qtosDados >= dados.length)
            expandirVetor();
        // desloco para a direita os estudantes com RA > RA do novo
        for (int indice = qtosDados; indice > posicaoAtual; indice--)
            dados[indice] = dados[indice-1];
        dados[posicaoAtual] = novo;
        qtosDados++;
    }
}

