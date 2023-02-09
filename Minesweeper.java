import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.*;

import java.util.*;


public class Minesweeper extends Application {

	int rows = 4, cols = 5;
	int mines = 5;
	
	int count;
	
	void setCount(){
		count = rows * cols - mines;
	}
	
	int game[][] = new int[rows][cols];

	Set randomNumbers(int mines, int rows, int cols){
		Random rand = new Random();
		Set<Integer> bombs = new HashSet<>();
		
		while(bombs.size() < mines){
		
			int randomNumber = rand.nextInt(cols*rows) + 1;
			bombs.add(randomNumber);
		
		}
		
		return bombs;
		
	}
	
	void printGame(){
	
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				System.out.print(game[i][j]+ " ");
			}
			System.out.println();
		}
	
	}

	public void changeValues() {
		for (int i = 0; i < rows; i++) {
		   for (int j = 0; j < cols; j++) {
		      if (game[i][j] == 0) {
		        int count = 0;
		        // Check the top left
		        if (i > 0 && j > 0 && game[i-1][j-1] == -1) {
		            count++;
		        }
		        // Check above
		        if (i > 0 && game[i-1][j] == -1) {
		            count++;
		        }
		        // Check the top right
		        if (i > 0 && j < cols-1 && game[i-1][j+1] == -1) {
		            count++;
		        }
		        // Check the left
		        if (j > 0 && game[i][j-1] == -1) {
		            count++;
		        }
		        // Check the right
		        if (j < cols-1 && game[i][j+1] == -1) {
		            count++;
		        }
		        // Check the bottom left
		        if (i < rows-1 && j > 0 && game[i+1][j-1] == -1) {
		            count++;
		        }
		        // Check below
		        if (i < rows-1 && game[i+1][j] == -1) {
		            count++;
		        }
		        // Check the bottom right
		        if (i < rows-1 && j < cols-1 && game[i+1][j+1] == -1) {
		            count++;
		        }
		        game[i][j] = count;
		        }
		    }
		}
	}

	Button initButton(int value, Stage primaryStage){
		Button button = new Button("\0");
		button.setPadding(new Insets(30, 30, 30, 30));
		button.setAlignment(Pos.CENTER);
		button.setOnAction(e -> {
			
			if(value == -1){
				gameOver("You LOST", primaryStage);
			}
		
			button.setText(String.valueOf(value));
			button.setDisable(true);			
			count--;
			
			if(count == 0){
				gameOver("You WON", primaryStage);
			}
			
		});
		
		return button;
	}
	
	void initGame(GridPane gridPane, Stage primaryStage){
	
		Set gameMines = randomNumbers(mines, rows, cols);
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if(gameMines.contains(i*cols + j)){
					game[i][j] = -1;
				}else{
					game[i][j] = 0;
				}	
			}
		}
		
		changeValues();
		
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				gridPane.add(initButton(game[i][j], primaryStage), j, i);
			}
		}
		
		
		printGame();
	}
	
	void gameOver(String string, Stage primaryStage){
	
		System.out.println(string);
	
		newGame(primaryStage);
	
		Stage stage = new Stage();
		Label label = new Label(string);
		label.setAlignment(Pos.CENTER);
		Scene scene = new Scene(label, 300, 300);

		stage.setTitle("Game Over");
		stage.setScene(scene);
		stage.show();

	}
	
	public void newGame(Stage primaryStage){
	
		
		setCount();
	
		GridPane gridPane = new GridPane();
		initGame(gridPane, primaryStage);
		
		Scene scene = new Scene(gridPane);
		primaryStage.setScene(scene);
	}

	public void start(Stage primaryStage){
	
		newGame(primaryStage);		
		primaryStage.setTitle("Minesweeper");
		primaryStage.show();
	}

	public static void main(String[] args){
		launch(args);
	}

}
