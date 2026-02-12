import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Parse {

    public Document getDocFromFile(String filename) 
    throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try{
            doc = db.parse(filename);
        }
        catch (Exception ex){
            System.out.println("XML Parse Failure");
            ex.printStackTrace();
        }
        return doc;
    }

    // parseCard Method parses the cards and returns the values
    public void parseCard(Deck deck){
        Document d = null;
        try{
        d = getDocFromFile("cards.xml");
        }
        catch (Exception ex){
            System.out.println("XML Parse Failure");
        }
        Element root = d.getDocumentElement();
        NodeList cards = root.getElementsByTagName("cards");
        for (int i = 0; i < cards.getLength(); i++){
            Node card = cards.item(i);
            String cardName = card.getAttributes().getNamedItem("name").getNodeValue();
            System.out.println("Card Names: (please work!)" + cardName);
        }
        
    }

    // parseBoard method parses the board and returns the values
    public static Board parseBoard(Board board){
        return board;
    }




}
