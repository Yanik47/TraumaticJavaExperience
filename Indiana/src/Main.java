
public class Main {
    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.setAll();
        Indiana newIndiana = new Indiana();
        newIndiana.setPlayerController(controller);
        newIndiana.underwaterMovesAllowed(5);
        newIndiana.findExit();

    }
}