import java.io.PrintStream;
import java.util.ArrayList;

public class CORE
{
    private static ArrayList<PrintStream> theClientStreams = new ArrayList<PrintStream>();
    private static byte[] CurrentFile;

    public static void UploadFile(byte[] File)
    {
        CurrentFile = File;
    }

    public static byte[] DownloadFile()
    {
        return CurrentFile;
    }

    public static synchronized void addClientThreadPrintStream(PrintStream ps)
    {
        System.out.println("adding client thread");
        CORE.theClientStreams.add(ps);
    }

    public static synchronized void removeClientThreadPrintStream(PrintStream ps)
    {
        CORE.theClientStreams.remove(ps);
    }

    public static void broadcastMessage(String message)
    {
        System.out.println("About to broadcast....");
        for (PrintStream ps : CORE.theClientStreams)
        {
            ps.println(message);
        }
    }
}