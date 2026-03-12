
// This program creates a customized version of the GUI for Deadwood
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GUIManager extends JFrame {
	JFrame frame;
	JLayeredPane layeredFrame;
	JLabel boardLabel;
	double scaleRatio = 0.85;
	ImageIcon boardImage = new ImageIcon("graphics/board.jpg");
	Image scaledBoard = boardImage.getImage().getScaledInstance((int) (boardImage.getIconWidth() * scaleRatio),
			(int) (boardImage.getIconHeight() * scaleRatio), Image.SCALE_SMOOTH);
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

	JLayeredPane statsMenu;
	JLabel statsTitle;
	JLabel rankLabel;
	JLabel dollarLabel;
	JLabel creditsLabel;
	JLabel rehearseTokensLabel;
	JLabel locationLabel;
	JLabel roleLabel;

	// JLabel[] cards = new JLabel[10];
	Hashtable<String, JLabel> spaces = new Hashtable<>();
	Hashtable<String, JLabel> cards = new Hashtable<>();
	Hashtable<String, JLabel[]> shots = new Hashtable<>();
	JLabel[] players;
	String[] nameSet = new String[] {
			"train station",
			"secret hideout",
			"church",
			"hotel",
			"main street",
			"jail",
			"general store",
			"ranch",
			"bank",
			"saloon",
			"trailer",
			"office"
	};

	String[] playerDiceOrder = new String[] {
			"b",
			"c",
			"g",
			"o",
			"p",
			"r",
			"v",
			"w",
			"y"
	};

	// Default constructor that creates the deck and the board.
	// Takes user input to determine number of players
	// creates the players and initializes the board and the screen areas
	GUIManager() {
		BoardManager.createDeck();
		BoardManager.createBoard();

		String in = null;
		int playerNumberInput = -1;
		// gets user input for the number of players -- try catch to make sure it's a
		// valid number
		while ((playerNumberInput < 2 || playerNumberInput > 8)) {
			try {
				in = JOptionPane.showInputDialog("How many players? (2-8)");
				playerNumberInput = Integer.parseInt(in);
			} catch (NumberFormatException e) {

			}

		}

		players = new JLabel[playerNumberInput];
		GameManager.setPlayerAmt(playerNumberInput);
		GameManager.createPlayers();

		initScreen();
		initScreenAreas();
		frame.setVisible(true);
	}

	// Initilizes the Screen
	private void initScreen() {
		frame = initFrame();
		layeredFrame = frame.getLayeredPane();
		boardImage.setImage(scaledBoard);
		boardLabel = initBoard(layeredFrame);
		mainMenu = initMainMenu(layeredFrame);
		statsMenu = initStatsMenu(layeredFrame);

		// Creates the needed button number for the Main Menu
		for (int i = 0; i < buttCt; i++) {
			initButt(i, mainMenu);
		}

		neighborMenu = initNeighborMenu(layeredFrame);
		cards = initCards();
		shots = initShots();
		System.out.println("Initialization Complete. Showing GUI\n");
	}

	// initilizes the cards and their roles
	private Hashtable<String, JLabel> initCards() {
		Hashtable<String, JLabel> retTable = new Hashtable<>();
		Board board = BoardManager.board;
		ArrayList<Space> spaceList = board.getSpaceList();
		int idx = 0;
		// Assigns a card for every space
		for (int i = 0; i < spaceList.size(); i++) {
			if (spaceList.get(i) instanceof Scene) {
				Space workingSpace = spaceList.get(i);
				Scene workingScene = (Scene) workingSpace;
				Card workingCard = workingScene.getCard();
				JLabel retLabel = new JLabel();
				System.out.println("Space: " + workingSpace.name);
				// retCards[idx].setIcon(new ImageIcon("graphics/Card/" +
				// workingCard.getBackground()));
				retLabel.setIcon(new ImageIcon("graphics/cardback.png"));
				ImageIcon icon = (ImageIcon) retLabel.getIcon();
				Image scaledIcon = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * scaleRatio),
						(int) (icon.getIconHeight() * scaleRatio), Image.SCALE_SMOOTH);
				retLabel.setIcon(new ImageIcon(scaledIcon));
				retLabel.setBounds((int) (workingSpace.getX() * scaleRatio),
						(int) (workingSpace.getY() * scaleRatio), (int) (workingSpace.getW() * scaleRatio),
						(int) (workingSpace.getH() * scaleRatio));
				retLabel.setOpaque(true);
				retLabel.setVisible(true);
				layeredFrame.add(retLabel, JLayeredPane.PALETTE_LAYER);
				retTable.put(nameSet[idx], retLabel);
				idx++;
			}
		}
		layeredFrame.revalidate();
		layeredFrame.repaint();
		return retTable;
	}

	// initilizes the shot counter sprites
	private Hashtable<String, JLabel[]> initShots() {
		Hashtable<String, JLabel[]> shotTable = new Hashtable<>();
		Board board = BoardManager.board;
		ArrayList<Space> spaceList = board.getSpaceList();
		int index = 0;
		for (int i = 0; i < spaceList.size(); i++) {
			if (spaceList.get(i) instanceof Scene) {
				Space workingSpace = spaceList.get(i);
				Scene workingScene = (Scene) workingSpace;

				int shotLoc[][] = workingScene.getShotLocation();
				JLabel arr[] = new JLabel[4];

				for (int j = 0; j < shotLoc.length; j++) {
					int x = shotLoc[j][0];
					int y = shotLoc[j][1];
					int h = shotLoc[j][2];
					int w = shotLoc[j][3];

					JLabel shot = new JLabel();
					shot.setIcon(new ImageIcon("graphics/shot.png"));
					ImageIcon icon = (ImageIcon) shot.getIcon();
					Image scaled = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * scaleRatio),
							(int) (icon.getIconHeight() * scaleRatio), Image.SCALE_SMOOTH);
					shot.setIcon(new ImageIcon(scaled));
					shot.setBounds((int) (x * scaleRatio), (int) (y * scaleRatio), (int) (w * scaleRatio),
							(int) (h * scaleRatio));
					shot.setVisible(true);
					layeredFrame.add(shot, JLayeredPane.PALETTE_LAYER);
					arr[j] = shot;

				}
				shotTable.put(nameSet[index], arr);
				index ++;
			}
		}
		layeredFrame.revalidate();
		layeredFrame.repaint();
		return shotTable;
	}

	// Initilizes the players and their Sprites
	private JLabel[] initPlayers() {
		Player[] players = GameManager.getPlayerList();
		JLabel[] retLabel = new JLabel[players.length];

		// Creates the needed player Sprites
		for (int i = 0; i < players.length; i++) {
			System.out.println("Making new player sprite");
			JLabel start = spaces.get("trailer");
			JLabel player = new JLabel();
			System.out.println("graphics/Dice/" + playerDiceOrder[i] + players[i].rank + ".png");
			ImageIcon newImage = new ImageIcon("graphics/Dice/" + playerDiceOrder[i] + players[i].rank + ".png");
			Image scaledImage = newImage.getImage().getScaledInstance((int) (newImage.getIconWidth() * scaleRatio),
					(int) (newImage.getIconHeight() * scaleRatio), Image.SCALE_SMOOTH);
			player.setIcon(new ImageIcon(scaledImage));
			player.setBounds(start.getBounds());
			player.setOpaque(true);
			player.setVisible(true);
			layeredFrame.add(player, JLayeredPane.PALETTE_LAYER);
			retLabel[i] = player;
		}
		return retLabel;
	}

	// initializes the screen areas
	private void initScreenAreas() {
		int size = (int) (40 * scaleRatio);
		int[][] statSet = new int[][] {
				{ (int) (20 * scaleRatio), (int) (210 * scaleRatio), size, size }, // Train Station
				{ (int) (300 * scaleRatio), (int) (830 * scaleRatio), size, size }, // Secret Hideout
				{ (int) (755 * scaleRatio), (int) (670 * scaleRatio), size, size }, // Church
				{ (int) (1110 * scaleRatio), (int) (630 * scaleRatio), size, size }, // Hotel
				{ (int) (1100 * scaleRatio), (int) (180 * scaleRatio), size, size }, // Main Street
				{ (int) (285 * scaleRatio), (int) (150 * scaleRatio), size, size }, // Jail
				{ (int) (300 * scaleRatio), (int) (380 * scaleRatio), size, size }, // General Store
				{ (int) (285 * scaleRatio), (int) (635 * scaleRatio), size, size }, // Ranch
				{ (int) (845 * scaleRatio), (int) (490 * scaleRatio), size, size }, // Bank
				{ (int) (730 * scaleRatio), (int) (220 * scaleRatio), size, size }, // Saloon
				{ (int) (1070 * scaleRatio), (int) (325 * scaleRatio), size, size }, // Trailers
				{ (int) (80 * scaleRatio), (int) (470 * scaleRatio), size, size } // Casting Office
		};
		Board board = BoardManager.board;
		ArrayList<Space> spaceList = board.getSpaceList();

		// Creates places for Players in each space
		for (int i = 0; i < spaceList.size(); i++) {
			Space workingSpace = spaceList.get(i);
			if (workingSpace != null) {
				// Create places for the players
				JLabel playerSpace = new JLabel();
				playerSpace.setBounds(statSet[i][0], statSet[i][1], statSet[i][2], statSet[i][3]);
				playerSpace.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						System.out.println("Entered player space");
						playerSpace.repaint();
					}

					@Override
					public void mouseExited(MouseEvent e) {
						System.out.println("Exited player space");
						playerSpace.repaint();
					}
				});

				layeredFrame.add(playerSpace, JLayeredPane.PALETTE_LAYER);
				spaces.put(nameSet[i], playerSpace);

				// Create places for the roles
				if (workingSpace instanceof Scene) {
					Scene workingScene = (Scene) workingSpace;
					Role[] roleSet = workingScene.getRoles();
					for (int j = 0; j < roleSet.length; j++) {
						if (roleSet[j] != null) {
							// For each role on the scene
							int xPos = roleSet[j].getX();
							int yPos = roleSet[j].getY();
							int height = roleSet[j].getWidth();
							int width = roleSet[j].getHeight();
							JPanel rolePanel = new JPanel();
							rolePanel.setBounds((int) (xPos * scaleRatio), (int) (yPos * scaleRatio),
									(int) (width * scaleRatio), (int) (height * scaleRatio));
							rolePanel.setOpaque(false); // starts transparent
							rolePanel.setBackground(Color.ORANGE);

							// Add mouse hover effect
							rolePanel.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseEntered(MouseEvent e) {
									System.out.println("Entered role");
									rolePanel.setOpaque(true);
									rolePanel.repaint();
								}

								@Override
								public void mouseExited(MouseEvent e) {
									System.out.println("Exited role");
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
					cardPanel.setBounds((int) (workingScene.getX() * scaleRatio),
							(int) (workingScene.getY() * scaleRatio), (int) (workingScene.getW() * scaleRatio),
							(int) (workingScene.getH() * scaleRatio));
					cardPanel.setOpaque(false);
					cardPanel.setBackground(Color.BLUE);

					cardPanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							System.out.println("Entered card");
							cardPanel.setOpaque(true);
							cardPanel.repaint();
						}

						@Override
						public void mouseExited(MouseEvent e) {
							System.out.println("Exited card");
							cardPanel.setOpaque(false);
							cardPanel.repaint();
						}
					});
					layeredFrame.add(cardPanel, JLayeredPane.PALETTE_LAYER + 10);

					Role[] cardRoles = workingCard.getRoles();
					for (int j = 0; j < cardRoles.length; j++) {
						// For each role on the card
						if (cardRoles[j] != null) {
							int xPos = workingScene.getX() + cardRoles[j].getX();
							int yPos = workingScene.getY() + cardRoles[j].getY();
							int width = cardRoles[j].getWidth();
							int height = cardRoles[j].getHeight();

							JPanel cardRole = new JPanel();
							cardRole.setBounds((int) (xPos * scaleRatio), (int) (yPos * scaleRatio),
									(int) (width * scaleRatio), (int) (height * scaleRatio));
							// For Bounds testing --
							// cardRole.setBorder(BorderFactory.createLineBorder(Color.RED));
							cardRole.setOpaque(false);
							cardRole.setBackground(Color.ORANGE);

							cardRole.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseEntered(MouseEvent e) {
									System.out.println("Entered cardRole");
									cardRole.setOpaque(true);
									cardRole.repaint();
								}

								@Override
								public void mouseExited(MouseEvent e) {
									System.out.println("Exited cardRole");
									cardRole.setOpaque(false);
									cardRole.repaint();
								}

							});
							layeredFrame.add(cardRole, JLayeredPane.PALETTE_LAYER + 10, 0);
						}
					}
				}
			}
		}
		players = initPlayers();
		playerStats();
	}

	private JLayeredPane initStatsMenu(JLayeredPane basePane) {
		int width = 220;
		int height = 220;
		int x = boardImage.getIconWidth() + 5;
		int y = 280;

		JLayeredPane pane = new JLayeredPane();
		pane.setBounds(x, y, width, height);
		pane.setBackground(Color.GRAY);
		pane.setOpaque(true);

		// Title creation
		statsTitle = new JLabel();
		statsTitle.setBounds(50, 10, 160, 30);
		statsTitle.setForeground(Color.WHITE);
		statsTitle.setFont(new Font("Palatino Linotype", Font.BOLD, 18));
		pane.add(statsTitle, 1);

		// Rank
		rankLabel = new JLabel();
		rankLabel.setBounds(20, 50, 200, 20);
		pane.add(rankLabel, 1);

		// Dollars and Credits
		dollarLabel = new JLabel();
		dollarLabel.setBounds(20, 75, 200, 20);
		pane.add(dollarLabel, 1);

		creditsLabel = new JLabel();
		creditsLabel.setBounds(20, 100, 200, 20);
		pane.add(creditsLabel, 1);

		// Rehearsal Tokens
		rehearseTokensLabel = new JLabel();
		rehearseTokensLabel.setBounds(20, 125, 200, 20);
		pane.add(rehearseTokensLabel, 1);

		// Location and Role
		locationLabel = new JLabel();
		locationLabel.setBounds(20, 150, 200, 20);
		pane.add(locationLabel, 1);

		roleLabel = new JLabel();
		roleLabel.setBounds(20, 175, 200, 20);
		pane.add(roleLabel, 1);

		basePane.add(pane, 2);
		return pane;
	}

	// this method sets the information for the stats menu
	public void playerStats() {
		Player p = GameManager.getActivePlayer();
		statsTitle.setText("Player " + (GameManager.getActvPlyrIdx() + 1) + " Stats");
		rankLabel.setText("Rank: " + p.rank);
		dollarLabel.setText("Dollars: " + p.dollars);
		creditsLabel.setText("Credits: " + p.credits);
		rehearseTokensLabel.setText("Rehearsal Tokens: " + p.rehearseTokens);
		locationLabel.setText("Current Location: " + p.currLocation.name);

		if (p.currRole != null) {
			roleLabel.setText("Role: " + p.currRole);
		} else {
			roleLabel.setText("Role: Not Working");
		}
	}

	// initilizes the Neighbor Menu
	private JLayeredPane initNeighborMenu(JLayeredPane basePane) {
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

		for (int i = 0; i < 4; i++) {
			JButton moveOption = null;
			if (i == 0) {
				String buttName = "Neighbor " + (i + 1);
				moveOption = new JButton(buttName);
				neighborButt1 = moveOption;
			} else if (i == 1) {
				String buttName = "Neighbor " + (i + 1);
				moveOption = new JButton(buttName);
				neighborButt2 = moveOption;
			} else if (i == 2) {
				String buttName = "Neighbor " + (i + 1);
				moveOption = new JButton(buttName);
				neighborButt3 = moveOption;
			} else if (i == 3) {
				String buttName = "Neighbor " + (i + 1);
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

	// Creates the main frame object for the GUI on initialization
	private JFrame initFrame() {
		System.out.println("Initializing main frame...");
		JFrame newFrame = new JFrame("Deadwood");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.getContentPane().setBackground(Color.DARK_GRAY);
		newFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		newFrame.setIconImage(new ImageIcon("graphics/shot.png").getImage());
		System.out.println("Main frame initiailized\n");

		return newFrame;
	}

	// Creates the board object for the GUI on initialization
	private JLabel initBoard(JLayeredPane pane) {
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

	// Creates a button object for the GUI on initialization
	// Idx is linked to what type of button it will be
	private void initButt(int idx, JLayeredPane pane) {
		JButton tempButt;
		int buttWidth = 200;
		int buttHeight = 40;
		int boardDist = 10;
		int quanticY = 45;

		switch (idx) {
			case 0:
				// Initializing Act Button
				System.out.println("Initializing Button 0: Act");
				tempButt = new JButton("Act");
				actButt = tempButt;
				break;
			case 1:
				// Initializing Rehearse Button
				System.out.println("Initializing Button 1: Rehearse");
				tempButt = new JButton("Rehearse");
				reherButt = tempButt;
				break;
			case 2:
				// Initializing Move Button
				System.out.println("Initializing Button 2: Move");
				tempButt = new JButton("Move");
				moveButt = tempButt;
				break;
			case 3:
				// Initializing End Turn Button
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

	private JLayeredPane initMainMenu(JLayeredPane basePane) {

		int width = 220;
		int height = 260;

		int x = boardImage.getIconWidth() + 5;
		int y = 5;

		JLayeredPane pane = new JLayeredPane();
		pane.setBounds(x, y, width, height);
		pane.setBackground(Color.GRAY);
		pane.setOpaque(true);

		JLabel label = new JLabel("Menu: Player 1");
		label.setBounds(30, 10, 160, 30);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Palatino Linotype", Font.BOLD, 18));
		menuLabel = label;
		pane.add(label, 1);

		basePane.add(pane, 2);

		return pane;
	}

	// Changes the text on the neighbor buttons to match the neighbors of the
	// player's space
	public void updateNeighborOptions() {
		Player player = GameManager.getActivePlayer();
		Space currSpace = player.currLocation;
		Space[] neighbors = currSpace.neighborSpaces;
		for (int i = 0; i < neighbors.length; i++) {
			String name = neighbors[i].name;
			switch (i) {
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

	// Mouse event handler for the GUI
	class boardMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == actButt) {
				// This is everything that will happen when the player tries to act
				GameManager.getActivePlayer().act(true);
				System.out.println("Clicked Act Button");
				playerStats();

			} else if (e.getSource() == reherButt) {
				// This is everything that will happen when the player tries to rehearse
				System.out.println("Clicked Rehearse Button");
				if (!GameManager.getPlayerActed()) {
					Player player = GameManager.getActivePlayer();
					System.out.println(player.rehearseTokens);
					player.rehearse();
					System.out.println(player.rehearseTokens);
					GameManager.makeActed();
					playerStats();
				}
			} else if (e.getSource() == moveButt) {
				// This is everything that will happen when the player tries to move

				// Create a new menu to select the neighboring place the Player wants to move to
				// preinitialized and set to false, buttons for neighboring spaces need to be
				// adjusted
				// Once the menu is up, it waits for one of the neighbor buttons to be clicked
				// change the layer of the main menu while this is up so only the one on top can
				// be interacted with
				// that active player will need to be moved to said neighbor, player.move(Scene)
				// change visual

				// flip card if player is first on the scene, show card

				System.out.println("Clicked Move Button");
				if (!GameManager.getPlayerMoved()) {
					updateNeighborOptions();
					mainMenu.setVisible(false);
					neighborMenu.setVisible(true);
					playerStats();

				}
				playerStats();

			} else if (e.getSource() == endTButt) {
				// This is everything that will happen when the player tries to end their turn
				System.out.println("Clicked End Turn Button");
				System.out.println(GameManager.getActvPlyrIdx());
				GameManager.changeTurn();
				menuLabel.setText("Menu: Player " + (GameManager.getActvPlyrIdx() + 1));
				System.out.println(GameManager.getActvPlyrIdx());
				playerStats();

			} else if (e.getSource() == neighborButt1) {
				System.out.println("Selected to move to the first neighbor");
				System.out.println("Space before: " + GameManager.getActivePlayer().currLocation.name);
				String newSceneName = neighborButt1.getText().toLowerCase();
				GameManager.getActivePlayer().move(newSceneName);
				mainMenu.setVisible(true);
				neighborMenu.setVisible(false);
				GameManager.makeMoved();
				JLabel newSpaceLabel = spaces.get(newSceneName);
				players[GameManager.getActvPlyrIdx()].setBounds(newSpaceLabel.getBounds());

				Player player = GameManager.getActivePlayer();
				Space currSpace = player.currLocation;
				if (currSpace instanceof Scene) {
					System.out.println("Moved to a scene. Setting the card to it's front");
					Scene currScene = (Scene) currSpace;
					Card workingCard = currScene.getCard();
					String spaceName = currSpace.name.toLowerCase();
					JLabel currCardLabel = cards.get(spaceName);
					System.out.println("cards.get(" + spaceName + "): " + currCardLabel);

					ImageIcon imgIcn = new ImageIcon("graphics/Card/" + workingCard.getBackground());
					Image img = imgIcn.getImage().getScaledInstance((int) (imgIcn.getIconWidth() * scaleRatio),
							(int) (imgIcn.getIconHeight() * scaleRatio), Image.SCALE_SMOOTH);
					// imgIcn.setImage(img);
					currCardLabel.setIcon(new ImageIcon(img));

				}

				System.out.println("Space after: " + GameManager.getActivePlayer().currLocation.name);
				playerStats();

			} else if (e.getSource() == neighborButt2) {
				System.out.println("Selected to move to the second neighbor");
				String newSceneName = neighborButt2.getText().toLowerCase();
				GameManager.getActivePlayer().move(newSceneName);
				mainMenu.setVisible(true);
				neighborMenu.setVisible(false);
				GameManager.makeMoved();
				JLabel newSpaceLabel = spaces.get(newSceneName);
				players[GameManager.getActvPlyrIdx()].setBounds(newSpaceLabel.getBounds());

				Player player = GameManager.getActivePlayer();
				Space currSpace = player.currLocation;
				if (currSpace instanceof Scene) {
					System.out.println("Moved to a scene. Setting the card to it's front");
					Scene currScene = (Scene) currSpace;
					Card workingCard = currScene.getCard();
					String spaceName = currSpace.name.toLowerCase();
					JLabel currCardLabel = cards.get(spaceName);
					System.out.println("cards.get(" + spaceName + "): " + currCardLabel);
					ImageIcon imgIcn = new ImageIcon("graphics/Card/" + workingCard.getBackground());
					Image img = imgIcn.getImage().getScaledInstance((int) (imgIcn.getIconWidth() * scaleRatio),
							(int) (imgIcn.getIconHeight() * scaleRatio), Image.SCALE_SMOOTH);
					// imgIcn.setImage(img);
					currCardLabel.setIcon(new ImageIcon(img));

				}

				playerStats();

			} else if (e.getSource() == neighborButt3) {
				System.out.println("Selected to move to the third neighbor");
				String newSceneName = neighborButt3.getText().toLowerCase();
				GameManager.getActivePlayer().move(newSceneName);
				mainMenu.setVisible(true);
				neighborMenu.setVisible(false);
				GameManager.makeMoved();
				JLabel newSpaceLabel = spaces.get(newSceneName);
				players[GameManager.getActvPlyrIdx()].setBounds(newSpaceLabel.getBounds());

				Player player = GameManager.getActivePlayer();
				Space currSpace = player.currLocation;
				if (currSpace instanceof Scene) {
					System.out.println("Moved to a scene. Setting the card to it's front");
					Scene currScene = (Scene) currSpace;
					Card workingCard = currScene.getCard();
					String spaceName = currSpace.name.toLowerCase();
					JLabel currCardLabel = cards.get(spaceName);
					System.out.println("cards.get(" + spaceName + "): " + currCardLabel);
					ImageIcon imgIcn = new ImageIcon("graphics/Card/" + workingCard.getBackground());
					Image img = imgIcn.getImage().getScaledInstance((int) (imgIcn.getIconWidth() * scaleRatio),
							(int) (imgIcn.getIconHeight() * scaleRatio), Image.SCALE_SMOOTH);
					// imgIcn.setImage(img);
					currCardLabel.setIcon(new ImageIcon(img));

				}

				playerStats();

			} else if (e.getSource() == neighborButt4) {
				System.out.println("Selected to move to the fourth neighbor");
				String newSceneName = neighborButt4.getText().toLowerCase();
				GameManager.getActivePlayer().move(newSceneName);
				mainMenu.setVisible(true);
				neighborMenu.setVisible(false);
				GameManager.makeMoved();
				JLabel newSpaceLabel = spaces.get(newSceneName);
				players[GameManager.getActvPlyrIdx()].setBounds(newSpaceLabel.getBounds());

				Player player = GameManager.getActivePlayer();
				Space currSpace = player.currLocation;
				if (currSpace instanceof Scene) {
					System.out.println("Moved to a scene. Setting the card to it's front");
					Scene currScene = (Scene) currSpace;
					Card workingCard = currScene.getCard();
					String spaceName = currSpace.name.toLowerCase();
					JLabel currCardLabel = cards.get(spaceName);
					System.out.println("cards.get(" + spaceName + "): " + currCardLabel);
					ImageIcon imgIcn = new ImageIcon("graphics/Card/" + workingCard.getBackground());
					Image img = imgIcn.getImage().getScaledInstance((int) (imgIcn.getIconWidth() * scaleRatio),
							(int) (imgIcn.getIconHeight() * scaleRatio), Image.SCALE_SMOOTH);
					// imgIcn.setImage(img);
					currCardLabel.setIcon(new ImageIcon(img));

				}

				playerStats();

			} else if (e.getSource() instanceof JButton) {
				JButton b = (JButton) e.getSource();

				if (b.getText().equals("Cancel")) {
					neighborMenu.setVisible(false);
					mainMenu.setVisible(true);
					System.out.println("Move cancelled");
					playerStats();
				}
			} else {
				// This is what happens when the player clicks a button without a purpose.
				// This should not happen
				System.out.println("Clicked <Redacted>");
				playerStats();
			}
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}
}
