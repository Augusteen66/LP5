import java.util.*;

public class bully{

    static boolean state[] = new boolean[5];

    public static void up(int up) {

        if (state[up - 1] = true) {
            System.out.println("Process " + up + " already up!");
        } else {
            state[up - 1] = true;
            System.out.println("Process " + up + " holding election!");
            for (int i = up; i < 5; i++) {
                System.out.println("Process " + up + " sending election message to process " + (i+1));
            }
            for (int i = up + 1; i < 5; i++) {
                if (state[i - 1] == true) {
                    System.out.println("Process " + i + " sending alive message to " + up);
                    break;
                }
            }
        }
    }

    public static void down(int down) {

        if (state[down - 1] == false) {
            System.out.println("Process " + down + " already down!");
        } else {
            state[down - 1] = false;
        }
    }

    public static void mess(int mess) {

        if (state[mess -1] == true) {

            if (state[4] == true) {
                System.out.println("OK!");
            } else {
                System.out.println("process"+mess+"election");
                for (int i = mess; i < 5; i++) {
                    System.out.println("Process " + mess + " sending election message to " + (i+1));
                }
                for (int i = 5; i >= mess; i--) {
                    if (state[i-1] == true) {
                        System.out.println("Coordinator message send from process"+i+"to all");
                        break;
                    }
                }
            }

        } else {
            System.out.println("Prccess"+mess+"is down");
        }

    }

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        int choice;
        for(int i=0;i<5;i++)
        {
            state[i] = true;
        }
        System.out.println("5 active process are:");
        System.out.println("Process up  = p1 p2 p3 p4 p5");
        System.out.println("Process 5 is coordinator");

        do {
            System.out.println(".........");
            System.out.println("1 up a process.");
            System.out.println("2.down a process");
            System.out.println("3 send a message");
            System.out.println("4.Exit");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                {
                    System.out.println("bring proces up");
                    int up = sc.nextInt();
                    if(up==5)
                    {
                        System.out.println("process 5 is co-ordinator");
                        state[4] = true;

                    }
                    else
                    {
                        up(up);
                    }
                } break;

                case 2:

                {
                    System.out.println("bring down any process.");
                    int down = sc.nextInt();
                    down(down);

                }
                break;

                case 3: {
                    System.out.println("which process will send message");
                    int mess = sc.nextInt();
                    mess(mess);
                }
                break;
                }


            }
        while(choice!=4);
        sc.close();

    }

}