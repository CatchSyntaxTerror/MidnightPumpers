package testing;

import util.MarkdownLanguage;
import util.MarkdownConstants;

public class MarkdownTest {
    public static void main (String [] args) {
        MarkdownLanguage.Commands exCommands = exampleCommands();
        String exMarkdown = exCommands.toString();
        String sameExample = MarkdownLanguage.getMarkdown(exCommands);

        System.out.println(exMarkdown);
    }

    private static MarkdownLanguage.Commands exampleCommands() {

        MarkdownLanguage.ButtonCommands exampleBC = new MarkdownLanguage.ButtonCommands();
        for (int i = 0; i <= 4; i++) {
            exampleBC.addButtonCommand(exampleButtons[i]);
        }
        MarkdownLanguage.TextFieldCommands exampleTC = new MarkdownLanguage.TextFieldCommands();
        for (int i = 0; i <= 5; i++) {
            exampleTC.addFieldCommand(exampleTextFields[i]);
        }

        return new MarkdownLanguage.Commands(exampleBC,exampleTC);
    }

    // Some example button commands using the button constructor
    static MarkdownLanguage.ButtonCommands.Button [] exampleButtons = {
            new MarkdownLanguage.ButtonCommands.Button(0,false,false),
            new MarkdownLanguage.ButtonCommands.Button(1,false,true),
            new MarkdownLanguage.ButtonCommands.Button(2,true,true),
            new MarkdownLanguage.ButtonCommands.Button(3,false,false),
            new MarkdownLanguage.ButtonCommands.Button(45,false,true)
    };

    // Some example text commands using a couple of the texfield constructors

    static MarkdownLanguage.TextFieldCommands.TextField [] exampleTextFields = {
            new MarkdownLanguage.TextFieldCommands.TextField(
                    "Welcome!",
                    0,
                    MarkdownConstants.Size.Large,
                    MarkdownConstants.Font.Bold,
                    MarkdownConstants.BGColor.White),

            new MarkdownLanguage.TextFieldCommands.TextField(
                    "Select Gas",
                    1,
                    MarkdownConstants.Size.Medium,
                    MarkdownConstants.Font.Normal,
                    MarkdownConstants.BGColor.White),

            new MarkdownLanguage.TextFieldCommands.TextField("unleaded",2),
            new MarkdownLanguage.TextFieldCommands.TextField("plus",3),
            new MarkdownLanguage.TextFieldCommands.TextField("super-plus",4),
            new MarkdownLanguage.TextFieldCommands.TextField(
                    "Some fields can be left unspecified",
                    89,
                    MarkdownConstants.Size.Unspecified,
                    MarkdownConstants.Font.Unspecified,
                    MarkdownConstants.BGColor.Unspecified)

    };
}
