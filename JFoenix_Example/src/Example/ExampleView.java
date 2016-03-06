package Example;

import Design.View;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by alex on 3/5/2016.
 */
public final class ExampleView implements View
{
    private final JPanel panel = new JPanel(new MigLayout());
    private final JButton button;
    private final JCheckBox checked;
    private final JComboBox<String> combo;
    private final JTextField textField;
    private final JRadioButton radioButton1;
    private final JRadioButton radioButton2;
    private final JRadioButton radioButton3;
    private final JTextArea textArea;
    private final JFXPanel fxPanel = new JFXPanel();

    public ExampleView()
    {
        radioButton1 = new JRadioButton("Radio Button 1");
        radioButton2 = new JRadioButton("Radio Button 2");
        radioButton3 = new JRadioButton("Radio Button 3");
        button = new JButton("Button");
        checked = new JCheckBox("Checkable checky thing");
        combo = new JComboBox<String>(new String[] {"Hello", "Goodbye", "Good Morning", "Good Afternoon", "Good night", ":)"});
        textField = new JFormattedTextField();
        textArea = new JTextArea("Type stuff here");
        textArea.setBorder(new TitledBorder("Title Border"));

        filePanel();
        setListeners();
    }

    private void filePanel()
    {
        panel.add(new JLabel("Hello click the button!"), "cell 0 0");
        panel.add(button, "wrap");
        panel.add(checked, "wrap");
        panel.add(combo, "wrap");
        textField.setMinimumSize(new Dimension(150, 10));
        panel.add(textField, "wrap");
        panel.add(radioButton1);
        panel.add(radioButton2);
        panel.add(radioButton3, "wrap");
        panel.add(textArea);
    }

    private void setListeners()
    {
        radioButton1.addActionListener(new AbstractAction()
           {
               @Override
               public void actionPerformed(ActionEvent e)
               {
                   if(radioButton1.isSelected())
                   {
                       radioButton2.setSelected(false);
                       radioButton3.setSelected(false);
                   }
               }
           }
        );

        radioButton2.addActionListener(new AbstractAction()
           {
               @Override
               public void actionPerformed(ActionEvent e)
               {
                   if(radioButton2.isSelected())
                   {
                       radioButton1.setSelected(false);
                       radioButton3.setSelected(false);
                   }
               }
           }
        );

        radioButton3.addActionListener(new AbstractAction()
           {
               @Override
               public void actionPerformed(ActionEvent e)
               {
                   if(radioButton3.isSelected())
                   {
                       radioButton1.setSelected(false);
                       radioButton2.setSelected(false);
                   }
               }
           }
        );

        button.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(textField.getText().isEmpty())
                    textField.setText((String)combo.getSelectedItem());
            }
        });
    }

    protected Component getComponent()
    {
        return panel;
    }
}
