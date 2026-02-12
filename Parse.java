
//Imports for XML
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
//import java.io.File;

public class Parse {

    public Document getDocFromFile(String filename)
            throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try {
            doc = db.parse(filename);
        } catch (Exception ex) {
            System.out.println("XML Parse Failure");
            ex.printStackTrace();
        }
        return doc;
    }

    // parseCard Method parses the cards and returns the values

    // Parts of a card:
    // Card - name, image, budget
    // Scene - number, subtext
    // part - name, level
    // area - x, y, h, w
    // line - 'text'

    public void parseCard(Deck deck) {
        Document d = null;
        try {
            d = getDocFromFile("cards.xml");
        } catch (Exception ex) {
            System.out.println("XML Parse Failure");
        }
        Element root = d.getDocumentElement();
        NodeList cards = root.getElementsByTagName("cards");
        for (int i = 0; i < cards.getLength(); i++) {
            Card workingCard = deck.getCardSet()[i];
            Node card = cards.item(i);
            String cardName = card.getAttributes().getNamedItem("name").getNodeValue();
            workingCard.setTitle(cardName);
            // System.out.println("Card Names: " + cardName);
            String img = card.getAttributes().getNamedItem("img").getNodeValue();
            // String.out.println("Card Image: " + img);
            workingCard.setBackground(img);
            int budget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());
            // String.out.println("Card Budget: " + budget);
            workingCard.setBudget(budget);
            NodeList children = card.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node sub = children.item(j);
                if ("number".equals(sub.getNodeName())) {
                    int num = Integer.parseInt(card.getAttributes().getNamedItem("number").getNodeValue());
                    // System.out.println("Scene number: " +num);
                    workingCard.setSceneNumber(num);
                    String subtext = sub.getTextContent();
                    // System.out.println("Scene subtext: " + subtext);
                    workingCard.setDesc(subtext);
                } else if ("name".equals(sub.getNodeName())) {
                    String name = card.getAttributes().getNamedItem("name").getNodeValue();
                    // System.out.println("Part Name: " + name);
                    workingCard.setRoleName(cardName);
                    int level = Integer.parseInt(card.getAttributes().getNamedItem("level").getNodeValue());
                    // System.out.println("Level number: " + level);
                    workingCard.setLevel(level);

                    for (int k = 1; k < children.getLength(); k++) {
                        Node subitem = children.item(k);
                        if ("x".equals(subitem.getNodeName())) {
                            int x = Integer.parseInt(card.getAttributes().getNamedItem("x").getNodeValue());
                            // System.out.println("X value: " + x);
                            workingCard.setX(x);
                            int y = Integer.parseInt(card.getAttributes().getNamedItem("y").getNodeValue());
                            // System.out.println("Y value: " + y);
                            workingCard.setY(y);
                            int h = Integer.parseInt(card.getAttributes().getNamedItem("h").getNodeValue());
                            // System.out.println("H value: " + h);
                            workingCard.setH(h);
                            int w = Integer.parseInt(card.getAttributes().getNamedItem("w").getNodeValue());
                            // System.out.println("H value: " + h);
                            workingCard.setW(w);
                            String subtext = subitem.getTextContent();
                            // System.out.println("Subtext: " + subtext);
                            workingCard.setRoleLine(subtext);
                        }
                    }
                }
            }
        }

    }

    // parseBoard method parses the board and returns the values
    public static Board parseBoard(Board board) {
        return board;
    }

}
