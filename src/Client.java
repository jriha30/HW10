import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        Socket s = new Socket("localhost", 2222);
        Scanner clientInput = new Scanner(s.getInputStream());
        String question = clientInput.nextLine();
        System.out.println(question);
        Scanner localInput = new Scanner(System.in);
        PrintStream clientOutput = new PrintStream(s.getOutputStream());

        Thread lt = new Thread() {
            public void run()
            {
                String line;
                while(true)
                {
                    line = clientInput.nextLine();
                    System.out.println(line);
                }
            }
        };

        lt.start();

        String line;
        while(true)
        {
            line = localInput.nextLine();
            clientOutput.println(line);
            if(line.equals("/quit"))
            {
                break;
            }
            else if(line.startsWith("/upload"))
            {
                System.out.println("File Uploaded");
            }
            else if(line.startsWith("/download"))
            {
                System.out.println("File Downloaded");
            }
        }
        System.out.println("Goodbye");
        System.exit(0);

    }
}