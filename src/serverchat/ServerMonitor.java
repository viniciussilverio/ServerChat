package serverchat;

import java.awt.BorderLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ServerMonitor extends javax.swing.JFrame {

    ServerManager serverManager;
    ServerStatusListener statusListener;
    ClientListener clientListener;
    static HashMap<String, String> hm = new HashMap<String, String>();

    //Form do servidor
    public ServerMonitor(ServerManager getManager) {
        serverManager = getManager;
        statusListener = new MyStatusListener();
        clientListener = new MyClientListener();
        initComponents();
        createListModel();
    }

    void createListModel() {
        list_model = new javax.swing.DefaultListModel();
        list_online_clients = new javax.swing.JList(list_model);
        list_online_clients.setBorder(javax.swing.BorderFactory.createTitledBorder("Clientes Online"));

        dlsm = new DefaultListSelectionModel();
        dlsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list_online_clients.setSelectionModel(dlsm);
        list_panel.setLayout(new BorderLayout());
        list_panel.add(list_online_clients);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_monitor_clients = new javax.swing.JTextArea();
        list_panel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        but_start = new javax.swing.JButton();
        but_stop = new javax.swing.JButton();
        lb_status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatServer");
        setIconImage(new ImageIcon(ServerMonitor.class.getResource("/media/icone-1.png")).getImage());

        ta_monitor_clients.setColumns(20);
        ta_monitor_clients.setEditable(false);
        ta_monitor_clients.setRows(5);
        ta_monitor_clients.setBorder(javax.swing.BorderFactory.createTitledBorder("MONITOR DE CLIENTES"));
        jScrollPane1.setViewportView(ta_monitor_clients);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout list_panelLayout = new javax.swing.GroupLayout(list_panel);
        list_panel.setLayout(list_panelLayout);
        list_panelLayout.setHorizontalGroup(
                list_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 118, Short.MAX_VALUE)
        );
        list_panelLayout.setVerticalGroup(
                list_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 233, Short.MAX_VALUE)
        );

        but_start.setText("Iniciar");
        but_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_startActionPerformed(evt);
            }
        });

        but_stop.setText("Parar");
        but_stop.setEnabled(false);
        but_stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_stopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(but_start)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(but_stop)
                                .addContainerGap(259, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(but_start)
                                        .addComponent(but_stop))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lb_status.setFont(new java.awt.Font("Tahoma", 1, 14));
        lb_status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_status.setText("O servidor está parado");
        lb_status.setBorder(javax.swing.BorderFactory.createTitledBorder("Status do Servidor"));
        lb_status.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(list_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb_status, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(list_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private void but_startActionPerformed(java.awt.event.ActionEvent evt) {
        but_stop.setEnabled(true);
        but_start.setEnabled(false);
        serverManager.startServer(statusListener, clientListener);

    }

    private void but_stopActionPerformed(java.awt.event.ActionEvent evt) {
        String texto = "O projeto foi desenvolvido por alunos do 5º SEM de Ciência da Computação\n"
                + "da UNIP Swift no ano de 2018.\n\n"
                + "Integrantes:\n"
                + "Jéssica Santos da Cruz \n"
                + "RA: C84737-2\n"
                + "E-mail: jessicasantos_cruz@hotmail.com\n\n"
                + "Vinícius de Jesus Benedito Silvério\n"
                + "RA: C98596-1\n"
                + "E-mail: vinos.vinicius@gmail.com\n\n"
                + "Yan Luccas Oliveira  \n"
                + "RA: C9625C-7\n"
                + "E-mail: yan.hbk@hotmail.com \n\n"
                + "Github (Cliente): https://github.com/viniciussilverio/EcoChat/ \n"
                + "Github (Server): https://github.com/viniciussilverio/ServerChat";
        JOptionPane.showMessageDialog(null, texto, "Sobre a Equipe", 1);
        serverManager.stopServer(statusListener);
        but_stop.setEnabled(false);
        but_start.setEnabled(true);
    }

    class MyStatusListener implements ServerStatusListener {

        public void status(String message) {
            lb_status.setText(message);
        }
    }

    class MyClientListener implements ClientListener {

        public void signIn(String userName) {
            list_model.addElement((Object) userName);
        }

        public void signOut(String userName) {
            list_model.removeElement((Object) userName);
        }

        public void clientStatus(String status) {
            ta_monitor_clients.append(status + "\n");
        }

        public void mapped(String nam, String ip) {
            if (hm.get(nam) == null) {

                hm.put(nam, ip);
                System.out.println(nam + " " + ip);
            }
        }
    }

    public javax.swing.JButton but_start;
    private javax.swing.JButton but_stop;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lb_status;
    private javax.swing.JPanel list_panel;
    public javax.swing.JTextArea ta_monitor_clients;
    public javax.swing.DefaultListModel list_model;
    public javax.swing.DefaultListSelectionModel dlsm;
    public javax.swing.JList list_online_clients;
}
