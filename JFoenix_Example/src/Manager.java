import Example.ExampleController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alex on 3/5/2016.
 */
public class Manager extends JFrame
{
    public static void run() throws Exception
    {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Manager manager = new Manager();
                manager.setVisible(true);
            }
        });
    }

    private static final int width = 300;
    private static final int height = 300;

    public Manager() {

        initUI();
    }

    private void initUI() {
        createLayout();

        setTitle("Manager");
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createLayout()
    {
        ExampleController controller = new ExampleController();
        Container pane = getContentPane();
        pane.add(controller.getComponent());
        pane.setLayout(new GridLayout());
        pack();
    }
}
