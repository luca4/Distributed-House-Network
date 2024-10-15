package core;

import pack.data.HouseInfo;
import pack.communication.RestStub;
import pack.communication.SocketStub;
import pack.data.Measures;

import java.util.NoSuchElementException;
import java.util.Scanner;

// Allows user to use "Exit" and "Boost" commands
public class Console extends Thread{

    private Measures measures = Measures.getInstance();
    private final String errorMessage = "Errore: inserisci i comandi corretti";

    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Commands:\nexit: termina la casa\nboost: inizia il periodo di boost\n");
            String line = null;
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println(errorMessage);
                continue;
            }

            if (line.equals("exit") || line.equals("e")) {
                // The program can terminate only after sending the "Exit" message
                Utils.setCanTerminate(false);

                RestStub.deleteHouseFromServer();
                SocketStub.exitFromNetwork();
                measures.removeMeasure(HouseInfo.getHouseId());

                Utils.terminate();

                System.exit(0);
            } else if (line.equals("boost") || line.equals("b")) {
                if (ResourceManager.getInstance().isUsingResource())
                    System.out.println("Boost già in uso");
                else
                    ResourceManager.getInstance().obtainResource();
            } else
                System.err.println(errorMessage);
        }
    }
}
