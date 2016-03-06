package Example;

import Design.Controller;

import java.awt.*;

/**
 * Created by alex on 3/5/2016.
 */
public final class ExampleController implements Controller
{
    private final ExampleModel model;
    private final ExampleView view;

    public ExampleController()
    {
        model = new ExampleModel();
        view = new ExampleView();
    }

    public Component getComponent()
    {
        return view.getComponent();
    }
}
