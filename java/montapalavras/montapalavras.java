import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Palavra {
    private String palavra;
    private int pontos;

    Palavra(String palavra, int pontos){
        this.palavra = palavra;
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
                palavras.add(new Palavra(palavra, 0));
            }

            for (Palavra p : palavras){
                System.out.println(p.getPalavra() + ", " + p.getPontos());
            }
        } catch (FileNotFoundException e){
            System.out.println("Erro ao ler arquivo.");
            e.printStackTrace();
        }
    }
}
