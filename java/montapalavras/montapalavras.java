import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.*;
import java.text.Normalizer;
import java.util.Arrays;

/*
    Este algoritmo, primeiro, lê as palavras que compõem o banco de palavras
    para criar seus respectivos objetos da classe Palavra. Essa classe tem
    apenas três atributos simples e alguns métodos, como pode-se ver abaixo.
    O construtor dessa classe, ao receber uma string com a palavra, já calcula
    os pontos através de expressões regulares que contam os caracteres e somam
    a pontuação correspondente a cada um, de acordo com a definição no enunciado.
    Ainda no construtor, armazena-se a forma normalizada da palavra, isto é,
    sem maiúsculas ou diacríticos.

    Após esse pré-processamento, o algoritmo percorre a lista do banco de palavras
    e, para cada uma delas, ordena seus caracteres. Isso é útil para juntar os
    caracteres repetidos e podermos procurar por eles juntos, avaliando também
    se há a quantidade necessária de cada um na lista de letras da entrada,
    agora também normalizada e sem maiúsculas.

    Assim, novamente com expressões regulares, são feitas as correspondências
    entre cada grupo de caracteres (com um ou mais elementos iguais) das palavras
    do banco com a lista de letras. Aquelas palavras que forem satisfeitas em
    em todas as suas buscas, isto é, todas as suas letras foram encontradas na
    lista de letras da entrada em quantidades suficientes, são possíveis de serem
    montadas.

    A primeira palavra que se encaixa nesses critérios é logo salva como
    resposta. A partir disso, as próximas são comparadas com essa resposta
    temporária e, caso uma tenha maior pontuação ou atenda aos critérios de
    desempate, ela substitui a outra até o fim do algoritmo ou até que outra
    lhe tire o lugar.
*/

class Palavra {
    private String palavra;
    private String palavraNormalizada;
    private int pontos;

    Palavra(String palavra){
        this.palavra = palavra;
        String palavraNormalizada = Normalizer.normalize(palavra, Normalizer.Form.NFD)
                                              .replaceAll("[^\\p{ASCII}]", "");
        this.palavraNormalizada = palavraNormalizada;


        // calculando os pontos de acordo com a definição
        // a avaliação é feita na palavra normalizada,
        // por causa de letras maiúsculas e diacríticos
        int pontos = 0;

        Matcher matcher;

        Pattern pts1 = Pattern.compile("(?i)[EAIONRTLSU]");
        pontos += pts1.matcher(palavraNormalizada).results().count();

        Pattern pts2 = Pattern.compile("(?i)[WDG]");
        pontos += pts2.matcher(palavraNormalizada).results().count() * 2;

        Pattern pts3 = Pattern.compile("(?i)[BCMP]");
        pontos += pts3.matcher(palavraNormalizada).results().count() * 3;

        Pattern pts4 = Pattern.compile("(?i)[FHV]");
        pontos += pts4.matcher(palavraNormalizada).results().count() * 4;

        Pattern pts8 = Pattern.compile("(?i)[JX]");
        pontos += pts8.matcher(palavraNormalizada).results().count() * 8;

        Pattern pts10 = Pattern.compile("(?i)[QZ]");
        pontos += pts10.matcher(palavraNormalizada).results().count() * 10;

        this.pontos = pontos;
    }

    void setPalavra(String palavra){
        this.palavra = palavra;
    }

    void setPontos(int pontos){
        this.pontos = pontos;
    }

    public String getPalavra(){
        return this.palavra;
    }

    public String getPalavraNormalizada(){
        return this.palavraNormalizada;
    }

    public int getPontos(){
        return this.pontos;
    }
}

public class montapalavras {
    public static void main (String args[]){
        List<Palavra> palavras = new ArrayList<Palavra>();

// -------------------------- LEITURA DE ARQUIVO --------------------------- //

        // lendo arquivo com banco de palavras e salvando na lista palavras
        try {
            File file = new File ("banco.txt");
            Scanner reader = new Scanner(file).useDelimiter(" ");
            while (reader.hasNextLine()){
                String palavra = reader.next();
                palavra = palavra.replace("\"", "");
                palavra = palavra.replace(",", "");
                palavras.add(new Palavra(palavra));
            }
        } catch (FileNotFoundException e){
            System.out.println("Erro ao ler arquivo com banco de palavras.");
            e.printStackTrace();
        }

// ------------------------------- ENTRADA --------------------------------- //

        // lendo a sequência de letras e normalizando
        System.out.print("Digite as letras disponíveis nesta jogada: ");
        Scanner s = new Scanner(System.in);
        String letras = s.nextLine();
        letras = Normalizer.normalize(letras, Normalizer.Form.NFD)
                           .replaceAll("[^\\p{ASCII}]", "");
        letras = letras.toLowerCase();
        String letrasSobra = letras;

        // ordenando as letras
        char cs[] = letras.toCharArray();
        Arrays.sort(cs);
        letras = new String(cs);

        // para armazenar a resposta final
        Palavra palavraMontada = null;

// ---------------------- PROCURANDO PALAVRAS POSSÍVEIS --------------------- //

        for (Palavra p : palavras){
            // ordena os caracteres da palavra em ordem alfabética
            char chars[] = p.getPalavraNormalizada().toLowerCase().toCharArray();
            Arrays.sort(chars);
            String palavraOrdenada = new String(chars);

            // procura grupos de caracteres iguais, que agora estarão juntos
            Pattern pattern = Pattern.compile("(\\w)\\1*");
            Matcher matcher = pattern.matcher(palavraOrdenada);

            // se o grupo não for encontrado na lista de letras,
            // então não é possível formamr a palavra p
            boolean possivel = true;
            String parte = null;
            while (matcher.find() && possivel){
                parte = matcher.group();
                if (!letras.matches(".*" + parte + ".*")){
                    possivel = false;
                }
            }

// ----------------------------------- DESEMPATE ----------------------------- //
            if (possivel){
                if (palavraMontada == null){
                    palavraMontada = p;
                } else if (p.getPontos() > palavraMontada.getPontos()){
                    palavraMontada = p;
                } else if (p.getPontos() == palavraMontada.getPontos()
                           && p.getPalavra().length() < palavraMontada.getPalavra().length()){
                        palavraMontada = p;
                }
            }
        }

// --------------------------------- SAÍDA --------------------------------- //

        try {
            for (char c : palavraMontada.getPalavraNormalizada().toCharArray()){
                letrasSobra = letrasSobra.replaceFirst("(?i)" + c, "");
            }

            System.out.print(palavraMontada.getPalavra().toUpperCase());
            System.out.println(", palavra de " + palavraMontada.getPontos() + " pontos");
            System.out.println("Sobraram: " + letrasSobra.toUpperCase());
        }
        catch (NullPointerException e){
            System.out.println("Não foi possível montar palavra alguma :(");
        }
    }
}
