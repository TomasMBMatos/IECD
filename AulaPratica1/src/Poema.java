import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Poema {

    private Document doc;
    private Element root;

    public Poema(String filePath) {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(file);
            this.root = doc.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void apresentaPoema() {
        NodeList estrofes = root.getElementsByTagName("estrofe");
        for (int i = 0; i < estrofes.getLength(); i++) {
            NodeList versos = ((Element) estrofes.item(i)).getElementsByTagName("verso");
            for (int j = 0; j < versos.getLength(); j++) {
                System.out.println(versos.item(j).getTextContent());
            }
            System.out.println();
        }
    }

    public void classificaEstrofes() {
        NodeList estrofes = root.getElementsByTagName("estrofe");
        for (int i = 0; i < estrofes.getLength(); i++) {
            NodeList versos = ((Element) estrofes.item(i)).getElementsByTagName("verso");
            System.out.println("Estrofe " + (i+1) + " tem " + versos.getLength() + " versos.");
        }
    }

    public void adicionaVerso(int numEstrofe, String texto) {
        Element estrofe = (Element) root.getElementsByTagName("estrofe").item(numEstrofe-1);
        Element verso = doc.createElement("verso");
        verso.setTextContent(texto);
        estrofe.appendChild(verso);
    }

    public void removeEstrofe(int numEstrofe) {
        Element estrofe = (Element) root.getElementsByTagName("estrofe").item(numEstrofe-1);
        root.removeChild(estrofe);
    }

    public void buscaVersoPorPalavra(String palavra) {
        NodeList estrofes = root.getElementsByTagName("estrofe");
        for (int i = 0; i < estrofes.getLength(); i++) {
            NodeList versos = ((Element) estrofes.item(i)).getElementsByTagName("verso");
            for (int j = 0; j < versos.getLength(); j++) {
                String texto = versos.item(j).getTextContent();
                if (texto.contains(palavra)) {
                    System.out.println(texto);
                    System.out.println();
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Poema poema = new Poema("poema.xml");

        while(true) {
            System.out.println("1-Apresente o poema na sua forma escrita clássica.");
            System.out.println("2-Classifique as estrofes quanto ao número de versos.");
            System.out.println("3-Acrescente um verso no fim de um estrofe.");
            System.out.println("4-Remova uma determinada estrofe.");
            System.out.println("5-Indique os versos que contêm uma determinada palavra.");
            int in = Integer.parseInt(sc.nextLine());
            switch(in) {
                case 1:
                    poema.apresentaPoema();
                    break;
                case 2:
                    poema.classificaEstrofes();
                    break;
                case 3:
                    System.out.println("Escolha uma estrofe.");
                    int estrofe1 = Integer.parseInt(sc.nextLine());
                    System.out.println("Introduza o verso.");
                    String verso = sc.nextLine();
                    poema.adicionaVerso(estrofe1, verso);
                    poema.apresentaPoema();
                    break;
                case 4:
                    System.out.println("Escolha uma estrofe.");
                    int estrofe2 = Integer.parseInt(sc.nextLine());
                    poema.removeEstrofe(estrofe2);
                    poema.apresentaPoema();
                    break;
                case 5:
                    System.out.println("Indique a palavra");
                    String palavra = sc.nextLine();
                    poema.buscaVersoPorPalavra(palavra);
                    //poema.apresentaPoema();
                    break;
            }
        }
    }
}
