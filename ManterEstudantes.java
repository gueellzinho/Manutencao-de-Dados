import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.in;
import static java.lang.System.out;

public class ManterEstudantes {
    static Estudante[] dados;
    static int qtosDados;
    static int posicaoAtual;
    static Scanner leitor = new Scanner(in);
    private static BufferedReader arquivoDeEntrada;
    private static BufferedWriter arquivoDeSaida;

    public void leituraDosDados(String nomeArquivo) throws IOException {
        dados = new Estudante[50];
        for (int ind=0; ind < 50; ind++)
            dados[ind] = new Estudante();
        qtosDados = 0;
        try {
            posicaoAtual = 0;
            BufferedReader arquivoDeEntrada = new BufferedReader(
                    new FileReader(nomeArquivo));
            String linhaDoArquivo = "";
            try {
                boolean parar = false;
                while (! parar) {
                    Estudante novoDado = new Estudante();
                    try {

                        if (novoDado.leuLinhaDoArquivo(arquivoDeEntrada) ) {
                            incluirNoFinal(novoDado);
                        }
                        else
                            parar = true;
                    }
                    catch (Exception erroDeLeitura) {
                        out.println(erroDeLeitura.getMessage());
                        parar = true;

                    }
                }
                arquivoDeEntrada.close();
            }
            catch (IOException erroDeIO) {
                out.println(erroDeIO.getMessage());
            }
        }
        catch (FileNotFoundException erro) {
            out.println(erro.getMessage());
        }
    }

    public void gravarDados(String nomeArquivo) throws IOException {
        BufferedWriter arquivoDeSaida = new BufferedWriter(
                new FileWriter(nomeArquivo));
        for (int indice=0; indice < qtosDados; indice++)
            arquivoDeSaida.write(dados[indice].formatoDeArquivo());
        arquivoDeSaida.close();
    }

    public boolean existeEstudante(Estudante estProcurado) {
        int inicio = 0;
        int fim = qtosDados - 1;
        boolean achou = false;
        while (! achou && inicio <= fim) {
            posicaoAtual = (inicio + fim) / 2;
            String raDoMeioDoTrechoDoVetor = dados[posicaoAtual].getRa();
            String raDoProcurado = estProcurado.getRa();
            if (raDoMeioDoTrechoDoVetor.compareTo(raDoProcurado) == 0)
                achou = true;
            else
            if (raDoProcurado.compareTo(raDoMeioDoTrechoDoVetor) < 0)
                fim = posicaoAtual - 1;
            else
                inicio = posicaoAtual + 1;
        }
        if (!achou)
            posicaoAtual = inicio;
        return achou;
    }

    public void incluirEstudante(String curso, String ra, String nome) throws Exception {
        Estudante umEstudante = new Estudante(curso, ra, nome);
        if (existeEstudante(umEstudante))
            JOptionPane.showMessageDialog(null,"Estudante repetido!");
        else
        {
            incluirEmOrdem(umEstudante);
        }
    }
    public void excluirEstudante(String ra) throws Exception {
        ordenar();
        Estudante umEstudante = new Estudante(" ", ra, " ");
        if (!existeEstudante(umEstudante))
            JOptionPane.showMessageDialog(null,"Estudante não encontrado!");
        else
        {
            excluir(posicaoAtual);
        }
    }
    private static void expandirVetor() {
        Estudante[] novoVetor = new Estudante[dados.length * 2];
        for (int indice=0; indice<qtosDados; indice++)
            novoVetor[indice] = dados[indice];
        dados = novoVetor;
    }
    public void incluirEmOrdem(Estudante novo) {
        ordenar();
        if (qtosDados >= dados.length)
            expandirVetor();
        for (int indice = qtosDados; indice > posicaoAtual; indice--)
            dados[indice] = dados[indice-1];
        dados[posicaoAtual] = novo;
        qtosDados++;
    }
    public void incluirNoFinal(Estudante novoDado) {
        if (!existeEstudante(novoDado)) {
            if (qtosDados >= dados.length)
                expandirVetor();
            dados[qtosDados] = novoDado;
            qtosDados++;
        }
        else
            out.println("Estudante já existe");
    }
    private static void excluir(int indiceDeExclusao) {
        qtosDados--;
        for (int indice=indiceDeExclusao; indice < qtosDados; indice++)
            dados[indice] = dados[indice+1];
    }


    public void trocar(int indMaior, int indMenor) {
        Estudante auxiliar = dados[indMaior];
        dados[indMaior] = dados[indMenor];
        dados[indMenor] = auxiliar;
    }

    public void alterar(int posicaoDeAlteracao, Estudante novoDado){
        if (posicaoDeAlteracao >= 0 && posicaoDeAlteracao < qtosDados)
            dados[posicaoDeAlteracao] = novoDado;
        else
            throw new IndexOutOfBoundsException();
    }
    public void ordenar(){
        Estudante auxiliar;
        for (int lento = 0; lento < qtosDados; lento++){
            for (int rapido = lento+1; rapido < qtosDados; rapido++){
                if (Integer.parseInt(dados[lento].getRa()) > Integer.parseInt(dados[rapido].getRa())) {
                    auxiliar = dados[lento];
                    dados[lento] = dados[rapido];
                    dados[rapido] = auxiliar;
                }
            }
        }
    }
    public void digitarNotas(String raEstudante) {
        try {
            Estudante estProc = new Estudante("00", raEstudante, "A");
            if (!existeEstudante(estProc))
                out.println("Não há um(a) estudante com esse RA!");
            else {  // se RA foi encontrado, variável onde contém seu índice
                out.print("Quantidade de notas a serem digitadas: ");
                int quant = leitor.nextInt();
                leitor.nextLine();

                dados[posicaoAtual].setQuantasNotas(quant);
                double nota;
                for (int indNota = 0; indNota < quant; indNota++) {
                    do {
                        out.printf("Digite a %da. nota:", indNota + 1);
                        nota = leitor.nextDouble();
                        if (nota >= 0 && nota <= 10)
                            break;  // sai do do-while
                        out.println("Nota inválida. Digite novamente:");
                    } while (true);
                    dados[posicaoAtual].setNota(nota, indNota);
                }
            }
        }
        catch (Exception erro) {
            out.println("Não foi possivel criar objeto Estudante.");
            out.println(erro.getMessage());
        }
    }
}