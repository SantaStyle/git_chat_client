import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuReferenceWindow extends JFrame {
    private final int width_screen = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int height_screen = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int window_width = 400;
    private final int window_height = 400;
    public MenuReferenceWindow() {
        //настройка общего окна справки
        setTitle("Справка");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds((width_screen)/6,(height_screen)/4,window_width,window_height);

        // Настраиваем вертикальную панель
        Box box = Box.createVerticalBox();
        JLabel loginLabel1 = new JLabel("Дополнительные возможности для пользователя:");
        JLabel loginLabel12 = new JLabel(" ");
        JLabel loginLabel2 = new JLabel("Команда для изменения никнейма: в текстовом поле чата необходимо написать /changenick + новый никнейм");
        JLabel loginLabel3 = new JLabel("Пример изменения никнейма: /changenick newnick");
        JLabel loginLabel13 = new JLabel(" ");
        JLabel loginLabel4 = new JLabel("Команда для выхода из чата: в текстовом поле чата необходимо написать /end");
        JLabel loginLabel14 = new JLabel(" ");
        JLabel loginLabel5 = new JLabel("Команда для отправки сообщения определенному пользователю: в текстовом поле чата необходимо написать /pm + nick + текст сообщения");
        JLabel loginLabel6 = new JLabel("Пример отправки сообщения определенному пользователю: /pm nick1 привет");

        //Размещаем горизонтальные панели на одной вертикальной
        box.add(loginLabel1);
        box.add(Box.createHorizontalStrut(6));
        box.add(loginLabel12);
        box.add(Box.createHorizontalStrut(6));
        box.add(loginLabel2);
        box.add(Box.createHorizontalStrut(6));
        box.add(loginLabel3);
        box.add(Box.createHorizontalStrut(6));
        box.add(loginLabel13);
        box.add(Box.createHorizontalStrut(6));
        box.add(loginLabel4);
        box.add(Box.createHorizontalStrut(6));
        box.add(loginLabel14);
        box.add(Box.createHorizontalStrut(6));
        box.add(loginLabel5);
        box.add(Box.createHorizontalStrut(6));
        box.add(loginLabel6);
        box.add(Box.createHorizontalStrut(6));

        // Размещаем вертикальные панели на одной вертикальной
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12,12,12,12));
        mainBox.add(box);
        setContentPane(mainBox);
        pack();
        setResizable(false);
    }
}
