// This program creates a customized version of the GUI for Deadwood
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GUIManager extends JFrame {
	JFrame frame;
	JLayeredPane layeredFrame;
	JLabel boardLabel;
	double scaleRatio = 1;
	ImageIcon boardImage = scaleImageIcon(new ImageIcon("graphics/board.jpg"));
	//Labels and buttons for the main menu
	JLabel menuLabel;
	int buttCt = 5;
	JButton actButt;
	JButton reherButt;
	JButton moveButt;
	JButton endTButt;
	JLayeredPane neighborMenu;
	JLayeredPane mainMenu;

	//Labels for the upgrade menu
	JButton upgradeButt;
	JLayeredPane upgradeMenu;
	ArrayList<JButton> upgradeButtons = new ArrayList<>();

	//Labels for the neighbor menu
	JButton neighborButt1, neighborButt2, neighborButt3, neighborButt4;

	//Labels for the stats menu
	JLayeredPane statsMenu;
	JLabel statsTitle, dayLabel, rankLabel, dollarLabel, creditsLabel, rehearseTokensLabel, locationLabel, roleLabel;

	//These hashtables are for knowing which label matches to which physical object
	Hashtable<JPanel, Role> labelToRole = new Hashtable<>();
	Hashtable<Role, Space> roleToSpace = new Hashtable<>();
	Hashtable<String, JLabel> spaces = new Hashtable<>();
	Hashtable<String, JLabel> cards = new Hashtable<>();
	Hashtable<String, JLabel[]> shots = new Hashtable<>();
	Hashtable<Player, JLabel> playerToLabel = new Hashtable<>();
	JLabel[] players;

	//Preset data for picture names
	String[] nameSet = new String[] {
			"train station", "secret hideout",
			"church",        "hotel",
			"main street",   "jail",
			"general store", "ranch",
			"bank",          "saloon",
			"trailer",       "office"
	};

	String[] playerDiceOrder = new String[] {"b", "c", "g", "o", "p", "r", "v", "w", "y"};

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
			} catch (NumberFormatException e) {}
		}

		players = new JLabel[playerNumberInput];
		GameManager.setPlayerAmt(playerNumberInput);
		GameManager.createPlayers();

		initScreen();
		initScreenAreas(); 
		frame.setVisible(true);
	}

	// Often we need to scale an image according to screen size. This returns the an ImageIcon that had been scaled
	private ImageIcon scaleImageIcon(ImageIcon prev){
		Image scaledIcon = prev.getImage().getScaledInstance((int) (prev.getIconWidth() * scaleRatio),
		(int) (prev.getIconHeight() * scaleRatio), Image.SCALE_SMOOTH);
		prev.setImage(scaledIcon);
		return prev;
	}

	// Initilizes the Screen and all of the different menus
	private void initScreen() {
		frame = initFrame();
		layeredFrame = frame.getLayeredPane();

		boardLabel = initBoard(layeredFrame);
		mainMenu = initMainMenu(layeredFrame);
		statsMenu = initStatsMenu(layeredFrame);
		upgradeMenu = initUpgradeMenu(layeredFrame);

		// Creates the needed button number for the Main Menu
		for (int i = 0; i < buttCt; i++) {
			initButt(i, mainMenu);
		}

		neighborMenu = initNeighborMenu(layeredFrame);
		cards = initCards();
		shots = initShots();
		System.out.println("Initialization Complete. Showing GUI\n");
	}

	// initilizes the cards and places them on the board
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
				retLabel.setIcon(scaleImageIcon(new ImageIcon("graphics/cardBack.png")));
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
		// gets the number of scenes and gets the shot counter location for each scene
		for (int i = 0; i < spaceList.size(); i++) {
			if (spaceList.get(i) instanceof Scene) {
				Space workingSpace = spaceList.get(i);
				Scene workingScene = (Scene) workingSpace;

				int shotLoc[][] = new int[3][4];
				shotLoc = workingScene.getShotLocation();
				JLabel arr[] = new JLabel[4];

				// gets the values of the shot counter location, sets the sprites
				for (int j = 0; j < shotLoc.length; j++) {
					if (shotLoc[j] != null) {
						int x = shotLoc[j][0];
						int y = shotLoc[j][1];
						int h = shotLoc[j][2];
						int w = shotLoc[j][3];

						JLabel shot = new JLabel();
						shot.setIcon(scaleImageIcon(new ImageIcon("graphics/shot.png")));
						shot.setBounds((int) (x * scaleRatio), (int) (y * scaleRatio), (int) (w * scaleRatio),
								(int) (h * scaleRatio));
						shot.setVisible(true);
						layeredFrame.add(shot, JLayeredPane.PALETTE_LAYER);
						arr[j] = shot;

					}
				}
				shotTable.put(nameSet[index], arr);
				index++;

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
			player.setIcon(scaleImageIcon(new ImageIcon("graphics/Dice/" + playerDiceOrder[i] + players[i].rank + ".png")));
			player.setBounds(start.getBounds());
			player.setOpaque(true);
			player.setVisible(true);
			layeredFrame.add(player, JLayeredPane.PALETTE_LAYER + 100, 1);
			retLabel[i] = player;
			playerToLabel.put(players[i], retLabel[i]);
		}
		return retLabel;
	}

	// initializes the screen areas
	// additionally handles where the players go when they are on the board
	// also handles taking a role on and off card
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
			// if the working space is not null, then spaces are created for the players
			if (workingSpace != null) {
				JLabel playerSpace = new JLabel();
				playerSpace.setBounds(statSet[i][0], statSet[i][1], statSet[i][2], statSet[i][3]);
				// Check to see if the mouse has entered/exited the player space
				//This mouse listener only exists in case we decide to implement a popup menu for showing what players at at a scene
				playerSpace.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						//System.out.println("Entered player space");
						playerSpace.repaint();
					}

					@Override
					public void mouseExited(MouseEvent e) {
						//System.out.println("Exited player space");
						playerSpace.repaint();
					}
				});

				layeredFrame.add(playerSpace, JLayeredPane.PALETTE_LAYER);
				spaces.put(nameSet[i], playerSpace);

				// Create places for the roles
				if (workingSpace instanceof Scene) {
					Scene workingScene = (Scene) workingSpace;
					Card workingCard = workingScene.getCard();
					Role[] roleSet = workingScene.getRoles();
					// goes through the roleset, and for each role gets the x/y/h/w values and then
					// sets the bounds for those spaces
					for (int j = 0; j < roleSet.length; j++) {
						if (roleSet[j] != null) {
							// For each role on the scene
							roleToSpace.put(roleSet[j], workingSpace);
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
								public void mouseClicked(MouseEvent e) {
									Scene scene = (Scene) GameManager.getActivePlayer().currLocation;
									Role matchedRole = labelToRole.get(rolePanel);
									// checks to see if role taking conditions are met
									if (GameManager.getPlayerMoved() || GameManager.getTookRole()
											|| scene.getCard() == null) {
										return;
									}
									System.out.println(GameManager.getPlayerMoved() + ", " + GameManager.getTookRole()
											+ ", " + scene.getCard() == null);
									// checks to see if the role exists
									if (matchedRole == null) {
										System.out.println("Didn't find the associated role");
									}
									// checks to see if the role is on the scene
									else {
										System.out.println("Found role: " + matchedRole.getTitle());
										Player player = GameManager.getActivePlayer();
										Role[] roleList = scene.getRoles();
										boolean foundRole = false;
										// checking the title of the role to make sure it is on the scene
										for (int k = 0; k < roleList.length; k++) {
											if (roleList[k] != null
													&& roleList[k].getTitle().equals(matchedRole.getTitle())) {
												foundRole = true;
											}
										}
										// checks to see if the player can take the role
										// puts the plauer on that role
										if ((player.currRole == null) && matchedRole.canTake(player) && foundRole
												&& (scene.getCard() != null)) {
											System.out.println("Player can take this role");
											player.currRole = matchedRole;
											matchedRole.setPlayer(player);
											JLabel playerLabel = playerToLabel.get(player);
											playerLabel.setBounds(rolePanel.getBounds());
											GameManager.makeTaken();
											neighborMenu.setVisible(false);
											mainMenu.setVisible(true);
										} else {
											System.out.println("Player cannot take this role");
										}
									}

									playerStats();
								}

								// checks to see if the mouse entered/exited the role area
								@Override
								public void mouseEntered(MouseEvent e) {
									//System.out.println("Entered role");
									Role r = labelToRole.get(rolePanel);
									Space sp = roleToSpace.get(r);
									Scene scene = null;
									if(sp instanceof Scene){
										scene = (Scene) sp;
									}
									if(scene != null && scene.getCard() != null){
										rolePanel.setOpaque(true);
										rolePanel.repaint();
									}
								}

								@Override
								public void mouseExited(MouseEvent e) {
									//System.out.println("Exited role");
									rolePanel.setOpaque(false);
									rolePanel.repaint();
								}
							});
							labelToRole.put(rolePanel, roleSet[j]);

							// Add to layered pane
							layeredFrame.add(rolePanel, JLayeredPane.PALETTE_LAYER);
						}
					}

					//This chunk is for in case we wanted to show when a card is being hovered over, but it doesn't work and we didn't need it anyway
					//It was safer to just not remove it
					JPanel cardPanel = new JPanel();
					cardPanel.setBounds((int) (workingScene.getX() * scaleRatio),
							(int) (workingScene.getY() * scaleRatio), (int) (workingScene.getW() * scaleRatio),
							(int) (workingScene.getH() * scaleRatio));
					cardPanel.setOpaque(false);
					cardPanel.setBackground(Color.BLUE);
					// checks to see if the mouse has entered/exited the card
					cardPanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							//System.out.println("Entered card");
							cardPanel.setOpaque(true);
							cardPanel.repaint();
						}

						@Override
						public void mouseExited(MouseEvent e) {
							//System.out.println("Exited card");
							cardPanel.setOpaque(false);
							cardPanel.repaint();
						}
					});
					layeredFrame.add(cardPanel, JLayeredPane.PALETTE_LAYER + 10);

					Role[] cardRoles = workingCard.getRoles();
					for (int j = 0; j < cardRoles.length; j++) {
						// For each role on the card, find the x/y/w/h and set the color to orange when
						// hovered over
						if (cardRoles[j] != null) {
							roleToSpace.put(cardRoles[j], workingSpace);
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
								// Looks for roles and lets the player take them
								public void mouseClicked(MouseEvent e) {
									Scene scene = (Scene) GameManager.getActivePlayer().currLocation;
									// checks to see if the role taking conditions are met
									if (GameManager.getPlayerMoved() || GameManager.getTookRole()|| scene.getCard() == null)
										return;
									System.out.println(GameManager.getPlayerMoved() + ", " + GameManager.getTookRole()
											+ ", " + scene.getCard() == null);
									Role matchedRole = labelToRole.get(cardRole);
									// checks to see if the role exists
									if (matchedRole == null) {
										System.out.println("Didn't find the associated role");
									}
									// finds the role and checks to see if it's the correct role
									else {
										System.out.println("Found role: " + matchedRole.getTitle());
										Player player = GameManager.getActivePlayer();
										Card sceneCard = scene.getCard();
										Role[] roleList = sceneCard.getRoles();
										boolean foundRole = false;
										// checks to see if the desired role title equals the role title
										for (int k = 0; k < roleList.length; k++) {
											if (roleList[k] != null
													&& roleList[k].getTitle().equals(matchedRole.getTitle())) {
												foundRole = true;
											}
										}
										// if the player can take the role, place the player on the role
										if ((player.currRole == null) && matchedRole.canTake(player) && foundRole
												&& (scene.getCard() != null)) {
											System.out.println("Player can take this role");
											player.currRole = matchedRole;
											matchedRole.setPlayer(player);
											JLabel playerLabel = playerToLabel.get(player);
											playerLabel.setBounds(cardRole.getBounds());
											neighborMenu.setVisible(false);
											mainMenu.setVisible(true);

										} else {
											System.out.println("Player cannot take this role");
										}
									}

									playerStats();
								}

								// Mouse listeners for entering/exiting the role.
								@Override
								public void mouseEntered(MouseEvent e) {
									//System.out.println("Entered cardRole");
									Role r = labelToRole.get(cardRole);
									Space sp = roleToSpace.get(r);
									Scene scene = null;
									if(sp instanceof Scene){
										scene = (Scene) sp;
									}
									if((scene != null) && (scene.getCard() != null)){
										cardRole.setOpaque(true);
										cardRole.repaint();
									}
								}

								@Override
								public void mouseExited(MouseEvent e) {
									//System.out.println("Exited cardRole");
									cardRole.setOpaque(false);
									cardRole.repaint();
								}

							});
							labelToRole.put(cardRole, cardRoles[j]);
							layeredFrame.add(cardRole, JLayeredPane.PALETTE_LAYER + 10, 0);
						}
					}
				}
			}
		}
		players = initPlayers();
		playerStats();
	}

	// initializes and shows the current player's stats
	// shows rank, dollars, credits, rehearsal tokens, current location, and role
	private JLayeredPane initStatsMenu(JLayeredPane basePane) {
		int width = 220;
		int height = 220;
		int x = boardImage.getIconWidth() + 5;
		int y = 320;

		JLayeredPane pane = new JLayeredPane();
		pane.setBounds(x, y, width, height);
		pane.setBackground(new Color(177, 114, 70));
		pane.setOpaque(true);

		// Title creation
		statsTitle = new JLabel();
		statsTitle.setBounds(50, 10, 160, 30);
		statsTitle.setForeground(Color.WHITE);
		statsTitle.setFont(new Font("Palatino Linotype", Font.BOLD, 18));
		pane.add(statsTitle, 1);

		dayLabel = new JLabel();
		dayLabel.setBounds(50, 30, 160, 30);
		dayLabel.setForeground(Color.LIGHT_GRAY);
		dayLabel.setFont(new Font("Palatino Linotype", Font.BOLD, 17));
		pane.add(dayLabel, 1);

		// Rank
		rankLabel = new JLabel();
		rankLabel.setBounds(20, 60, 200, 20);
		rankLabel.setForeground(new Color(200, 200, 200));
		pane.add(rankLabel, 1);

		// Dollars and Credits
		dollarLabel = new JLabel();
		dollarLabel.setBounds(20, 85, 200, 20);
		dollarLabel.setForeground(new Color(200, 200, 200));
		pane.add(dollarLabel, 1);

		creditsLabel = new JLabel();
		creditsLabel.setBounds(20, 110, 200, 20);
		creditsLabel.setForeground(new Color(200, 200, 200));
		pane.add(creditsLabel, 1);

		// Rehearsal Tokens
		rehearseTokensLabel = new JLabel();
		rehearseTokensLabel.setBounds(20, 135, 200, 20);
		rehearseTokensLabel.setForeground(new Color(200, 200, 200));
		pane.add(rehearseTokensLabel, 1);

		// Location and Role
		locationLabel = new JLabel();
		locationLabel.setBounds(20, 160, 200, 20);
		locationLabel.setForeground(new Color(200, 200, 200));
		pane.add(locationLabel, 1);

		roleLabel = new JLabel();
		roleLabel.setBounds(20, 185, 200, 20);
		roleLabel.setForeground(new Color(200, 200, 200));
		pane.add(roleLabel, 1);

		basePane.add(pane, 2);
		return pane;
	}

	// this method sets the information for the stats menu
	public void playerStats() {
		Player p = GameManager.getActivePlayer();
		statsTitle.setText("Player " + (GameManager.getActvPlyrIdx() + 1) + " Stats");
		dayLabel.setText("Days Left: " + GameManager.getDay());
		rankLabel.setText("Rank: " + p.rank);
		dollarLabel.setText("Dollars: " + p.dollars);
		creditsLabel.setText("Credits: " + p.credits);
		rehearseTokensLabel.setText("Rehearsal Tokens: " + p.rehearseTokens);
		String name = p.currLocation.name;
		if(name.equals("trailer")) name = "Trailers";
		if(name.equals("office")) name = "Casting Office";
		locationLabel.setText("Current Location: " + name);
		// sets the text for the player's current role
		if (p.currRole != null) {
			roleLabel.setText("Role: " + p.currRole.getTitle());
		} else {
			roleLabel.setText("Role: Not Working");
		}
	}

	// initilizes the Neighbor Menu
	private JLayeredPane initNeighborMenu(JLayeredPane basePane) {

		int width = 220;
		int height = 300;
		int x = boardImage.getIconWidth() + 5;
		int y = 5;

		JLayeredPane pane = new JLayeredPane();
		pane.setBounds(x, y, width, height);
		pane.setBackground(new Color(177, 114, 70));
		pane.setOpaque(true);

		JLabel label = new JLabel("Move to which?");
		label.setBounds(30, 10, 160, 30);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Palatino Linotype", Font.BOLD, 18));
		pane.add(label, 1);

		// goes through each neighbor and creates a button for it
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
			moveOption.setBackground(new Color(188, 218, 157));
			moveOption.setFocusPainted(false);
			moveOption.setForeground(Color.WHITE);
			moveOption.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
			moveOption.setBounds(10, 50 + (i * 40), 200, 35);
			moveOption.addMouseListener(new boardMouseListener());
			pane.add(moveOption, 2);
		}

		JButton cancel = new JButton("Cancel");
		cancel.setBackground(new Color(188, 218, 157));
		cancel.setFocusPainted(false);
		cancel.setForeground(Color.WHITE);
		cancel.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
		cancel.setBounds(10, 210, 200, 35);
		cancel.addMouseListener(new boardMouseListener());
		pane.add(cancel, 2);

		pane.setVisible(false);

		basePane.add(pane, 3);

		return pane;
	}

	// initializes the Upgrade Menu with buttons for dollars and credit upgrades
	private JLayeredPane initUpgradeMenu(JLayeredPane basePane) {
		Casting c = (Casting) BoardManager.board.getSpaceByName("office");
		int[] dollarCost = c.getDollarAmts();
		int[] creditCost = c.getCreditAmts();

		int x = boardImage.getIconWidth() + 5;
		int y = 5;
		int w = 220;
		int h = 300;

		JLayeredPane pane = new JLayeredPane();
		pane.setBounds(x, y, w, h);
		pane.setOpaque(true);
		pane.setBackground(new Color(177, 114, 70));
		JLabel title = new JLabel("Upgrade Rank:");
		title.setBounds(30, 10, 160, 30);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Palatino Linotype", Font.BOLD, 18));
		pane.add(title, 1);

		// for each rank option (rank number, dollars/credits) a button is created
		for (int i = 0; i < 5; i++) {
			int rank = (i + 2);
			JLabel rankLabel = new JLabel("Rank " + rank);
			rankLabel.setBounds(10, 50 + (i * 40), 60, 30);
			rankLabel.setForeground(new Color(230, 230, 230));
			rankLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
			pane.add(rankLabel, 1);

			JButton dollarButt = new JButton("$" + dollarCost[i]);
			dollarButt.setBounds(70, 50 + (i * 40), 60, 30);
			dollarButt.setBackground(new Color(188, 218, 157));
			dollarButt.setForeground(Color.WHITE);
			dollarButt.addMouseListener(new boardMouseListener());
			pane.add(dollarButt, 2);

			JButton credButt = new JButton(creditCost[i] + "c");
			credButt.setBounds(140, 50 + (i * 40), 60, 30);
			credButt.setBackground(new Color(188, 218, 157));
			credButt.setForeground(Color.WHITE);
			credButt.addMouseListener(new boardMouseListener());
			pane.add(credButt, 2);

			upgradeButtons.add(dollarButt);
			upgradeButtons.add(credButt);
		}

		JButton cancel = new JButton("Cancel");
		cancel.setBounds(10, 250, 200, 35);
		cancel.setBackground(new Color(188, 218, 157));
		cancel.setForeground(Color.WHITE);
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
		newFrame.getContentPane().setBackground(new Color(234, 203, 175));
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

		// initialises all the buttons for the main menu
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
			case 4:
				// Initializing the Upgrade Button
				System.out.println("Initializing Button 4: Upgrade");
				tempButt = new JButton("Upgrade");
				upgradeButt = tempButt;
				upgradeButt.setVisible(false);
				break;
			default:
				System.out.println("Tried implementing a button that does not exist: " + idx);
				return;
		}
		tempButt.setBackground(new Color(188, 218, 157));
		tempButt.setFocusPainted(false);
		tempButt.setForeground(Color.WHITE);
		tempButt.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		tempButt.setBounds(10, 50 + (idx * quanticY), buttWidth, buttHeight);
		tempButt.addMouseListener(new boardMouseListener());
		pane.add(tempButt, 2);
		System.out.println("Button " + idx + " Initialized\n");
	}

	// initilizes the main Menu
	private JLayeredPane initMainMenu(JLayeredPane basePane) {
		int width = 220;
		int height = 300;

		int x = boardImage.getIconWidth() + 5;
		int y = 5;

		JLayeredPane pane = new JLayeredPane();
		pane.setBounds(x, y, width, height);
		pane.setBackground(new Color(177, 114, 70));
		pane.setOpaque(true);

		JLabel label = new JLabel("Action Menu: P" + (GameManager.getActvPlyrIdx()+ 1));
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
		// goes through all the neighbors and correctly labels the buttons with the
		// names
		for (int i = 0; i < neighbors.length; i++) {
			String name = neighbors[i].name;
			if(name.equals("office")) name = "Casting Office";
			if(name.equals("trailer")) name = "Trailers";
			switch (i) {
				case 0: neighborButt1.setText(name); break;
				case 1: neighborButt2.setText(name); break;
				case 2: neighborButt3.setText(name); break;
				case 3: neighborButt4.setText(name); break;
				default: break;
			}
		}
		neighborButt4.setVisible(neighbors.length == 4);
	}

	// Mouse event handler for the GUI
	class boardMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			// what happens when the Act button is pressed
			if (e.getSource() == actButt) {
				System.out.println("Clicked Act Button");
				Player player = GameManager.getActivePlayer();
				if(!(player.currLocation instanceof Scene)) return;
				Scene scene = (Scene) player.currLocation;
				// This is everything that will happen when the player tries to act
				// the player acts, shot counters are removed if there is a success, player
				// stats are updated
				if (GameManager.getPlayerActed() || player.currRole == null || scene.getShots() <= 0)
					return;
				boolean success = player.act(player.currRole.isOnCard());
				GameManager.makeActed();
				if (success) {
					System.out.println("Acting Success");
					System.out.println("Remaining shots: " + scene.getShots());
					String sceneName = scene.name.toLowerCase();
					JLabel[] shotsHere = shots.get(sceneName);
					int idx = 0;
					while (shotsHere != null && !shotsHere[idx].isVisible()) idx++;
					shotsHere[idx].setVisible(false);
					if (scene.getShots() == 0) {
						JLabel spot = spaces.get(sceneName);
						Role[] roleList = new Role[scene.getRoles().length + scene.getCard().getRoles().length];
						System.arraycopy(scene.getRoles(), 0, roleList, 0, scene.getRoles().length);
						System.arraycopy(scene.getCard().getRoles(), 0, roleList, scene.getRoles().length, scene.getCard().getRoles().length);
						for(int i = 0; i < roleList.length; i++){
								if(roleList[i] != null && roleList[i].getPlayer() != null){
									player = roleList[i].getPlayer();
									JLabel playerLabel = playerToLabel.get(player);
									playerLabel.setBounds(spot.getBounds());
									player.currRole.setPlayer(null);
									player.currRole = null;
								}
						}
						cards.get(sceneName).setVisible(false);
						scene.setCard(null);
					}
				}
			}
			// if the rehearse button is pressed, then the player rehearses and the stats
			// are updated
			else if (e.getSource() == reherButt) {
				// This is everything that will happen when the player tries to rehearse
				System.out.println("Clicked Rehearse Button");
				Player player = GameManager.getActivePlayer();
				if(!(player.currLocation instanceof Scene) || GameManager.getPlayerActed() || player.currRole == null) return;
				System.out.println("Rehearsal tokens before: " + player.rehearseTokens);
				player.rehearse();
				System.out.println("Rehearsal tokens after: " + player.rehearseTokens);
				GameManager.makeActed();
			}
			// if the move button is pressed, then the main menu's visibility is set to
			// false, and the neighbor menu becomes visable.
			else if (e.getSource() == moveButt) {
				System.out.println("Clicked Move Button");
				if (GameManager.getPlayerMoved() || (GameManager.getActivePlayer().currRole != null)) return; 
				updateNeighborOptions();
				mainMenu.setVisible(false);
				neighborMenu.setVisible(true);
			} 
			//This is functionality for ending the turn with a button click
			else if (e.getSource() == endTButt) {
				// This is everything that will happen when the player tries to end their turn
				boolean changingTurn = false;
				System.out.println("Clicked End Turn Button");
				System.out.println(GameManager.getActvPlyrIdx());

				if (BoardManager.scenesLeft() <= 1) {
					Player[] playerList = GameManager.getPlayerList();
					JLabel trailerLabel = spaces.get("trailer");
					for (int i = 0; i < playerList.length; i++) {
						JLabel playerLabel = playerToLabel.get(playerList[i]);
						if(playerList[i].currRole != null) playerList[i].currRole.setPlayer(null);
						playerList[i].currRole = null;
						playerLabel.setBounds(trailerLabel.getBounds());
					}
					ArrayList<Space> spaceList = BoardManager.board.getSpaceList();
					for (int i = 0; i < spaceList.size(); i++) {
						if (spaceList.get(i) instanceof Scene) {
							Scene scene = (Scene) spaceList.get(i);
							Card card = scene.getCard();
							if (card != null) {
								System.out.println("Found a card at " + scene.name.toLowerCase());
								JLabel label = cards.get(scene.name.toLowerCase());
								label.setVisible(false);
							}
							JLabel[] shotsHere = shots.get(scene.name.toLowerCase());
							for(int j = 0; j < shotsHere.length; j++){
								if(shotsHere[j] != null) shotsHere[j].setVisible(false);
							}
							shots.remove(scene.name.toLowerCase());
						}
					}
					for(int i = 0; i < players.length; i++){
    					JLabel playerLabel = players[i];
    					playerLabel.setVisible(true);
    					playerLabel.setBounds(trailerLabel.getBounds());
					}
					changingTurn = true;
				}
				GameManager.changeTurn();
				if(GameManager.getDay() == 0){
					System.out.println("Ending Game. Closing Main Window and Showing Final Scores");
					String finalMessage = "Game Over\nFinal Scores:\n";
					Player[] playerList = GameManager.getPlayerList();
					for(int i = 0; i < playerList.length; i++){
						int score = playerList[i].dollars + playerList[i].credits + (5 * playerList[i].rank);
						finalMessage  = finalMessage + "Player " + (i+1) + ": " + score + "\n";
					}
					JOptionPane.showMessageDialog(frame, finalMessage);
					frame.dispose();
					System.exit(0);
				}
				if(changingTurn){
					ArrayList<Space> spaceList = BoardManager.board.getSpaceList();
					for(int i = 0; i < spaceList.size(); i++){
						if(spaceList.get(i) instanceof Scene){
							Scene scene = (Scene) spaceList.get(i);
							Card card = scene.getCard();
							System.out.println("Showing new card at " + scene.name.toLowerCase());
							JLabel label = cards.get(scene.name.toLowerCase());
							label.setIcon(scaleImageIcon(new ImageIcon("graphics/cardBack.png")));
							label.setVisible(true);
						}
					}
					for (JLabel card : cards.values()) { layeredFrame.remove(card); }
					for (JLabel plr : players) { layeredFrame.remove(plr); }
					layeredFrame.removeAll();
					frame.dispose();

					boardLabel = initBoard(layeredFrame);
					cards = initCards();
					shots = initShots();

					labelToRole.clear();
					roleToSpace.clear();
					upgradeButtons.clear();
					spaces.clear();
					initScreen();
					initScreenAreas();
					frame.setVisible(true);
				}
				
				upgradeButt.setVisible(GameManager.getActivePlayer().currLocation instanceof Casting);
				menuLabel.setText("Action Menu");
				System.out.println(GameManager.getActvPlyrIdx());
				System.out.println("Scenes left: " + BoardManager.scenesLeft());
			}
			// if the upgrade button is clicked, then the main menu is no longer visible and
			// the upgrade menu is.
			else if (e.getSource() == upgradeButt) {
				System.out.println("Clicked Upgrade Button");
				mainMenu.setVisible(false);
				upgradeMenu.setVisible(true);
			}
			// this is what happens when an upgrade button is clicked.
			else if (upgradeButtons.contains(e.getSource())) {
				Player p = GameManager.getActivePlayer();
				int idx = upgradeButtons.indexOf(e.getSource());
				// math to pass the correct values to the upgrade method
				boolean dollarCredit = ((idx % 2) == 0);
				System.out.println(((idx / 2) + 1) + " :) " + dollarCredit);
				p.upgrade(((idx / 2) + 2), dollarCredit);
				JLabel playerSpace = playerToLabel.get(p);
				playerSpace.setIcon(scaleImageIcon(new ImageIcon("graphics/Dice/" + playerDiceOrder[GameManager.getActvPlyrIdx()] + p.rank + ".png")));

			}
			else if((e.getSource() instanceof JButton) && ((JButton) e.getSource()).getText().equals("Cancel")){
					neighborMenu.setVisible(false);
					mainMenu.setVisible(true);
					System.out.println("Move cancelled");
					playerStats();
			}
			// what happens if a neighbor button is clicked
			JButton nButt = null;
			if(e.getSource() == neighborButt1) nButt = neighborButt1;
			else if(e.getSource() == neighborButt2) nButt = neighborButt2;
			else if(e.getSource() == neighborButt3) nButt = neighborButt3;
			else if(e.getSource() == neighborButt4) nButt = neighborButt4;
			
			if(nButt != null){
				System.out.println("Space before: " + GameManager.getActivePlayer().currLocation.name);
				String newSceneName = nButt.getText().toLowerCase();
				if(newSceneName.equals("trailers")) newSceneName = "trailer";
				if(newSceneName.equals("casting office")) newSceneName = "office";
				GameManager.getActivePlayer().move(newSceneName);
				mainMenu.setVisible(true);
				neighborMenu.setVisible(false);
				GameManager.makeMoved();
				JLabel newSpaceLabel = spaces.get(newSceneName);
				players[GameManager.getActvPlyrIdx()].setBounds(newSpaceLabel.getBounds());

				Player player = GameManager.getActivePlayer();
				Space currSpace = player.currLocation;
				// if the space that the player is moving to is a scene and the card is on it's
				// back, then the card side is switched
				if (currSpace instanceof Scene) {
					System.out.println("Moved to a scene. Setting the card to it's front");
					Scene currScene = (Scene) currSpace;
					Card workingCard = currScene.getCard();
					String spaceName = currSpace.name.toLowerCase();
					if(workingCard != null){
						JLabel currCardLabel = cards.get(spaceName);
						System.out.println("New side: graphics/Card/" + workingCard.getBackground());
						ImageIcon imgIcn = new ImageIcon("graphics/Card/" + workingCard.getBackground());
						Image img = imgIcn.getImage().getScaledInstance((int) (imgIcn.getIconWidth() * scaleRatio),
								(int) (imgIcn.getIconHeight() * scaleRatio), Image.SCALE_SMOOTH);
						currCardLabel.setIcon(new ImageIcon(img));	
					}
				}
				currSpace = GameManager.getActivePlayer().currLocation;
				System.out.println("Space after: " + currSpace.name);
				upgradeButt.setVisible(currSpace instanceof Casting);
			}
			playerStats();
		}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
	}
}
