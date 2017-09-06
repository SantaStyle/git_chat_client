import javax.swing.*;
import java.awt.*;

/*
класс настройки текстового поля
 */
public class MyTextField extends JTextField {
    private String hint;
    private static Font hintFont = new Font("Arial",Font.PLAIN,14);

    public MyTextField(String hint){
        this.hint = hint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(getText().isEmpty()){
            g.setColor(Color.blue);
            g.setFont(hintFont);
            g.drawString(hint,3,17);
        }
    }
}
