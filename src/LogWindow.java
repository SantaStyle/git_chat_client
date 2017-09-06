import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogWindow extends JFrame{
    private final int width_screen = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int height_screen = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int window_width = 400;
    private final int window_height = 400;
    private JTextField txtlog;
    private JPasswordField txtpass;
    private final JFrame frame = this;
    private JTextField nick = null;
    private ChatWindow chatWindow;

    public LogWindow() {
        //настройка общего окна входа в чат
        setTitle("Вход в чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds((width_screen-window_width)/2,(height_screen-window_height)/2,window_width,window_height);

        //добавляем верхнее меню
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        //добавляем выпадающий список файл
        JMenu menufile = new JMenu("Файл");
        menufile.addSeparator();
        jMenuBar.add(menufile);
        JMenuItem jminew = new JMenuItem("Регистрация");
        menufile.add(jminew);
        JMenuItem jmiexit = new JMenuItem("Выход");
        menufile.add(jmiexit);

        //добавляем выпадающий список
        JMenu helpmenu = new JMenu("Помощь");
        helpmenu.addSeparator();
        jMenuBar.add(helpmenu);
        JMenuItem menureference = new JMenuItem("Справка");
        helpmenu.add(menureference);

        // Настраиваем горизонтальную панель (для ввода логина)
        Box box1 = Box.createHorizontalBox();
        JLabel loginLabel = new JLabel("Логин:");
        txtlog = new JTextField(15);
        box1.add(loginLabel);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(txtlog);

        // Настраиваем горизонтальную панель (для ввода пароля)
        Box box2 = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Пароль:");
        txtpass = new JPasswordField(15);
        box2.add(passwordLabel);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(txtpass);

        // Настраиваем горизонтальную панель (с кнопками)
        Box box3 = Box.createHorizontalBox();
        JButton ok = new JButton("Войти");
        JButton cancel = new JButton("Выход");
        box3.add(Box.createHorizontalGlue());
        box3.add(ok);
        box3.add(Box.createHorizontalStrut(35));
        box3.add(cancel);

        // Настраиваем горизонтальную панель (с кнопкой регистрация)
        Box box4 = Box.createHorizontalBox();
        JButton regNewUser = new JButton("Регистрация");
        box4.add(Box.createHorizontalGlue());
        box4.add(regNewUser);

        // Уточняем размеры компонентов
        loginLabel.setPreferredSize(passwordLabel.getPreferredSize());

        // Размещаем горизонтальные панели на одной вертикальной
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12,12,12,12));
        mainBox.add(box1);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box2);
        mainBox.add(Box.createVerticalStrut(17));
        mainBox.add(box3);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(box4);
        setContentPane(mainBox);
        pack();
        setResizable(false);

        //действие по нажатии кнопки "регистрация" - открывается окно регистрации
        regNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reg_new_user rg = new Reg_new_user();
                rg.setVisible(true);
            }
        });

        //действие по нажатии кнопки "ок" - подключаемся к серверу и проходим проверку пользователя
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatWindow = new ChatWindow();
                chatWindow.connect(txtlog, txtpass, nick);
                chatWindow.setVisible(true);
                setVisible(false);
            }
        });

        //действие по нажатии кнопки "выход" - выход из программы
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //действие по нажатии Enter в поле "пароль" - действия аналогичные как и при нажатии кнопки ок
        txtpass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatWindow = new ChatWindow();
                chatWindow.connect(txtlog, txtpass, nick);
                chatWindow.setVisible(true);
                setVisible(false);
            }
        });

        //действие по нажатии кнопки "регистрация" - открывается окно регистрации
        jminew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reg_new_user rg = new Reg_new_user();
                rg.setVisible(true);
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

        //действие по нажатии кнопки "выход" - выход из программы
        jmiexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);

    }

}