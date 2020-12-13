import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Palavra {
    private String palavra;
    private int pontos;

    void setPalavra(String palavra){
        this->palavra = palavra;
    }

    void setPontos(int pontos){
        this-> pontos = pontos;
    }

    public String getPalavra(){
        return this->palavra;
    }

    public int getPontos(){
        return this->pontos;
    }
}

public class montapalavras {
    public static void main (String args[]){
        List<String> palavras = new ArrayList<String>();

        // lendo arquivo com banco de palavras
        try {
            File file = new File ("banco.txt");
            Scanner reader = new Scanner(file).useDelimiter(" ");
            while (reader.hasNextLine()){
                String palavra = reader.next();
                palavra = palavra.replace("\"", "");
                palavra = palavra.replace(",", "");
                palavras.add(palavra);
            }
            for (String p : palavras){
                System.out.println(p);
            }
        } catch (FileNotFoundException e){
            System.out.println("Erro ao ler arquivo.");
            e.printStackTrace();
        }
    }
}
