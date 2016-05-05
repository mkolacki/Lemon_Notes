import com.jfoenix.controls.JFXTextArea;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mockito.internal.matchers.Null;
import org.tbee.javafx.scene.layout.MigPane;

import javax.swing.*;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class Help extends Coggie {
    private final int WIDTH = 450;
    private final int HEIGHT = 500;

    private final Stage help_screen;
    private final Stage primary_stage;
    private final Pane primary_pane;

    private Label projects;
    private Label notes;
    private Label modes;
    private Label fonts;
    private Label colors_and_themes;
    private Label saving_notes;
    private Label deleting_notes;
    private Label clearing_the_screen;
    private Label previewing;
    private Label closing;

    public Help(Stage stage, Pane pane){
        if(stage != null){
            primary_stage = stage;
        }else {
            throw new NullPointerException("Primary Stage is null");
        }
        if(pane != null){
            primary_pane = pane;
        }else {
            throw new NullPointerException("Primary Pane is null");
        }

        help_screen = new Stage();
        help_screen.setTitle("Help");
        help_screen.initStyle(StageStyle.UTILITY);
        help_screen.initModality(Modality.APPLICATION_MODAL);
        help_screen.initOwner(primary_stage);

        MigPane help_pane = create_layout();
        pane.setId("MainHelpScreenPane");
        Scene help_screen_scene = new Scene(help_pane, WIDTH, HEIGHT);
        help_screen.setScene(help_screen_scene);

    }

    public void show(){
        help_screen.setX(primary_stage.getX());
        help_screen.setY(primary_stage.getY());
        help_screen.show();
    }

    private MigPane create_layout(){
        MigPane main_pane = new MigPane();
        main_pane.setId("MainHelpSCreenPaneBuilder");
        main_pane.setPrefSize(WIDTH, HEIGHT);

        String text = "Projects are designed to hold a list of notes which would presumably be\n" +
                      "relevant to the respective project's name. A list of all current projects\n" +
                      "may be observed by clicking on the \"Projects\" button in the upper left\n" +
                      "of the screen. New projects may be created by clicking on the \"New Project\"\n" +
                      "button on the upper right of the screen. Existing projects may be managed\n" +
                      "by clicking the \"Project Settings...\" option in the dropdown menu observable\n" +
                      "by clicking on the cog wheel icon in the upper center of the screen. In this\n" +
                      "area, projects may be renamed, deleted, or added at whim.";

        projects = new Label(text);
        projects.setId("ProjectsHelp");
        projects.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        MigPane project_help_pane = new MigPane();
        project_help_pane.setId("ProjectHelpPaneBuilder");
        project_help_pane.add(projects);
        project_help_pane.setPrefSize(WIDTH, main_pane.getPrefHeight()/2);
        TitledPane project_pane = new TitledPane("Projects", project_help_pane);
        project_pane.setId("ProjectHelpPane");
        project_pane.setExpanded(false);

        text = "Notes are the purpose of the application! Each one denoted by a given\n" +
                "subject, notes contain whatever messages you save in them, presumably\n" +
                "relating to their designated project. Past notes are accessible via the \"Notes\"\n" +
                "button just right of the \"Projects\" button. New notes may be saved by\n" +
                "clicking the plus icon on the lower right of the screen. If deleting a past note\n" +
                "is desired, then one need only select the note from the notes list and click the\n" +
                "trash icon on the lower right of the screen. Notes may utilize one or more\n" +
                "modes, which may be previewed by clicking the all seeing eye in the lower\n" +
                "right of the screen.";

        notes = new Label(text);
        notes.setId("NotesHelp");
        notes.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        MigPane notes_help_pane = new MigPane();
        notes_help_pane.setId("NotesHelpPaneBuilder");
        notes_help_pane.add(notes);
        notes_help_pane.setPrefSize(WIDTH, main_pane.getPrefHeight()/2);
        TitledPane note_pane = new TitledPane("Notes", notes_help_pane);
        note_pane.setId("NotesHelpPane");
        note_pane.setExpanded(false);

        text = "Modes are operations which may be performed on the text within a note by\n" +
                "using certain indicators within the text at both the beginning and end\n" +
                "locations, wrapping the text where the specified operation is desired.\n" +
                "A list of available modes and how to use them is provided in the\n" +
                "\"Mode Settings...\" option of the dropdown menu observable by clicking\n" +
                "on the cog wheel icon in the upper center of the screen. The applied\n" +
                "operations may be observed by clicking the preview button denoted by\n" +
                "an eye icon on the central right portion of the screen.";

        modes = new Label(text);
        modes.setId("ModessHelp");
        modes.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        MigPane modes_help_pane = new MigPane();
        modes_help_pane.setId("ModesHelpPaneBuilder");
        modes_help_pane.add(modes);
        modes_help_pane.setPrefSize(WIDTH, main_pane.getPrefHeight()/2);
        TitledPane mode_pane = new TitledPane("Modes", modes_help_pane);
        mode_pane.setId("ModesHelpPane");
        mode_pane.setExpanded(false);

        text = "Several fonts may be chosen for both the subject and primary text areas. Also\n" +
                "selectable is the size of the text which will be displayed in the text areas. The\n" +
                "fonts and sizes of text displayed may be changed in the \"Font Settings...\"\n" +
                "option of the dropdown menu observable by clicking on the cog wheel icon\n" +
                "in the upper center of the screen.";

        fonts = new Label(text);
        fonts.setId("FontsHelp");
        fonts.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        MigPane font_help_pane = new MigPane();
        font_help_pane.setId("FontsHelpPaneBuilder");
        font_help_pane.add(fonts);
        font_help_pane.setPrefSize(WIDTH, main_pane.getPrefHeight()/2);
        TitledPane font_pane = new TitledPane("Fonts", font_help_pane);
        font_pane.setId("FontsHelpPane");
        font_pane.setExpanded(false);

        text = "To prevent from this application being an eye sore, the option to change\n" +
                "the colors of several components is made available via the \"Visual Settings...\"\n" +
                "option of the dropdown menu, accessible by clicking on the cog wheel icon\n" +
                "in the upper center of the screen. Colors are chosen by clicking on any of\n" +
                "the boxes aside their respective components in the Visual Settings screen.\n" +
                "Chosen colors are applied by clicing the \"apply\" button at the bottom\n" +
                "right of the screen.";

        colors_and_themes = new Label(text);
        colors_and_themes.setId("ColorsAndtThemesHelp");
        colors_and_themes.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        MigPane colors_and_themes_help_pane = new MigPane();
        colors_and_themes_help_pane.setId("ColorsAndThemesHelpPaneBuilder");
        colors_and_themes_help_pane.add(colors_and_themes);
        colors_and_themes_help_pane.setPrefSize(WIDTH, main_pane.getPrefHeight()/2);
        TitledPane colors_and_themes_pane = new TitledPane("Colors and Themes", colors_and_themes_help_pane);
        colors_and_themes_pane.setId("ProjectHelpPane");
        colors_and_themes_pane.setExpanded(false);

        text = "Notes are saved by entering text in the subject areas and the main text areas\n" +
                "on the screen, and then clicking on the plus icon on the lower right portion\n" +
                "of the screen. A note is saved to the currently selected project, and may be\n" +
                "observed by clicking on the \"Notes\" button located just aside the \"Projects\"\n" +
                "button in the upper left of the screen. Notes with empty components cannot\n" +
                "be saved.";

        saving_notes = new Label(text);
        saving_notes.setId("SavingNotesHelp");
        saving_notes.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        MigPane saving_notes_help_pane = new MigPane();
        saving_notes_help_pane.setId("SavingNotesHelpPaneBuilder");
        saving_notes_help_pane.add(saving_notes);
        saving_notes_help_pane.setPrefSize(WIDTH, main_pane.getPrefHeight()/2);
        TitledPane saving_notes_pane = new TitledPane("Saving Notes", saving_notes_help_pane);
        saving_notes_pane.setId("SavingNotesHelpPane");
        saving_notes_pane.setExpanded(false);

        text = "Notes may be deleted by clicking the trash can icon on the lower right\n" +
                "portion of the screen. If an old note is currently being viewed, an alert\n" +
                "verifying that a delete is desired will appear. If the currently displayed note\n" +
                "has not been previously saved, an alert verifying that a clearing of\n" +
                "the screen is desired will appear. I";

        deleting_notes = new Label(text);
        deleting_notes.setId("DeletingNotesHelp");
        deleting_notes.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        MigPane deleting_notes_help_pane = new MigPane();
        deleting_notes_help_pane.setId("DeletingNotesHelpPaneBuilder");
        deleting_notes_help_pane.add(deleting_notes);
        deleting_notes_help_pane.setPrefSize(WIDTH, main_pane.getPrefHeight()/2);
        TitledPane deleting_notes_pane = new TitledPane("Deleting Notes", deleting_notes_help_pane);
        deleting_notes_pane.setId("DeletingNotesHelpPane");
        deleting_notes_pane.setExpanded(false);

        text = "The screen may be cleared of the currently displayed note by clicking the\n" +
                "button denoted by an image that resembles a blank note on the central right\n" +
                "portion of the screen. If an old note is being observed, the screen will be\n" +
                "cleared but not deleted. If a new note was being written, an alert will\n" +
                "appear asking idf a save prior to the screen being cleared is desired.\n" +
                "In any case, a clearing of the screen with new, unsaved note will clear the\n" +
                "main text area, but will leave the subject field as is.";

        clearing_the_screen = new Label(text);
        clearing_the_screen.setId("ClearingTheScreenHelp");
        clearing_the_screen.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        MigPane clearing_the_screen_help_pane = new MigPane();
        clearing_the_screen_help_pane.setId("ClearingTheScreenHelpPaneBuilder");
        clearing_the_screen_help_pane.add(clearing_the_screen);
        clearing_the_screen_help_pane.setPrefSize(WIDTH, main_pane.getPrefHeight()/2);
        TitledPane clearing_the_screen_pane = new TitledPane("Clearing the Screen", clearing_the_screen_help_pane);
        clearing_the_screen_pane.setId("ClearingTheScreenHelpPane");
        clearing_the_screen_pane.setExpanded(false);

        text = "Previewing of the currently displayed note with all of the indicated modes\n" +
                "may be done by clicking the eye icon on the central right of the main screen.\n" +
                "This will cause another screen to appear with the indicated modes being applied\n" +
                "to the appropriate text.";

        previewing = new Label(text);
        previewing.setId("PreviewingHelp");
        previewing.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        MigPane previewing_help_pane = new MigPane();
        previewing_help_pane.setId("PreviewingHelpPaneBuilder");
        previewing_help_pane.add(previewing);
        previewing_help_pane.setPrefSize(WIDTH, main_pane.getPrefHeight()/2);
        TitledPane previewing_pane = new TitledPane("Previewing the Current Note", previewing_help_pane);
        previewing_pane.setId("PreviewingHelpPane");
        previewing_pane.setExpanded(false);

        text = "The application may be closed by clicking the big red X in the upper right\n" +
                "portion of the screen, as with most other applications.";

        closing = new Label(text);
        closing.setId("ClosingHelp");
        closing.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        MigPane closing_help_pane = new MigPane();
        closing_help_pane.setId("ClosingHelpPaneBuilder");
        closing_help_pane.add(closing);
        closing_help_pane.setPrefSize(WIDTH, main_pane.getPrefHeight()/2);
        TitledPane closing_pane = new TitledPane("Closing out of the Application", closing_help_pane);
        closing_pane.setId("ClosingHelpPane");
        closing_pane.setExpanded(false);


        main_pane.add(project_pane, "wrap");
        main_pane.add(note_pane, "wrap");
        main_pane.add(mode_pane, "wrap");
        main_pane.add(font_pane, "wrap");
        main_pane.add(colors_and_themes_pane, "wrap");
        main_pane.add(saving_notes_pane, "wrap");
        main_pane.add(deleting_notes_pane, "wrap");
        main_pane.add(clearing_the_screen_pane, "wrap");
        main_pane.add(previewing_pane, "wrap");
        main_pane.add(closing_pane, "wrap");

        return main_pane;
    }


}