
package serverchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ServerManager implements MessageListener
{
    public static final int CLIENT_NUMBER=100;
    public static final String DISCONNECT_STRING="DISCONNECT";
    int SERVER_PORT;
    public static final int BACKLOG=100;
    public static final int MULTICAST_SENDING_PORT=5555;
    public static final int MULTICAST_LISTENING_PORT=5554;
    ExecutorService serverExeCutor;
    ServerSocket server;
    Socket clientSocket;
    Clients[] client;
    int clientNumber=0;
    static String[] clientTracker;
    String users="";

    public ServerManager()
    {
        client=new Clients[CLIENT_NUMBER];
        clientTracker=new String [CLIENT_NUMBER];
        serverExeCutor=Executors.newCachedThreadPool();
    }

    public void startServer(ServerStatusListener statusListener,ClientListener clientListener)
    {
        try
        {
            do{
            SERVER_PORT = Inteiro("Digite o número da Porta: ", "12345");
            }while(SERVER_PORT < 1 || SERVER_PORT > 65535 );
            statusListener.status("O servidor está escutando na porta: "+SERVER_PORT);

            server=new ServerSocket(SERVER_PORT,BACKLOG);
            serverExeCutor.execute(new ConnectionController(statusListener,clientListener));
        }
        catch(IOException ioe)
        {
            statusListener.status("Erro ao iniciar o servidor");
        }
    }

    public void stopServer(ServerStatusListener statusListener)
    {
        try 
        {
            server.close();
            statusListener.status("O servidor está parado");
        }
        catch(SocketException ex)
        {
            statusListener.status("Erro de Socket");
        }
        catch (IOException ioe)
        {
            statusListener.status("Erro de leitura");
        }
    }

    public void controllConnection(ServerStatusListener statusListener,ClientListener clientListener)
    {
        while(clientNumber<CLIENT_NUMBER)
        { 
            try
            {
                clientSocket= server.accept();
                client[clientNumber]=new Clients(clientListener,clientSocket,this,clientNumber);
                serverExeCutor.execute(client[clientNumber]);
                clientNumber++;
            }
            catch(SocketException ex)
            {
                ex.printStackTrace();
                break;
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                statusListener.status("Erro não especificado!!!");
                break;
            }
        }
    }

    public void sendInfo(String message)
    {
        StringTokenizer tokens=new StringTokenizer(message);
        String to=tokens.nextToken();

        for(int i=0;i<clientNumber;i++)
        {
            if(clientTracker[i].equalsIgnoreCase(to))
            {
                try
                {
                    client[i].output.writeObject(message);
                    client[i].output.flush();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void sendFile(String sendTo,int file)
    {
        for(int i=0;i<clientNumber;i++)
        {
            if(clientTracker[i].equalsIgnoreCase(sendTo))
            {
                try
                {
                    client[i].output.writeInt(file);
                    client[i].output.flush();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    
    // send all;

    public void sendNameToAll(String message)
    {
        for(int i=0;i<clientNumber;i++)
        {
            try
            {
                System.out.println("O servidor está enviando   "+ message);
                client[i].output.writeObject(message);
                client[i].output.flush();
            }
            catch (IOException ex)
            {}
        }
    }

    class ConnectionController implements Runnable
    {
        ServerStatusListener statusListener;
        ClientListener clientListener;

        ConnectionController(ServerStatusListener getStatusListener,ClientListener getClientListener)
        {
            statusListener=getStatusListener;
            clientListener=getClientListener;
        }

        public void run()
        {
            controllConnection(statusListener,clientListener);
        }
    }
    public static int Inteiro(String mensagem, String porta){
		ImageIcon icone = new ImageIcon(ServerMonitor.class.getResource("/media/icone-1.png"));
		int verifica = 0;
		int numero = 0;
		do{
			try{
				verifica = 1;
				numero = Integer.parseInt((String)JOptionPane.showInputDialog(null, mensagem, "Porta", JOptionPane.INFORMATION_MESSAGE, icone, null, porta));
			}catch(NumberFormatException e){
				verifica = 0;
				JOptionPane.showMessageDialog(null, "Opa, ocorreu um erro na leitura, tente digítar um valor numérico.");
			}
		}while (verifica == 0);
		return numero;
	}
}
