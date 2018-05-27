
package serverchat;

public class ServerChat {
public static void main(String[] args)
    {
        ServerManager serverManager=new ServerManager();
        ServerMonitor monitor=new ServerMonitor(serverManager);

        monitor.setVisible(true);
    }
}
