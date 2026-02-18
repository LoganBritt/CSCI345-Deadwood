# CSCI345-Deadwood
This is the repository that contains the code for the game Deadwood

Window has been excluded from implementation for Assignment 2

Remaining Work:\
Report\
Class Diagram

Testing:\
We need to test upgrading, end day, end game, taking new upgrades, scene fininhing on and off card,
end scene bonus distribution, and a lot of UI stuff needs to be updated to make to look good.

Bugs:\

About Deadwood:\
Deadwood is a board game created by Cheap Ass Games, and was originally designed as an easy-to-create
board game for people to create for cheap and using materials typically found around the house.
This is a terminal-based implementation of the game.

To begin the game, in your terminal, execute Deadwood.java. This will begin the game. Follow instuctions
to prepare the game for your number of players, and take turns between players acting, rehearsing, moving
to new sets, and trying to become the most skilled actor among your friends.

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
