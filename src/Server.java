import java.io.FileOutputStream;
import java.io.*;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class Server
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket s = new ServerSocket(2222);
        while(true)
        {
            System.out.println("Listening for Connection...");
            Socket client = s.accept(); //blocks
            ChatWorkerThread t = new ChatWorkerThread(client);
            t.start();
        }
    }

//    public static void main(String[] args) throws IOException
//    {
//        String folder = "D:\\Documents\\";
//        Scanner s = new Scanner(System.in);
//        System.out.println("Please enter the File Name: ");
//        String FileName = s.nextLine();
//        String path = folder + FileName + ".txt";
//
//        byte[] array = FilePathToByteArray(path);
//
//        System.out.println("Please enter the New File Name: ");
//
//        WriteToFile(array, s.nextLine());
//    }

    public static void WriteToFile(byte[] array, String FileName) throws IOException
    {
        FileOutputStream outputStream = new FileOutputStream("D:\\Documents\\" + FileName + ".txt");
        outputStream.write(array);
    }

    public static byte[] FilePathToByteArray(String path) throws IOException
    {
        File file = new File(path);

        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int)file.length()];
        fl.read(arr);
        fl.close();
        return arr;
    }
}
