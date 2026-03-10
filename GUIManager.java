 // This program creates a customized version of the GUI for Deadwood
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GUIManager extends JFrame{
	JFrame frame;
	JLayeredPane layeredFrame;
	JLabel boardLabel;
	double scaleRatio = 0.85;
	ImageIcon boardImage = new ImageIcon("graphics/board.jpg");
	Image scaledBoard = boardImage.getImage().getScaledInstance((int) (boardImage.getIconWidth() *scaleRatio), (int) (boardImage.getIconHeight()*scaleRatio), Image.SCALE_SMOOTH);
	JLabel menuLabel;
	int buttCt = 4;
	JButton actButt;
	JButton reherButt;
	JButton moveButt;
	JButton endTButt;

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
	}

	private void initScreen(){
		frame = initFrame();
	    layeredFrame = frame.getLayeredPane();
		boardImage.setImage(scaledBoard); 
		boardLabel = initBoard(layeredFrame);
		menuLabel = initMenu(layeredFrame);

		for(int i = 0; i < buttCt; i++){
			initButt(i, layeredFrame);
		}

		System.out.println("Initialization Complete. Showing GUI\n");
	        frame.setVisible(true);
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
		int quanticY = 50;

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
		tempButt.setBounds(boardImage.getIconWidth() + boardDist, (idx+1) * quanticY, buttWidth, buttHeight);
		tempButt.addMouseListener(new boardMouseListener());
		pane.add(tempButt, 2);
		System.out.println("Button " + idx + " Initialized\n");
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
				Player player = GameManager.getActivePlayer();
				System.out.println(player.rehearseTokens);
				player.rehearse();
				System.out.println(player.rehearseTokens);
				
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
			}else if(e.getSource() == endTButt){
				//This is everything that will happen when the player tries to end their turn
				System.out.println("Clicked End Turn Button");
				System.out.println(GameManager.getActvPlyrIdx());
				GameManager.changeTurn();
				System.out.println(GameManager.getActvPlyrIdx());
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
