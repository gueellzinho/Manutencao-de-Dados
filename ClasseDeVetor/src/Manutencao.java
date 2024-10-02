import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

public class Manutencao {

    private static BufferedReader arquivoDeEntrada;
    private static BufferedWriter arquivoDeSaida;
    static Scanner leitor = new Scanner(in);
    static boolean continuarPrograma = true;
     // índice resultante da pesquisa binária

    public static void main(String[] args) throws Exception {
        estud = new Estudante[3];  // 50 - tamanho físico
        for (int ind=0; ind < 3; ind++)
            estud[ind] = new Estudante(); // criar objetos Estudante vazios no vetor
        quantosEstudantes = 0; // tamanho lógico (vetor vazio)
        preencherVetorPorArquivo();
        if (continuarPrograma) {
            seletorDeOpcoes();
            salvarVetorNoArquivo();
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
            out.print("\nSua opção: ");
            opcao = leitor.nextInt();
            leitor.nextLine();      // necessário após nextInt() para poder ler strings a seguir
            switch(opcao) {

            }
        }
        while (opcao != 0);
    }

    // compareTo()   == 0 primeiro dado igual outro dado
    // compareTo()   < 0  primeiro dado < outro dado
    // compareTo()   > 0  dado this > outro dado
    // esse método guarda no atributo "onde" o índice de inclusão ou
    // o índice em que o estudante procurado foi encontrado
    }
}
