# CSCI345-Deadwood
This is the repository that contains the code for the game Deadwood

Assignment 3 Notes: 

To begin the GUI implementation of the game, run java Deadwood to start. Then, the game will start. A dialogue will popup that asks for the number of players. Enter that number, and then the game begins. 

Below is a list of actions and how to execute them. 
	* Act -- When the player has a role, click this button. This will award the earned credits/dollars for a acting and will remove the shot counters at the scene
	* Rehearse -- Adds a rehearsal token to the player's stats. Can only add up to 1 less than the budget. 
	* Move -- pulls up a menu with all the neighboring buttons of the Space that you are on. In that neighboring menu, you can click on a location to move your sprite there. 
	* Upgrade -- While in the Casting Office, the Upgrade button will appear. When clicked, a new meny with all the upgrade options will appear. The dollars are on the left, and the Credits are on the right. When clicked, the money will leave the player's account and the rank will be updated and correctly reflect on the player's sprite
	* Taking a Role -- Click on the role you would like to take, either on the card or off the card
	* End Turn -- Ends the turn, switches to the next player


Assignment 2 Notes:

Window has been excluded from implementation for Assignment 2

About Deadwood:\
Deadwood is a board game created by Cheap Ass Games, and was originally designed as an easy-to-create
board game for people to create for cheap and using materials typically found around the house.\
This is a terminal-based implementation of the game.

To begin the game, in your terminal, execute Deadwood.java (java Deadwood). This will begin the game. Follow instuctions
in game to prepare the game for your number of players, and take turns between players acting, rehearsing, moving
to new sets, and trying to become the most skilled actor among your friends.

For the terminal version, in your temrinal, run java Deadwood to start.\
It will then start the game, and ask you for how many players will be playing. Enter your number and the game begins.\
Below is a list of commands for you to play the game. These commands can also be seen in game by typing "Help":\
	* Help: prints commands\
	* Stats: prints the active player's stats\
	* Stats All: prints the stats of all players\
	* Space: Prints everything you need to know about the space you're on\
	* Card: Prints everything you need to know about the card at the scene you're on\
	* Role: Prints everything you need to know about the role you've taken\
	* Move + space name: Moves your player to a neighboring space\
	* Act: Makes your player act on their role\
	* Rehearse: Has your player rehearse their role\
	* Take Role + role name: Allows your player to take a role on the scene or card you're at\
	* Upgrade Info: Prints all the upgrade related info you need to know\
	* Upgrade + new rank: Upgrades your player's rank to a new rank\
	* End Turn: Ends the active player's turn\
	* End Game: Ends the game early\
For more details on the commands, use the command in game for options and examples

Rules:

Deadwood takes place over the course of 4 days (3 days if 2-3 player game).\
Each day, every set is prepared with a card. Each set and card has a set of roles.\
At the beginning of each day, the players begin at the trailers and take turns moving to a set to
begin acting. A player can only move once a turn. Once a player has moved to a set, they can choose to
take up a role on either the card or the set whose level is less than or equal to the player's rank.\
When the player is working on a role, they have the option to either act or rehearse once a turn.\
When a player rehearses, they get a rehearsal token.\
If a player decides to act, they would normally roll a 6-sided die. In this version, the random chance
is determined automatically. If the random number is equal to or greater than the budget on the set's card,
the player's act was successful, and they are paid handsomely. If the acting was unsuccessful, then only
off-card roles get paid. Bonuses are distributed as follows:

* On card success: 2 credits
* On card failure: Nothing
* Off card success: 1 dollar + 1 credit
* Off card failure: 1 dollar

After a successful act, a shot counter is removed from the set. After all the shot counters have been
removed, bonuses are paid out to all players on the set and card. It is distributed as follows:

* On card bonuses: A number of dice would normally be rolled, but those are replaced with random values.
Then, they are sorted, and the highest bonueses distributed from highest on card role rank to the lowest,
and repeats until all bonueses have been paid. Each then gets dollars equal to the total bonus they got.
* Off card bonuses: Each player is paid a number of dollars equal to the rank of the role they're on.

At the Casting Office, players can make exchanges to increase their rank. The payments are as follows:

Rank 2:  4 dollars |  5 credits
Rank 3: 10 dollars | 10 credits
Rank 4: 18 dollars | 15 credits
Rank 5: 28 dollars | 20 credits
Rank 6: 40 dollars | 25 credits

A player can move from one rank to another without paying for the ranks in between

After all but one scene has been completed, the day is over.\
Players are moved back to the Trailers, shot counters are reset, and new cards are given to each set.

After the number of days are up, the game is over. The final scores are calculated as such:

dollars + credits + (5 * rank)\
Ex: $36 + 13 credits + rank 4 = (36 + 13 + (5*4)) = 69 points



