import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.in;
import static java.lang.System.out;

public class ManterEstudantes implements ManterDados {
    int qtosDados, posicaoAtual;
    Estudante[] dados = new Estudante[50];
    Situacao situacao;
    static Scanner leitor = new Scanner(in);

    public void leituraDosDados(String nomeDoArquivo) {
        try {
            posicaoAtual = 0;
            BufferedReader arquivoDeEntrada = new BufferedReader(
                    new FileReader("c:\\temp\\dadosEstudantes.txt"));
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
                new FileWriter("c:\\temp\\dadosEstudantes.txt"));
        for (int indice=0; indice < qtosDados; indice++)
            arquivoDeSaida.write(dados[indice].formatoDeArquivo());
        arquivoDeSaida.close();
    }

    public Boolean existe(Estudante dadoProcurado) {
        int inicio = 0;
        int fim = qtosDados - 1;
        boolean achou = false;
        while (! achou && inicio <= fim) {
            posicaoAtual = (inicio + fim) / 2;
            String raDoMeioDoTrechoDoVetor = dados[posicaoAtual].getRa();
            String raDoProcurado = dadoProcurado.getRa();
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

    public void trocar(int origem, int destino)
    {
        
    }

    public void incluirNoFinal(Estudante novoDado) {
        if (!existe(novoDado)) {
            if (qtosDados >= dados.length)
                expandirVetor();
            dados[qtosDados - 1] = novoDado;
            qtosDados++;
        }
        else
            out.println("Estudante já existe");
    }

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

    public void excluir(int posicaoDeExclusao) {
        qtosDados--;
        for (int indice=posicaoDeExclusao; indice < posicaoDeExclusao; indice++)
            dados[indice] = dados[indice+1];
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
    publoc void digitarNotas(String raEstudante) {
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
}
