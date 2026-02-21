
/*
//	This is the Class for the Card object that houses card roles and info
//	There should be 40 of these at any time and belong to the Deck object
*/
// This import we no longer need, but if we decide we need it again I wanted to leave it in
// import java.awt.image.BufferedImage;

public class Card {
        private Scene scene;
        private String background;
        private String title = "default";
        private String desc = "This card has no decription";
        private int budget;
        private Role[] roles;
        private int sceneNum;
        private int x;
        private int y;
        private int h;
        private int w;
        private int level;

        // Default contructor that sets up the card with the budget and the roleList
        Card(int cardBudget, Role[] roleList) {
                budget = cardBudget;
                roles = roleList;
        }

        // Default constructor that sets up the card with the budget, roleList, title,
        // and description
        Card(int cardBudget, Role[] roleList, String newTitle, String newDesc) {
                budget = cardBudget;
                roles = roleList;
                title = newTitle;
                desc = newDesc;
        }

        // gets the current scene
        public Scene getScene() {
                return scene;
        }

        // sets the new scene to be the current scene
        public void setScene(Scene newScene) {
                scene = newScene;
        }

        // gets the budget of the card
        public int getBudget() {
                return budget;
        }

        // sets the budget to the budget of the card
        public void setBudget(int newBudget) {
                budget = newBudget;
        }

        // returns the roles from the role list
        public Role[] getRoles() {
                return roles;
        }

        // gets the title of the card
        public String getTitle() {
                return title;
        }

        // sets the title of the card
        public void setTitle(String newTitle) {
                title = newTitle;
        }

        // gets the scene description
        public String getDesc() {
                return desc;
        }

        // sets the scene description
        public void setDesc(String newDesc) {
                desc = newDesc;
        }

        // gets the background
        public String getBackground() {
                return background;
        }

        // sets the background
        public void setBackground(String newBackground) {
                background = newBackground;
        }

        // gets the number of the scene
        public int getSceneNumber() {
                return sceneNum;
        }

        // gets sets the number of the scene
        public void setSceneNumber(int newNum) {
                sceneNum = newNum;
        }

        // Gets the level of the role
        public int getLevel() {
                return level;
        }

        // Sets the level of the role
        public void setLevel(int newLevel) {
                level = newLevel;
        }

        // gets the X value of the role
        public int getX() {
                return x;
        }

        // sets the X value of the role
        public void setX(int newX) {
                x = newX;
        }

        // gets the Y value of the role
        public int getY() {
                return y;
        }

        // sets the Y value of the role
        public void setY(int newY) {
                y = newY;
        }

        // gets the H value of the role
        public int getH() {
                return h;
        }

        // sets the H value of the role
        public void setH(int newH) {
                h = newH;
        }

        // gets the W value of the role
        public int getW() {
                return w;
        }

        // Sets the W value of the role
        public void setW(int newW) {
                w = newW;
        }

        // checks to see if the card has players on it
        public boolean hasPlayers() {
                for (int i = 0; i < roles.length; i++) {
                        if ((roles[i] != null) && (roles[i].getPlayer() != null)) {
                                return true;
                        }
                }
                return false;
        }
}
