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

    public static Document getDocFromFile(String filename)
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

    public static void parseCard(Deck deck) {
        Document d = null;
        try {
            d = getDocFromFile("cards.xml");
        } catch (Exception ex) {
            System.out.println("XML Parse Failure");
        }
        Element root = d.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");
        for (int i = 0; i < cards.getLength(); i++) {
            Card[] cardSet = deck.getCardSet();
	    Card workingCard = cardSet[i];
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

    public static void parseBoard(Board board) {
        Document doc = null;
        try {
            doc = getDocFromFile("board.xml");
        } catch (Exception e) {
            System.out.println("XML Parse Failure");
        }
        Element boardRoot = doc.getDocumentElement();
        NodeList sceneNodeList = boardRoot.getElementsByTagName("set");
	NodeList trailersNodeList = boardRoot.getElementsByTagName("trailer");
	NodeList castingNodeList = boardRoot.getElementsByTagName("office");
	ArrayList<Space> spaceList = board.getSpaceList();
	Space spaceObj = null;

	//Parsing names and creating spaces
	for(int i = 0; i < sceneNodeList.getLength() + 2; i++){
		if(i == 0){
//			System.out.println("Creating the trailers");
			spaceObj = new Trailers("trailer");
		}else if(i == sceneNodeList.getLength() + 1){
//			System.out.println("Creating the casting office");
			spaceObj = new Casting("office");
		}else{
			String sceneName = sceneNodeList.item(i-1).getAttributes().getNamedItem("name").getNodeValue();
//			System.out.println("Creating Scene: " + sceneName);
			spaceObj = new Scene(sceneName);
		}
		spaceList.add(spaceObj);
	}

	//Parse all the space info
	for(int i = 0; i < sceneNodeList.getLength() + 2; i++){
		//Getting to the right level of nodes
		Node spaceNode;
		spaceObj = spaceList.get(i);
		if(i == 0){
//			System.out.println("Setting trailers");
			spaceNode = trailersNodeList.item(0);
		}else if(i == sceneNodeList.getLength()+1){
//			System.out.println("Setting Casting");
			spaceNode = castingNodeList.item(0);
		}else{
//			System.out.println("Setting scene");
			spaceNode = sceneNodeList.item(i-1);
		}

		NodeList spaceChildren = spaceNode.getChildNodes();
		NodeList neighborNodes = spaceChildren.item(1).getChildNodes();
		Node areaNode = spaceChildren.item(3);

		//Getting the neighbor names
		String[] neighborNames = new String[(neighborNodes.getLength()-1)/2];
		int[] areaVals = new int[4];
		for(int j = 0; j < neighborNodes.getLength(); j++){
			if(neighborNodes.item(j).getNodeName().equals("neighbor")){
				String neighborName =  neighborNodes.item(j).getAttributes().getNamedItem("name").getNodeValue();
				neighborNames[(j-1)/2] = neighborName;
			}
		}

		//Getting the size and positions
                areaVals[0] = Integer.parseInt(areaNode.getAttributes().getNamedItem("x").getNodeValue());
                areaVals[1] = Integer.parseInt(areaNode.getAttributes().getNamedItem("y").getNodeValue());
                areaVals[2] = Integer.parseInt(areaNode.getAttributes().getNamedItem("h").getNodeValue());
                areaVals[3] = Integer.parseInt(areaNode.getAttributes().getNamedItem("w").getNodeValue());

		spaceObj.setVals(areaVals);
		spaceObj.setNeighbors(neighborNames);
		//Extra non-trailer info
		if(i == sceneNodeList.getLength() + 1){
			//Parsing casting office specific info
			parseCasting((Casting) spaceObj, spaceNode);
		}else if(i != 0){
			//Parsing scene specific info
			parseScene((Scene) spaceObj, spaceNode);
		}
		//System.out.println();
	}
	//BoardManager.cleanSpaces();
	//BoardManager.printAllSpaces();
    }

	public static void parseCasting(Casting casting, Node castingNode){
		Node upgradesNode = castingNode.getChildNodes().item(5);
		for(int i = 1; i < upgradesNode.getChildNodes().getLength(); i+=2){
			Node upgrade = upgradesNode.getChildNodes().item(i);
			int level = Integer.parseInt(upgrade.getAttributes().getNamedItem("level").getNodeValue());
			String currency = upgrade.getAttributes().getNamedItem("currency").getNodeValue();
			int amt = Integer.parseInt(upgrade.getAttributes().getNamedItem("amt").getNodeValue());
			casting.setUpgrade(level, currency, amt);

			int[] areaVals = new int[4];
			Node areaNode = upgrade.getChildNodes().item(1);
			areaVals[0] = Integer.parseInt(areaNode.getAttributes().getNamedItem("x").getNodeValue());
			areaVals[1] = Integer.parseInt(areaNode.getAttributes().getNamedItem("y").getNodeValue());
			areaVals[2] = Integer.parseInt(areaNode.getAttributes().getNamedItem("h").getNodeValue());
			areaVals[3] = Integer.parseInt(areaNode.getAttributes().getNamedItem("w").getNodeValue());
			casting.setArea(areaVals);
		}
	}

	public static void parseScene(Scene scene, Node sceneNode){
//		System.out.println("Setting scene-specific stuff for " + scene.name);
		NodeList sceneChildren = sceneNode.getChildNodes();
		Node takesNode = sceneChildren.item(5);
		Node partsNode = sceneChildren.item(7);

		//Setting takes values
		NodeList takesChildren = takesNode.getChildNodes();
		for(int i = 0; i < takesChildren.getLength(); i++){
			Node take = takesChildren.item(i);
			if(take.getNodeName().equals("take")){
				int number = Integer.parseInt(take.getAttributes().getNamedItem("number").getNodeValue());
				int[] areaVals = new int[4];
				Node areaNode = take.getChildNodes().item(0);
                        	areaVals[0] = Integer.parseInt(areaNode.getAttributes().getNamedItem("x").getNodeValue());
                        	areaVals[1] = Integer.parseInt(areaNode.getAttributes().getNamedItem("y").getNodeValue());
                        	areaVals[2] = Integer.parseInt(areaNode.getAttributes().getNamedItem("h").getNodeValue());
                        	areaVals[3] = Integer.parseInt(areaNode.getAttributes().getNamedItem("w").getNodeValue());
				scene.setTakeArea(number, areaVals);
			}
		}

		//Setting parts values
		NodeList partsChildren = partsNode.getChildNodes();
		scene.createRoleLists(partsChildren.getLength());
		Role[] roleList = scene.getUntakenRoles();
		for(int i = 0; i < partsChildren.getLength(); i++){
			Node part = partsChildren.item(i);
			if(part.getNodeName().equals("part")){
				String partName = part.getAttributes().getNamedItem("name").getNodeValue();
				int level = Integer.parseInt(part.getAttributes().getNamedItem("level").getNodeValue());

				NodeList partChildren = part.getChildNodes();
				Node areaNode = partChildren.item(1);
				Node lineNode = partChildren.item(3);

				String line = lineNode.getTextContent();
				int[] areaVals = new int[4];
				areaVals[0] = Integer.parseInt(areaNode.getAttributes().getNamedItem("x").getNodeValue());
				areaVals[1] = Integer.parseInt(areaNode.getAttributes().getNamedItem("y").getNodeValue());
				areaVals[2] = Integer.parseInt(areaNode.getAttributes().getNamedItem("h").getNodeValue());
				areaVals[3] = Integer.parseInt(areaNode.getAttributes().getNamedItem("w").getNodeValue());
				roleList[i] = new Role(level, partName, line, areaVals);
			}
		}
	}

    public static void printNodeList(NodeList in){
        for(int i = 0; i < in.getLength(); i++){
                System.out.println("Child " + (i+1) + ": " + in.item(i).getNodeName());
        }
    }
}


