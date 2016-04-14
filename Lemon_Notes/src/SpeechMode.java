import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alex on 4/7/2016.
 */
public class SpeechMode extends Mode
{
    private ExecutorService executor = Executors.newSingleThreadExecutor();
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
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                // listAllVoices();
                String voiceName = "kevin16";
                VoiceManager voiceManager = VoiceManager.getInstance();
                Voice helloVoice = voiceManager.getVoice(voiceName);
                if(helloVoice == null) {
                    System.err.println("Cannot find a voice named " + voiceName + ".  Please specify a different voice.");
                    System.exit(1);
                }

                helloVoice.allocate();
                for(String text: textArray)
                {
                    if(!text.equals(""))
                        helloVoice.speak(text);
                }
                helloVoice.deallocate();
            }
        });

        textAreaBoxData.setText(textAreaBoxData.getText().replaceAll("<speak>", "").replaceAll("</speak>", ""));
        return textAreaBoxData;
    }

    public static void listAllVoices() {
        System.out.println();
        System.out.println("All voices available:");
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();

        for(int i = 0; i < voices.length; ++i) {
            System.out.println("    " + voices[i].getName() + " (" + voices[i].getDomain() + " domain)");
        }

    }
}
