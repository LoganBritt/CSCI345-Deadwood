 // This program creates a customized version of the GUI for Deadwood
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GUIManager extends JFrame{
	JFrame frame;
	JLayeredPane layeredFrame;
	JLabel boardLabel;
	double scaleRatio = 1;
	ImageIcon boardImage = new ImageIcon("graphics/board.jpg");
	Image scaledBoard = boardImage.getImage().getScaledInstance((int) (boardImage.getIconWidth() *scaleRatio), (int) (boardImage.getIconHeight()*scaleRatio), Image.SCALE_SMOOTH);
	JLabel menuLabel;
	int buttCt = 4;
	JButton actButt;
	JButton reherButt;
	JButton moveButt;
	JButton endTButt;
	JLayeredPane neighborMenu;
	JLayeredPane mainMenu;

	JButton neighborButt1;
	JButton neighborButt2;
	JButton neighborButt3;
	JButton neighborButt4;

	JLabel[] cards = new JLabel[10];

	GUIManager(){
		BoardManager.createDeck();
		BoardManager.createBoard();

		String in = null;
		int playerNumberInput = -1;
		while ((playerNumberInput < 2 || playerNumberInput >8)){
			try {
				
				in = JOptionPane.showInputDialog("How many players? (2-8)");
				playerNumberInput = Integer.parseInt(in);
				
			} catch (NumberFormatException e) {

			}
			
		}

		GameManager.setPlayerAmt(playerNumberInput);
		GameManager.createPlayers();



		initScreen();
		initScreenAreas();
	}

	private void initScreen(){
		frame = initFrame();
	    layeredFrame = frame.getLayeredPane();
		boardImage.setImage(scaledBoard); 
		boardLabel = initBoard(layeredFrame);
		mainMenu = initMainMenu(layeredFrame);

		for(int i = 0; i < buttCt; i++){
			initButt(i, mainMenu);
		}

		neighborMenu = initNeighborMenu(layeredFrame);
		cards[] = initCards();
		System.out.println("Initialization Complete. Showing GUI\n");
	        frame.setVisible(true);
	}

	private JLabel[] initCards(){
		JLabel retCards = new JLabel[10];
		Board board = BoardManager.board;
		ArrayList<Space> spaceList = board.getSpaceList();

		for(int i = 0; i < spaceList.size(); i++){
			if(spaceList.get(i) instanceof Scene){
				Scene workingScene = (Scene) spaceList.get(i);
				Card workingCard = workingScene.getCard();
				retCards[i] = new JLabel();
				retCards[i].setIcon(workingCard.
			}
		}
	}

	private void initScreenAreas() {
		/*int[12][4] statSet = new int{
			{}
			{}
			{}
			{}
			{}
			{}
			{}
			{}
			{}
			{}
			{}
			{}
		}*/
		Board board = BoardManager.board;
		ArrayList<Space> spaceList = board.getSpaceList();
		for(int i = 0; i < spaceList.size(); i++){
			Space workingSpace = spaceList.get(i);
			if(workingSpace != null){
				//Create places for the players
				
				//Create places for the roles
				if(workingSpace instanceof Scene){
					Scene workingScene = (Scene) workingSpace;
					Role[] roleSet = workingScene.getRoles();
					for(int j = 0; j < roleSet.length; j++){
						if(roleSet[j] != null){
							//For each role on the scene
							int xPos = roleSet[j].getX();
							int yPos = roleSet[j].getY();
							int height = roleSet[j].getWidth();
							int width = roleSet[j].getHeight();
							JPanel rolePanel = new JPanel();
		    					rolePanel.setBounds(xPos, yPos, width, height);
		    					rolePanel.setOpaque(false); // starts transparent
		    					rolePanel.setBackground(Color.ORANGE);
		
		   					 // Add mouse hover effect
		    					rolePanel.addMouseListener(new MouseAdapter() {
		        					@Override
		        					public void mouseEntered(MouseEvent e) {
		            						rolePanel.setOpaque(true);
		            						rolePanel.repaint();
		       			 			}
		
		        					@Override
		        					public void mouseExited(MouseEvent e) {
		            						rolePanel.setOpaque(false);
		           						 rolePanel.repaint();
		       						}
		    					});
		
		    					// Add to layered pane
		    					layeredFrame.add(rolePanel, JLayeredPane.PALETTE_LAYER);
						}
					}
		
					Card workingCard = workingScene.getCard();
					JPanel cardPanel = new JPanel();
					cardPanel.setBounds(workingCard.getX(), workingCard.getY(), workingCard.getW(), workingCard.getH());
					cardPanel.setOpaque(false);
					cardPanel.setBackground(Color.BLUE);

					cardPanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
		            				cardPanel.setOpaque(true);
		            				cardPanel.repaint();
		       			 	}
		
		        			@Override
		        			public void mouseExited(MouseEvent e) {
		            				cardPanel.setOpaque(false);
		           				cardPanel.repaint();
		       				}
					});
					layeredFrame.add(cardPanel, JLayeredPane.PALETTE_LAYER);
					
					Role[] cardRoles = workingCard.getRoles();
					for(int j = 0; j < cardRoles.length; j++){
						//For each role on the card
						if(cardRoles[j] != null){
							int xPos = cardRoles[j].getX();
							int yPos = cardRoles[j].getY();
							int height = cardRoles[j].getWidth();
							int width = cardRoles[j].getHeight();
						}						
					}
				}
			}     
	        }
	}    


	private JLayeredPane initNeighborMenu(JLayeredPane basePane){
		int width = 220;
    		int height = 260;

    		int x = boardImage.getIconWidth() + 5;
    		int y = 5;

    		JLayeredPane pane = new JLayeredPane();
    		pane.setBounds(x, y, width, height);
    		pane.setBackground(Color.GRAY);
    		pane.setOpaque(true);
	
    		JLabel label = new JLabel("Move to which?");
    		label.setBounds(30, 10, 160, 30);
    		label.setForeground(Color.WHITE);
    		label.setFont(new Font("Palatino Linotype", Font.BOLD, 18));
    		pane.add(label, 1);

    		for(int i = 0; i < 4; i++){
    		    JButton moveOption = null;
		    if(i == 0){
			String buttName = "Neighbor " + (i+1);
			moveOption = new JButton(buttName);
			neighborButt1 = moveOption;
		   }else if(i == 1){
			String buttName = "Neighbor " + (i+1);
			moveOption = new JButton(buttName);
			neighborButt2 = moveOption;
		   }else if(i == 2){
			String buttName = "Neighbor " + (i+1);
			moveOption = new JButton(buttName);
			neighborButt3 = moveOption;
		   }else if(i == 3){
			String buttName = "Neighbor " + (i+1);
			moveOption = new JButton(buttName);
			neighborButt4 = moveOption;
		   }
		   moveOption.setBackground(Color.LIGHT_GRAY);
		   moveOption.setFocusPainted(false);
		   moveOption.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		   moveOption.setBounds(10, 50 + (i * 40), 200, 35);
   		   moveOption.addMouseListener(new boardMouseListener());
   		   pane.add(moveOption, 2);
    		}

    		JButton cancel = new JButton("Cancel");
		cancel.setBackground(Color.LIGHT_GRAY);
		cancel.setFocusPainted(false);
		cancel.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
    		cancel.setBounds(10, 210, 200, 35);
    		cancel.addMouseListener(new boardMouseListener());
    		pane.add(cancel, 2);
		
		pane.setVisible(false);
		
		basePane.add(pane, 3); 
		
    		return pane;
	}

	//Creates the main frame object for the GUI on initialization
	private JFrame initFrame(){
		System.out.println("Initializing main frame...");
		JFrame newFrame = new JFrame("Deadwood");
        	newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	newFrame.getContentPane().setBackground(Color.DARK_GRAY);
		newFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		newFrame.setIconImage(new ImageIcon("graphics/shot.png").getImage());
		System.out.println("Main frame initiailized\n");
		return newFrame;
	}

	//Creates the board object for the GUI on initialization
	private JLabel initBoard(JLayeredPane pane){
		
		int boardWidth = boardImage.getIconWidth();
		int boardHeight = boardImage.getIconHeight();
		int xPos = 0;
		int yPos = 0;

		System.out.println("Initializing board label");
		JLabel newBoardLabel = new JLabel();
		newBoardLabel.setIcon(boardImage);
		newBoardLabel.setBounds(xPos, yPos, boardWidth, boardHeight);
		pane.add(newBoardLabel, 0);
		System.out.println("Board label initialized\n");
		return newBoardLabel;
	}

	//Creates the menu object for the GUI on initialization
	private JLabel initMenu(JLayeredPane pane){
		int menuWidth = 200;
		int menuHeight = 40;
		int boardDist = 70;
		int topDist = 10;

		System.out.println("Initializing Button Menu");
		JLabel menu = new JLabel("Menu");
		menu.setBounds(boardImage.getIconWidth() + boardDist, topDist, menuWidth, menuHeight);
		menu.setForeground(Color.WHITE);
		menu.setFont(new Font("Palatino Linotype", Font.PLAIN, 30));
		pane.add(menu, 1);
		System.out.println("Button Menu Initialized\n");
		return menu;
	}

	//Creates a button object for the GUI on initialization
	//Idx is linked to what type of button it will be
	private void initButt(int idx, JLayeredPane pane){
		JButton tempButt;
		int buttWidth = 200;
		int buttHeight = 40;
		int boardDist = 10;
		int quanticY = 45;

		switch(idx){
			case 0:
				//Initializing Act Button
				System.out.println("Initializing Button 0: Act");
				tempButt = new JButton("Act");
				actButt = tempButt;
				break;
			case 1:
				//Initializing Rehearse Button
				System.out.println("Initializing Button 1: Rehearse");
				tempButt = new JButton("Rehearse");
				reherButt = tempButt;
				break;
			case 2:
				//Initializing Move Button
				System.out.println("Initializing Button 2: Move");
				tempButt = new JButton("Move");
				moveButt = tempButt;
				break;
			case 3:
				//Initializing End Turn Button
				System.out.println("Initializing Button 3: End Turn");
				tempButt = new JButton("End Turn");
				endTButt = tempButt;
				break;
			default:
				System.out.println("Tried implementing a button that does not exist: " + idx);
				return;
		}
		tempButt.setBackground(Color.LIGHT_GRAY);
		tempButt.setFocusPainted(false);
		tempButt.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		tempButt.setBounds(10, 50 + (idx * quanticY), buttWidth, buttHeight);
		tempButt.addMouseListener(new boardMouseListener());
		pane.add(tempButt, 2);
		System.out.println("Button " + idx + " Initialized\n");
	}

	private JLayeredPane initMainMenu(JLayeredPane basePane){

   		int width = 220;
    		int height = 260;

    		int x = boardImage.getIconWidth() + 5;
    		int y = 5;
		
	    	JLayeredPane pane = new JLayeredPane();
    		pane.setBounds(x, y, width, height);
    		pane.setBackground(Color.GRAY);
    		pane.setOpaque(true);

    		JLabel label = new JLabel("Menu");
    		label.setBounds(30, 10, 160, 30);
    		label.setForeground(Color.WHITE);
    		label.setFont(new Font("Palatino Linotype", Font.BOLD, 18));

    		pane.add(label, 1);
		
    		basePane.add(pane, 2);

    		return pane;
	}

	//Changes the text on the neighbor buttons to match the neighbors of the player's space
	public void updateNeighborOptions(){
		Player player = GameManager.getActivePlayer();
		Space currSpace = player.currLocation;
		Space[] neighbors = currSpace.neighborSpaces;
		for(int i = 0; i < neighbors.length; i++){
			String name = neighbors[i].name;
			switch(i){
				case 0:
					neighborButt1.setText(name);
					break;
				case 1:
					neighborButt2.setText(name);
					break;
				case 2:
					neighborButt3.setText(name);
					break;
				case 3:
					neighborButt4.setText(name);
					break;
				default:
					break;
			}
		}
		neighborButt4.setVisible(neighbors.length == 4);
	}

	//Mouse event handler for the GUI
	class boardMouseListener implements MouseListener{
		public void mouseClicked(MouseEvent e){
			if(e.getSource() == actButt){
				//This is everything that will happen when the player tries to act
				GameManager.getActivePlayer().act(true);
				System.out.println("Clicked Act Button");

			}else if(e.getSource() == reherButt){
				//This is everything that will happen when the player tries to rehearse
				System.out.println("Clicked Rehearse Button");
				if(!GameManager.getPlayerActed()){
					Player player = GameManager.getActivePlayer();
					System.out.println(player.rehearseTokens);
					player.rehearse();
					System.out.println(player.rehearseTokens);
					GameManager.makeActed();
				}				
			}else if(e.getSource() == moveButt){
				//This is everything that will happen when the player tries to move

				//Create a new menu to select the neighboring place the Player wants to move to
				//preinitialized and set to false, buttons for neighboring spaces need to be adjusted
				//Once the menu is up, it waits for one of the neighbor buttons to be clicked
				//change the layer of the main menu while this is up so only the one on top can be interacted with
				//that active player will need to be moved to said neighbor, player.move(Scene)
				//change visual

				//flip card if player is first on the scene, show card


				System.out.println("Clicked Move Button");
				if(!GameManager.getPlayerMoved()){
					updateNeighborOptions();
					mainMenu.setVisible(false);
					neighborMenu.setVisible(true);
					GameManager.makeMoved();
				}

			}else if(e.getSource() == endTButt){
				//This is everything that will happen when the player tries to end their turn
				System.out.println("Clicked End Turn Button");
				System.out.println(GameManager.getActvPlyrIdx());
				GameManager.changeTurn();
				System.out.println(GameManager.getActvPlyrIdx());

			} else if(e.getSource() == neighborButt1){
				System.out.println("Selected to move to the first neighbor");
				GameManager.getActivePlayer().move(neighborButt1.getText().toLowerCase());
				mainMenu.setVisible(true);
				neighborMenu.setVisible(false);

    			} else if(e.getSource() == neighborButt2){
				System.out.println("Selected to move to the second neighbor");
				GameManager.getActivePlayer().move(neighborButt2.getText().toLowerCase());
				mainMenu.setVisible(true);
				neighborMenu.setVisible(false);

    			} else if(e.getSource() == neighborButt3){
				System.out.println("Selected to move to the third neighbor");
				GameManager.getActivePlayer().move(neighborButt3.getText().toLowerCase());
				mainMenu.setVisible(true);
				neighborMenu.setVisible(false);

    			} else if(e.getSource() == neighborButt4){
				System.out.println("Selected to move to the fourth neighbor");
				GameManager.getActivePlayer().move(neighborButt4.getText().toLowerCase());
				mainMenu.setVisible(true);
				neighborMenu.setVisible(false);

    			} else if(e.getSource() instanceof JButton){
    				JButton b = (JButton) e.getSource();
	
    				if(b.getText().equals("Cancel")){
        				neighborMenu.setVisible(false);
					mainMenu.setVisible(true);
        				System.out.println("Move cancelled");
				}
			} else {
				//This is what happens when the player clicks a button without a purpose.
				//This should not happen
				System.out.println("Clicked <Redacted>");
			}
		}

		public void mousePressed(MouseEvent e){}

		public void mouseReleased(MouseEvent e){}

		public void mouseEntered(MouseEvent e){}

		public void mouseExited(MouseEvent e){}
	}
}
