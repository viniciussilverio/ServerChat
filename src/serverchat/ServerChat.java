
package serverchat;

public class ServerChat {
public static void main(String[] args)
    {
        MessengSrserver.ServerManager serverManager=new MessengSrserver.ServerManager();
        MessengSrserver.ServerMonitor monitor=new MessengSrserver.ServerMonitor(serverManager);

        monitor.setVisible(true);
    }
}
