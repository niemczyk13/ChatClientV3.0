package com.niemiec.battleship.view;

import com.niemiec.battleship.controllers.MainScreenController;
import com.niemiec.battleship.controllers.AcceptanceWindowController;
import com.niemiec.battleship.controllers.WaitingWindowController;
import com.niemiec.battleship.logic.BattleshipManagement;
import com.niemiec.chat.objects.Client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BattleshipView {
	private String nick;
	private String opponentPlayerNick;
	private Client client;
	private BattleshipManagement battleshipManagement;

	private MainScreenController mainScreenController;
	private FXMLLoader mainScreenLoader;
	private VBox vBoxMainScreen;
	private Stage stageMainScreen;

	private WaitingWindowController waitingWindowController;
	private FXMLLoader waitingWindowLoader;
	private VBox vBoxWaitingWindow;
	private Stage stageWaitingWindows;

	private FXMLLoader acceptanceWindowLoader;
	private AcceptanceWindowController acceptanceWindowController;
	private VBox vBoxAcceptanceWindow;
	private Stage stageAcceptanceWindow;

	public BattleshipView(String nick, String opponentNick, Client client, BattleshipManagement battleshipManagement) {
		this.nick = nick;
		this.opponentPlayerNick = opponentNick;
		this.client = client;
		this.battleshipManagement = battleshipManagement;
		loadMainScreenFXMLLoaderAndController();
	}

	private void loadMainScreenFXMLLoaderAndController() {
		mainScreenLoader = new FXMLLoader(this.getClass().getResource("/fxml/battleship/MainScreen.fxml"));
		vBoxMainScreen = null;
		try {
			vBoxMainScreen = mainScreenLoader.load();
		} catch (Exception e) {
		}
		mainScreenController = mainScreenLoader.getController();
		mainScreenController.setClient(this.client);
		mainScreenController.setOpponentPlayerNick(opponentPlayerNick);
	}

	public void showBattleshipWindow() {
		stageMainScreen = new Stage();
		Scene scene = new Scene(vBoxMainScreen);
		stageMainScreen.setTitle("Battleship: " + nick + " vs " + opponentPlayerNick);
		stageMainScreen.setScene(scene);
	
		stageMainScreen.show();
	}
	
	public void showAddedShipsBattleshipWindow() {
		
	}
	
	public void closeBattleshipWindow() {
		stageMainScreen.close();
	}

	private void loadWaitingWindow() {
		waitingWindowLoader = new FXMLLoader(
				this.getClass().getResource("/fxml/battleship/WaitingForAcceptanceWindow.fxml"));
		vBoxWaitingWindow = null;
		try {
			vBoxWaitingWindow = waitingWindowLoader.load();
		} catch (Exception e) {
		}
		waitingWindowController = waitingWindowLoader.getController();
	}

	public void showWaitingWindow(String message) {
		loadWaitingWindow();
	
		stageWaitingWindows = new Stage();
		Scene scene = new Scene(vBoxWaitingWindow);
		stageWaitingWindows.setScene(scene);
		waitingWindowController.setTextLabel(message);
		stageWaitingWindows.show();
	}
	
	public void closeWaitingWindow() {
		stageWaitingWindows.close();
	}
	
	private void loadAcceptanceWindow() {
		acceptanceWindowLoader = new FXMLLoader(this.getClass().getResource("/fxml/battleship/AcceptanceWindow.fxml"));
		vBoxAcceptanceWindow = null;
		try {
			vBoxAcceptanceWindow = acceptanceWindowLoader.load();
		} catch (Exception e) {
		}
		acceptanceWindowController = acceptanceWindowLoader.getController();
		acceptanceWindowController.setBattleshipManagement(battleshipManagement);
		acceptanceWindowController.setClient(client);
		acceptanceWindowController.setOpponentPlayerNick(opponentPlayerNick);
	}

	public void showAcceptanceWindow() {
		loadAcceptanceWindow();

		Platform.runLater(() -> {
			stageAcceptanceWindow = new Stage();
			Scene scene = new Scene(vBoxAcceptanceWindow);
			stageAcceptanceWindow.setScene(scene);
			acceptanceWindowController.setTextLabel("Otrzymałeś propozycję gry od użytkownika " + opponentPlayerNick);
			acceptanceWindowController.setOpponentPlayerNick(opponentPlayerNick);
			stageAcceptanceWindow.show();
		});
	}
	
	public void closeAcceptanceWindow() {
		stageAcceptanceWindow.close();
	}

	public MainScreenController getMainScreenController() {
		return mainScreenController;
	}
}
