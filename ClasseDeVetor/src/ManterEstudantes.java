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

<<<<<<< HEAD
    public void leituraDosDados(String nomeArquivo) throws IOException {
        dados = new Estudante[50];
        for (int ind=0; ind < 50; ind++)
            dados[ind] = new Estudante();
        qtosDados = 0;
=======

    public void leituraDosDados(String nomeDoArquivo) {
>>>>>>> a7bcaaff1f6f85cab40f4ba7cddd46dadaf58846
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
<<<<<<< HEAD
    public static void listarEstudantes() {
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

    public boolean existeEstudante(Estudante estProcurado) {
=======

    public Boolean existe(Estudante dadoProcurado) {
>>>>>>> a7bcaaff1f6f85cab40f4ba7cddd46dadaf58846
        int inicio = 0;
        int fim = qtosDados - 1;
        boolean achou = false;
        while (! achou && inicio <= fim) {
            posicaoAtual = (inicio + fim) / 2;
            String raDoMeioDoTrechoDoVetor = dados[posicaoAtual].getRa();
<<<<<<< HEAD
            String raDoProcurado = estProcurado.getRa();
=======
            String raDoProcurado = dadoProcurado.getRa();
>>>>>>> a7bcaaff1f6f85cab40f4ba7cddd46dadaf58846
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

<<<<<<< HEAD
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
=======
    public void trocar(int origem, int destino)
    {
        
    }

    public void incluirNoFinal(Estudante novoDado) {
        if (!existe(novoDado)) {
            if (qtosDados >= dados.length)
                expandirVetor();
            dados[qtosDados - 1] = novoDado;
>>>>>>> a7bcaaff1f6f85cab40f4ba7cddd46dadaf58846
            qtosDados++;
        }
        else
            out.println("Estudante já existe");
    }
<<<<<<< HEAD
    private static void excluir(int indiceDeExclusao) {
        qtosDados--;
        for (int indice=indiceDeExclusao; indice < qtosDados; indice++)
            dados[indice] = dados[indice+1];
    }

    public static void listarSituacoes() {
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
                    "%4.1f %16s "+ dados[indice]+"\n", mediaDesseEstudante,
                    situacao);
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }
    public void trocar(int indMaior, int indMenor) {
        Estudante auxiliar = dados[indMaior];
        dados[indMaior] = dados[indMenor];
        dados[indMenor] = auxiliar;
=======

    public void incluirEm(Estudante novoDado, int posicaoDeInclusao) {
        if (!existe(novoDado)) {
            if (qtosDados >= dados.length)
                expandirVetor();
            for (int indice = posicaoDeInclusao; indice > posicaoAtual; indice--)
                dados[indice] = dados[indice-1];
            dados[posicaoDeInclusao] = novoDado;
            qtosDados++;
        }
        else
            out.println("Estudante já existe");
    }

    public void expandirVetor() {
        Estudante[] novoVetor = new Estudante[dados.length * 2];
        for (int indice=0; indice<qtosDados; indice++)
            novoVetor[indice] = dados[indice];
        dados = novoVetor;
    }

    public void excluir(int posicaoDeExclusao) {
        qtosDados--;
        for (int indice=posicaoDeExclusao; indice < posicaoDeExclusao; indice++)
            dados[indice] = dados[indice+1];
>>>>>>> a7bcaaff1f6f85cab40f4ba7cddd46dadaf58846
    }

    public void alterar(int posicaoDeAlteracao, Estudante novoDado){
        if (posicaoDeAlteracao >= 0 && posicaoDeAlteracao < qtosDados)
            dados[posicaoDeAlteracao] = novoDado;
        else
            throw new IndexOutOfBoundsException();
    }
<<<<<<< HEAD
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
=======

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
    public Boolean estaVazio(){
        if (dados[posicaoAtual] == null)
            return true;
        else
            return false;
    }
    public Boolean estaNoInicio(){
        for (int i = 0; i < qtosDados/2; i++) {
            if (dados[i] == dados[posicaoAtual])
                return true;
        }
        return false;
    }
    public Boolean estaNoFim(){
        for (int i = qtosDados/2; i < qtosDados; i++) {
            if (dados[i] == dados[posicaoAtual])
                return true;
        }
        return false;
    }

    public void irAoInicio(){
        posicaoAtual = 0;
    }
    public void irAoFim(){
        posicaoAtual = qtosDados-1;
    }
    public void irAoAnterior(){
        posicaoAtual -= 1;
    }
    public void irAoProximo(){
        posicaoAtual += 1;
    }
    public int getPosicaoAtual(){
        return posicaoAtual;
    }
    public void setPosicaoAtual(int novaPosicao){
        posicaoAtual = novaPosicao;
    }
    public Situacao getSituacao(){
        return situacao;
    }
    public void setSituacao(Situacao novaSituacao){
        situacao = novaSituacao;
    }

    public Estudante valorDe(int indiceDeAcesso){return dados[indiceDeAcesso];}
}
>>>>>>> a7bcaaff1f6f85cab40f4ba7cddd46dadaf58846
