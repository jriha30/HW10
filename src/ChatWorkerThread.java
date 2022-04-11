import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatWorkerThread extends Thread
{
    private Socket theClientSocket;
    private PrintStream clientOutput;
    private Scanner clientInput;

    public ChatWorkerThread(Socket theClientSocket)
    {
        try
        {
            System.out.println("Connection Established...");
            this.theClientSocket = theClientSocket;
            this.clientOutput = new PrintStream(this.theClientSocket.getOutputStream());
            //System.out.println("About to add a printstream");
            CORE.addClientThreadPrintStream(this.clientOutput);

            this.clientInput = new Scanner(this.theClientSocket.getInputStream());
        }
        catch (Exception e)
        {
            System.err.println("Bad things happened in thread!!!!!");
            e.printStackTrace();
        }
    }

    public byte[] FilePathToByteArray(String path) throws IOException
    {
        File file = new File(path);

        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int)file.length()];
        fl.read(arr);
        fl.close();
        return arr;
    }

    public static void WriteToFile(byte[] array, String FilePath) throws IOException
    {
        FileOutputStream outputStream = new FileOutputStream(FilePath);
        outputStream.write(array);
    }

    public void run()
    {
        //this is what the thread does
        this.clientOutput.println("What is your name?");
        String name = clientInput.nextLine();
        CORE.broadcastMessage(name + " has joined!");

        String message;
        while(true)
        {
            message = clientInput.nextLine();
            if(message.equals("/quit"))
            {
                CORE.broadcastMessage(name + " has left the server!");
                CORE.removeClientThreadPrintStream(this.clientOutput);
                break;
            }
            else if(message.startsWith("/upload"))
            {
                try
                {
                    String path = message.split(" ")[1];
                    byte[] File = FilePathToByteArray(path);
                    CORE.UploadFile(File);
                }
                catch(Exception e)
                {

                }
            }
            else if(message.startsWith("/download"))
            {
                try
                {
                    String path = message.split(" ")[1];
                    WriteToFile(CORE.DownloadFile(), path);
                }
                catch(Exception e)
                {

                }
            }
            else
            {
                CORE.broadcastMessage(message);
            }
        }
    }
}