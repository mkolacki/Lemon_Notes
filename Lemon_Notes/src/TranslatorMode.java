import com.gtranslate.Language;
import javafx.scene.text.Text;

/**
 * Created by alex on 4/7/2016.
 */
public class TranslatorMode extends Mode
{
    public TranslatorMode()
    {
        super("Language Translator", "<translate>", "Convert from one language to another",
                "Default translate will attempt to convert any other language to english", "<translate>Hola mis amigos</translate>", true);
    }

    @Override
    public Text preview(Text textAreaBoxData)
    {
        System.out.println("Stuff!");
        String text = "";
        try
        {
            com.gtranslate.Translator translate = com.gtranslate.Translator.getInstance();
            text = translate.translate("I am programmer", Language.ENGLISH, Language.ROMANIAN);
            System.out.println(text);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new Text(text);
    }
}
