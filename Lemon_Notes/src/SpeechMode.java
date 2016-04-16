import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by alex on 4/7/2016.
 */
public class SpeechMode extends Mode
{
    public SpeechMode()
    {
        super("Text To Speech", "<speak>", "Takes in words",
                "Converts text to speech", "<speak>Hello there</speak>", true);
    }

    @Override
    public Text preview(Text textAreaBoxData)
    {
        final ArrayList<String> textArray = new ArrayList<String>();
        Scanner sc = new Scanner(textAreaBoxData.getText());
        sc.useDelimiter("speak");
        while (sc.hasNext())
        {
            String text = sc.next();
            if(text.startsWith(">") && text.endsWith("</"))
            {
                text = text.substring(1, text.length() - 2);
                textArray.add(text);
            }
        }

        return textAreaBoxData;
    }
}
