
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

public class ChatWindow extends JFrame {
    private final int width_screen = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int height_screen = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int window_width = 400;
    private final int window_height = 400;

    private JTextArea jtarea;
    private JTextField jtfield;
    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;
    private Thread thr;
    private Boolean isAuthorized;
    private String myNick;
    private LogWindow log;
    private Reg_new_user rgnew;
    private JLabel label;
    private JPanel jpanUser;
    private static List<String> labels = new ArrayList<String>();
    private Font fontUser;

    public ChatWindow() {

        //настройка общего окна чата
        setBounds((width_screen - window_width) / 2, (height_screen - window_height) / 2, window_width, window_height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        thr = null;
        isAuthorized = false;
        setLayout(new BorderLayout());

        //добавляем верхнее меню
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        //добавляем выпадающий список
        JMenu helpmenu = new JMenu("Помощь");
        helpmenu.addSeparator();
        jMenuBar.add(helpmenu);
        JMenuItem menureference = new JMenuItem("Справка");
        helpmenu.add(menureference);

        //настройка текстового поля чата с сообщениями от пользователей
        jtarea = new JTextArea();
        jtarea.setEditable(false);
        jtarea.setLineWrap(true);
        JScrollPane jspane = new JScrollPane(jtarea);
        add(jspane, BorderLayout.CENTER);

        //панель для активных пользователей
        jpanUser = new JPanel();
        add(jpanUser, BorderLayout.EAST);

        //панель кнопки "отправить" и текстового поля для ввода текста
        JPanel bottom = new JPanel(new BorderLayout());
        jtfield = new JTextField(25);
        JButton sendMSGb = new JButton("отправить");
        bottom.add(jtfield, BorderLayout.CENTER);
        bottom.add(sendMSGb, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        // Уточняем размеры панели активных пользователей
        jpanUser.setPreferredSize(sendMSGb.getPreferredSize());

        //действие при нажатии Enter в текстовом поле для отправки сообщения пользователям
        jtfield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMSG();
            }
        });

        //действие при нажатии кнопки "отправить" для отправки сообщения пользователям
        sendMSGb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMSG();
            }
        });

        //действие по нажатии "справка" - открывается окно справки
        menureference.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuReferenceWindow mrw = new MenuReferenceWindow();
                mrw.setVisible(true);
            }
        });

        //действие при закрытии чата, закрытие потока
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {

                try {
                    s.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    //Указываем ник пользователя в шапке чата
    public void setMyNick(String myNick) {
        this.myNick = myNick;
        setTitle("Пользователь [ " + myNick + " ]");
    }
    //получаем никнейм пользователя
    public String getMyNick() {
        return myNick;
    }

    public void connect(JTextField txtlog, JPasswordField txtpass, JTextField txtnick) {
        if (s == null || (s != null && s.isClosed())) {
            try {
                s = new Socket("127.0.0.1", 8189);
                in = new DataInputStream(s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());
                //подключение зарегистрированного пользователя в чат(проверка логина пароля)
                if (txtnick == null) {
                    out.writeUTF("/auth" + " " + txtlog.getText() + " " + new String(txtpass.getPassword()));

                //подключение для регистрации нового пользователя
                } else if (txtnick != null) {
                    out.writeUTF("/reg" + " " + txtlog.getText() + " " + new String(txtpass.getPassword()) + " "
                            + txtnick.getText());
                }
            } catch (IOException e) {

                e.printStackTrace();
            }

            if (thr == null) {
                thr = new Thread(() -> {

                    String str = null;
                    try {
                        while (true) {
                            str = in.readUTF();
                            if (str != null) {
                                //действия если  для авторизация нового пользователя
                                if (!isAuthorized) {
                                    // проверка на при подключении нового пользователя
                                    if (str.startsWith("/yes")) {
                                        String[] w = str.split("\\s");
                                        setMyNick(w[1]);
                                        isAuthorized = true;
                                        continue;
                                    //обновление панели чата с активными пользователя при подключении к серверу
                                    } else if (str.startsWith("/listuser")) {
                                        reloadpanel(str);
                                    //проверка на логин и пароль при входе в чат
                                    } else if (str.equals("stop")) {
                                        isAuthorized = false;
                                        log = new LogWindow();
                                        setVisible(false);
                                        JOptionPane.showMessageDialog(this, "Неправильно введен логин или пароль");
                                        thr = null;
                                        s.close();
                                        break;
                                    //проверка логина при входе пользователя в чат, если есть такой, система не пустит.
                                    } else if (str.equals("NickBusy")) {
                                        isAuthorized = false;
                                        log = new LogWindow();
                                        setVisible(false);
                                        JOptionPane.showMessageDialog(this, "Логин:  " + txtlog.getText() + " уже авторизирован");
                                        thr = null;
                                        s.close();
                                        break;
                                    //проверка логина в чате при регистрации нового пользователя
                                    } else if (str.equals("StopUser")) {
                                        rgnew = new Reg_new_user();
                                        rgnew.setVisible(true);
                                        JOptionPane.showMessageDialog(this, "Пользователь с таким логином существует в базе");
                                        isAuthorized = false;
                                        thr = null;
                                        s.close();
                                        break;
                                    //проверка ника в чате при регистрации нового пользователя
                                    } else if (str.equals("StopNick")) {
                                        rgnew = new Reg_new_user();
                                        rgnew.setVisible(true);
                                        JOptionPane.showMessageDialog(this, "Пользователь с таким ником существует в базе");
                                        isAuthorized = false;
                                        thr = null;
                                        s.close();
                                        break;
                                    }
                                }
                                //действия если авторизированный пользователь
                                if (isAuthorized) {
                                    //сообщение в текстовое поле чата(общий)
                                    if (!str.startsWith("/listuser")) {
                                        jtarea.append(str + " " + '\n');
                                    }
                                    jtarea.setCaretPosition(jtarea.getDocument().getLength());
                                    System.err.print(str + " ");

                                    //команда для выхода из чата(прописывается в текстовом поле чата)
                                    if (str.equals("end")) break;
                                    //команда для изменения ника в чате(прописывается в текстовом поле чата)
                                    if (str.startsWith("/nickchanged")) {
                                        String[] w = str.split("\\s");
                                        setMyNick(w[1]);
                                        continue;
                                    }
                                    //обновление панели активных пользователей при подключении нового пользователя
                                    if (str.startsWith("/listuser")) {
                                        reloadpanel(str);
                                    }
                                    //обновление панели активных пользователей при изменении никнейма пользователя
                                    if (str.startsWith("/listuserchang")){
                                        reloadpanel(str);
                                    }
                                }

                            }
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException ex) {
                        //при отключении сервера данный метод используется для переподключения пользователя
                        reconnect();
                    }

                });
                thr.start();
            }

        }
    }

    //метод для отправки сообщений
    public void sendMSG() {
        try {
            out.writeUTF(jtfield.getText());
            out.flush();
            jtfield.setText("");
            jtfield.grabFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println();


    }

    //метод для переподключения пользователя к серверу
    public void reconnect() {
        try {
            isAuthorized = false;
            s.close();
            thr = null;
            log = new LogWindow();
            setVisible(false);
            setMyNick("");
            JOptionPane.showMessageDialog(this, "Сбой подключения, необходимо повторно авторизироваться!");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //метод для обновления данныых об активных пользователях
    public void reloadpanel(String user) {
        fontUser = new Font(null, Font.BOLD, 20);
        jpanUser.removeAll();
        //String text = "Пользов.";
        String[] w = user.split("\\s");
        //w[0] = text;
        for (int i = 1; i < w.length; i++) {
            label = new JLabel(w[i]);
            label.setVerticalAlignment(JLabel.NORTH);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(fontUser);
            jpanUser.add(label);
        }
        label.setPreferredSize(jpanUser.getPreferredSize());
        jpanUser.repaint();
        jpanUser.revalidate();
        jpanUser.validate();

    }
}
