import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.awt.event.KeyEvent;
import java.io.File;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.*;

public class QuickFilterApp extends  Application implements EventHandler {

    ComboBox combo_box = new ComboBox();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    List<String> name = new ArrayList<>();
    List<String> finalName = new ArrayList<>();
    String kw;

    public static void main(String[] args) {
        launch((args));
    }

    @Override
    public void start(Stage stage) throws Exception {

        Label label = new Label ("Search something here");

        combo_box.setMaxSize(420,200);
        combo_box.setEditable(true);
        combo_box.setOnKeyReleased(e->searchName());

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem loadItem = new MenuItem("Load");

        menuBar.getMenus().addAll(fileMenu);
        fileMenu.getItems().addAll(loadItem);
        loadItem.setOnAction(e-> loadFile());

        VBox layout = new VBox(menuBar);
        // layout.setPadding(new Insets(20));
        layout.getChildren().addAll(label, combo_box);
        Scene scene = new Scene (layout, 420, 300);
        stage.setTitle("Quick Filtering");
        stage.setScene(scene);
        stage.show();

    }

    private void loadFile() {
        name.clear();
        FileChooser fileChooser = new FileChooser();
        File response = fileChooser.showOpenDialog(null);
        if (response != null){
            try{
                File myObj = new File(String.valueOf(response));
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()){
                    String data =  myReader.nextLine();
                    name.add(data);
                }
                combo_box.setItems(FXCollections.observableArrayList(name));
                myReader.close();
            } catch (FileNotFoundException e){
                alert.setHeaderText(null);
                alert.setContentText("File Not Found");
                alert.showAndWait();
            }
        } else
            return;
    }

    private void searchName(){
        if (name.isEmpty()){
            alert.setHeaderText(null);
            alert.setContentText("Please Load File");
            alert.showAndWait();
            return;
        }
        String kw = combo_box.getEditor().getText();
        finalName.clear();
        Iterator iter = name.iterator();
        while (iter.hasNext()){
            String temp = (String) iter.next();
            String splited [] = temp.trim().split("\\s+");          // take the first letter of name
            String shortname = new String();
            for (int i=0; i<splited.length; i++){
                shortname += splited[i].charAt(0);
            }
            if (kw.compareToIgnoreCase(temp) == 0 || kw.compareToIgnoreCase(shortname) == 0){
                finalName.add(temp);
            }
            String lower = temp.toLowerCase();    //Ignore Case to find word
            kw = kw.toLowerCase();
            if (lower.contains(kw)){
                finalName.add(temp);
            }
        }
        if (! finalName.isEmpty()){
            combo_box.setItems(FXCollections.observableArrayList(finalName));
        }
    }

    @Override
    public void handle(Event event) {

    }
}


