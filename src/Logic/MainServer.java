package Logic;

import UI.TerminalUI;

public class MainServer {
    public static void main(String[] args) throws Exception {
        String username = "ServerTerminal";
        TerminalUI ui = new TerminalUI(username);

        ServerLogic server = new ServerLogic(3000);
    }

}
