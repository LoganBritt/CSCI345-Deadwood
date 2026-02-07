
/*
//	This is the Class for the Card object that houses card roles and info
//	There should be 40 of these at any time and belong to the Deck object
*/
import java.awt.image.BufferedImage;

public class Card {

        private Scene scene;
        private BufferedImage background;
        private int budget;
        private Role[] roles;

        public static void main(String[] args) {
                System.out.println("Running Card.java");
        }

        Card(int cardBudget, Role[] roleList) {
                budget = cardBudget;
                roles = roleList;
        }

        // gets the current scene
        public Scene getScene() {
                return scene;
        }

        // sets the new scene to be the current scene
        public void setScene(Scene newScene) {
                scene = newScene;
        }

        // gives a role to the player
        public void setPlayer(Player player, Role role) {

        }

        public int getBudget() {
                return budget;
        }

        public Role[] getRoles() {
                return roles;
        }

}
