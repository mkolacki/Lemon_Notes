import com.gtranslate.Audio;
import com.gtranslate.Language;

import java.io.InputStream;

/**
 * Created by alex on 4/7/2016.
 */
public class Translator extends Mode
{
    public Translator()
    {
        super("Language Translator", "<translate>", "Convert from one language to another",
                "Default translate will attempt to convert any other language to english", "<translate>Hola mis amigos</translate>", true);
    }

    @Override
    public void update(String textAreaBoxData)
    {
        System.out.println("Stuff!");
        try
        {
            com.gtranslate.Translator translate = com.gtranslate.Translator.getInstance();
            String text = translate.translate("I am programmer", Language.ENGLISH, Language.ROMANIAN);
            System.out.println(text);

        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
    }
}
