import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.*;
import java.text.Normalizer;
import java.util.Arrays;

class Palavra {
    private String palavra;
    private String palavraNormalizada;
    private int pontos;

    Palavra(String palavra){
        this.palavra = palavra;
        String palavraNormalizada = Normalizer.normalize(palavra, Normalizer.Form.NFD)
                                              .replaceAll("[^\\p{ASCII}]", "");
        this.palavraNormalizada = palavraNormalizada;

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

        // lendo arquivo com banco de palavras
        try {
            File file = new File ("banco.txt");
            Scanner reader = new Scanner(file).useDelimiter(" ");
            while (reader.hasNextLine()){
                String palavra = reader.next();
                palavra = palavra.replace("\"", "");
                palavra = palavra.replace(",", "");
                palavras.add(new Palavra(palavra));
            }
            //
            // for (Palavra p : palavras){
            //     System.out.println(p.getPalavra() + ", " + p.getPontos());
            // }
        } catch (FileNotFoundException e){
            System.out.println("Erro ao ler arquivo com banco de palavras.");
            e.printStackTrace();
        }

        // lendo a sequência de letras
        Scanner s = new Scanner(System.in);
        String letras = s.nextLine();
        letras = Normalizer.normalize(letras, Normalizer.Form.NFD)
                           .replaceAll("[^\\p{ASCII}]", "");
        letras = letras.toLowerCase();
        System.out.println(letras);

        List<Palavra> palavrasPossiveis = new ArrayList<Palavra>();

        for (Palavra p : palavras){
            //ordena os caracteres em ordem alfabética
            char chars[] = p.getPalavraNormalizada().toLowerCase().toCharArray();
            Arrays.sort(chars);
            String palavraOrdenada = new String(chars);

            //procura grupos de caracteres iguais
            Pattern pattern = Pattern.compile("(\\w)\\1*");
            Matcher matcher = pattern.matcher(palavraOrdenada);
            // System.out.println(p.getPalavra() + ", " + p.getPontos());
            // System.out.println(p.getPalavraNormalizada());
            // System.out.println(palavraOrdenada);

            boolean possivel = true;
            while (matcher.find() && possivel){
                String parte = matcher.group();
                if (!letras.matches(".*" + parte + ".*")){
                    possivel = false;
                }
            }
            if (possivel){
                palavrasPossiveis.add(p);
            }
        }

        for (Palavra p : palavrasPossiveis){
            System.out.println(p.getPalavra());
        }
    }
}
