
//Imports for XML
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
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
        NodeList cards = root.getElementsByTagName("card");
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

    public void parseBoard(Board board) {
        Document doc = null;
        try {
            doc = getDocFromFile("board.xml");
        } catch (Exception e) {
            System.out.println("XML Parse Failure");
        }
        Element boardRoot = doc.getDocumentElement();
        NodeList sceneNodeList = boardRoot.getElementsByTagName("set");
	NodeList trailersNodeList = boardRoot.getElementsByTagName("trailers");
	NodeList castingNodeList = boardRoot.getElementsByTagName("office");
	ArrayList<Space> spaceList = board.getSpaceList();
	//Parse all the space info
	for(int i = 0; i < sceneNodeList.getLength() + 2; i++){
		//Getting to the right level of nodes
		Node spaceNode;
		if(i == sceneNodeList.getLength() - 2){
			spaceNode = trailersNodeList.item(0);
		}else if(i == sceneNodeList.getLength() - 1){
			spaceNode = castingNodeList.item(0);
		}else{
			spaceNode = sceneNodeList.item(i);
		}

		NodeList spaceChildNodes = spaceNode.getChildNodes();
		NodeList neighborNodes = spaceChildNodes.item(0).getChildNodes();
		Node areaNode = spaceChildNodes.item(1);

		//Getting the neighbor names
		String[] neighborNames = new String[4];
		int[] areaVals = new int[4];
		for(int j = 0; j < neighborNodes.getLength(); j++){
			neighborNames[j] = neighborNodes.item(j).getAttributes().getNamedItem("name").getNodeValue();
		}

		//Getting the size and positions
                areaVals[0] = Integer.parseInt(areaNode.getAttributes().getNamedItem("x").getNodeValue());
                areaVals[1] = Integer.parseInt(areaNode.getAttributes().getNamedItem("y").getNodeValue());
                areaVals[2] = Integer.parseInt(areaNode.getAttributes().getNamedItem("h").getNodeValue());
                areaVals[3] = Integer.parseInt(areaNode.getAttributes().getNamedItem("w").getNodeValue());

		spaceList.get(i).setVals(areaVals);
		spaceList.get(i).setNeighbors(neighborNames);

		//Extra non-trailer info
		if(i == sceneNodeList.getLength() - 1){
			//Parsing casting office specific info
			
		}else if(i != sceneNodeList.getLength() - 2){
			//Parsing scene specific info
			
		}
	}
    }

}
