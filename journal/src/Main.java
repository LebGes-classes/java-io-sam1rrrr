import data.DataManager;
import ui.Menu;

public class Main {
    public static void main(String[] args) {
        DataManager.load();
        Menu.mainMenu();
        //System.out.println("Рабочая директория: " + System.getProperty("user.dir"));
    }
}
