import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reg_new_user extends JFrame {
    private final int width_screen = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int height_screen = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int window_width = 400;
    private final int window_height = 400;
    private ChatWindow ch;

    public Reg_new_user() throws HeadlessException {
        //настройка общего окна регистрации нового пользователя
        setTitle("Регистрация пользователя");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds((width_screen-window_width)/2,(height_screen-window_height)/2,window_width,window_height);

        // Настраиваем горизонтальную панель (для ввода нового логина)
        Box box1 = Box.createHorizontalBox();
        JLabel loginLabel = new JLabel("Логин:");
        JTextField txtlogin = new JTextField(15);
        box1.add(loginLabel);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(txtlogin);
        // Настраиваем горизонтальную панель (для ввода нового пароля)
        Box box2 = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Пароль:");
        JPasswordField txtpassword = new JPasswordField(15);
        box2.add(passwordLabel);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(txtpassword);
        // Настраиваем горизонтальную панель (для ввода нового никнейма)
        Box box3 = Box.createHorizontalBox();
        JLabel nicklabel = new JLabel("Nick: ");
        JTextField txtnick = new JTextField(15);
        box3.add(nicklabel);
        box3.add(Box.createHorizontalStrut(6));
        box3.add(txtnick);
        // Настраиваем горизонтальную панель (с кнопками)
        Box box4 = Box.createHorizontalBox();
        JButton reg = new JButton("Зарегистрировать");
        JButton cancel = new JButton("Отмена");
        box4.add(Box.createHorizontalGlue());
        box4.add(reg);
        box4.add(Box.createHorizontalStrut(10));
        box4.add(cancel);
        // Уточняем размеры логина и никнейма
        loginLabel.setPreferredSize(passwordLabel.getPreferredSize());
        nicklabel.setPreferredSize(passwordLabel.getPreferredSize());
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

        //действие при нажатии кнопки "отмена" - скрывается панель регистрации
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        //действие при нажатии кнопки "Зарегистрировать" - подключаемся к серверу, проходим проверку логина, никнейма.
        reg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ch = new ChatWindow();
                ch.connect(txtlogin,txtpassword,txtnick);
                setVisible(false);
            }
        });

        setResizable(false);
    }
}
