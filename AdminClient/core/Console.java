package core;

import org.apache.commons.io.input.CloseShieldInputStream;

import java.util.InputMismatchException;
import java.util.Scanner;

// Manage user input and interaction
public class Console {

    public Console()
    {
        Stub stub = new Stub();

        while(true)
        {
            initMessage();
            int userInput = getIntegerValue(1, 6);

            switch (userInput) {
                case 1: {
                    stub.viewAllHouses();
                    break;
                }
                case 2: {
                    stub.getNHouseStat(getHouseId(), getN());
                    break;
                }
                case 3: {
                    stub.getNComplexStat(getN());
                    break;
                }
                case 4: {
                    stub.calculateNHouseStat(getHouseId(), getN());
                    break;
                }
                case 5: {
                    stub.calculateNComplexStat(getN());
                    break;
                }
                case 6:
                    System.exit(0);
            }
        }
    }

    private long getHouseId()
    {
        System.out.println("Inserisci l'id della casa:");
        return getIntegerValue(1, -1);
    }

    private int getN()
    {
        System.out.println("Inserisci il numero di ultime statistiche da considerare:");
        return getIntegerValue(1, -1);
    }

    private void initMessage()
    {
        System.out.println("\nSeleziona un'opzione:");
        System.out.println("1 - Visualizza elenco di case presenti");
        System.out.println("2 - Visualizza le ultime n statistiche di una casa");
        System.out.println("3 - Visualizza le ultime n statistiche condominiali");
        System.out.println("4 - Visualizza deviazione standard e media di n statistiche prodotte da una casa");
        System.out.println("5 - Visualizza deviazione standard e media di n statistiche condominiali");
        System.out.println("6 - Esci");
    }

    ///min o max = -1 means unlimited values
    private int getIntegerValue(int min, int max)
    {
        Scanner in = new Scanner(new CloseShieldInputStream(System.in));
        int input = 0;
        boolean gate = false;

        do {
            gate = false;
            try{
                input = in.nextInt();
            }
            // check int type
            catch(NumberFormatException | InputMismatchException e)
            {
                System.out.println("Valore non consentito, inserisci un numero intero\n");
                gate = true;
                in.next();
                continue;
            }

            // input range checks
            if(!(min == -1) && input < min)
            {
                System.out.println("Valore non consentito, dev'essere almeno "+min+"\n");
                gate = true;
            }

            if(!(max == -1) && input > max)
            {
                System.out.println("Valore non consentito, dev'essere al massimo "+max+"\n");
                gate = true;
            }

        }while(gate);

        in.close();

        return input;
    }

}
