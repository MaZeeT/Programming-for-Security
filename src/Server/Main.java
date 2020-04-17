package Server;

import UI.TerminalUI;

public class Main {
    public static void main(String[] args) throws Exception {
        String username = "ServerTerminal";
        TerminalUI ui = new TerminalUI(username);

        ChatServer server = new ChatServer(3000);
    }

}
