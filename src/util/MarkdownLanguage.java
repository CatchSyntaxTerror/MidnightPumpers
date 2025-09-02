package util;

import java.util.ArrayList;

public class MarkdownLanguage {

    // Separate fields, separate command types, end command message
    private static final char[] parseChars = new char[]{':', '*',';'};
    public Commands getCommands(String message) {
        if (message.length() <= 2) return null;
        enum commandType {
            Button,
            TextField,
            Unspecified;
        }
        commandType messageType = commandType.Unspecified;
        switch (message.substring(0,2)) {
            case "bc" -> messageType = commandType.Button;
            case "tc" -> messageType = commandType.TextField;
            default -> {
                return null;
            }
        }
        ButtonCommands buttonCommands = null;
        TextFieldCommands textFieldCommands = null;

        if (messageType == commandType.Button) {
            //TODO: Parse part of string thats for buttons into here
            //buttonCommands = getButtonCommands();
        }
        if (messageType == commandType.TextField) {
            //TODO: parse part of string thats for text fields into here
            //textFieldCommands = getTextFieldCommands();
        }
        Commands commands = new Commands(buttonCommands,textFieldCommands);
        return commands;
    }

    public ButtonCommands getButtonCommands(String message) {
        //TODO: parse button markdown into button commands
        return null;
    }

    public TextFieldCommands getTextFieldCommands (String message) {
        //TODO: parse text field markdown into text field commands
        return null;
    }

    /**
     * Gets markdown representation from commands
     * @param commands both button and text field
     * @return markdown string of this command
     */
    public String getMarkdown(Commands commands) {
        StringBuilder strbuilder = new StringBuilder();
        ArrayList<ButtonCommands.Button> buttonCommands = commands.getBCommands().buttonCommands;
        ArrayList<TextFieldCommands.TextField> textCommands = commands.getTCommands().textFieldCommands;
        strbuilder.append(getButtonMarkdown(buttonCommands));
        strbuilder.append(parseChars[1]);
        strbuilder.append(getTextFieldMarkdown(textCommands));
        strbuilder.append(parseChars[2]);
        return strbuilder.toString();
    }

    /**
     * Method for turning button commands list into a string
     * @param buttonCommands the list of button commands
     * @return a string for this list
     */
    private String getButtonMarkdown(ArrayList<ButtonCommands.Button> buttonCommands) {
        StringBuilder strbuilder = new StringBuilder();
        strbuilder.append("bc");
        strbuilder.append(parseChars[0]);
        for (ButtonCommands.Button buttonCommand : buttonCommands) {
            strbuilder.append(parseChars[0]);
            strbuilder.append(Integer.toString(buttonCommand.field));
            strbuilder.append(parseChars[0]);
            if (buttonCommand.mutualExclusion) {
                strbuilder.append("t");
                strbuilder.append(parseChars[0]);
            } else {
                strbuilder.append("f");
                strbuilder.append(parseChars[0]);
            }
            if (buttonCommand.responsive) {
                strbuilder.append("t");
                strbuilder.append(parseChars[0]);
            } else {
                strbuilder.append("f");
                strbuilder.append(parseChars[0]);
            }
        }
        return strbuilder.toString();
    }

    /**
     * Parses text commands into a string message
     * @param textCommands the commands to parse
     * @return a string in the mark-down language
     */
    private String getTextFieldMarkdown(ArrayList<TextFieldCommands.TextField> textCommands) {
        StringBuilder strbuilder = new StringBuilder();
        strbuilder.append("tc");
        strbuilder.append(parseChars[0]);
        for (TextFieldCommands.TextField textCommand : textCommands) {
            strbuilder.append(parseChars[0]);
            strbuilder.append(textCommand.text);
            strbuilder.append(parseChars[0]);
            strbuilder.append(Integer.toString(textCommand.field));
            strbuilder.append(parseChars[0]);
            switch (textCommand.bgColor) {
                case White -> strbuilder.append('w');
                case Unspecified -> strbuilder.append('x');
            }
            strbuilder.append(parseChars[0]);
            switch (textCommand.font) {
                case Normal -> strbuilder.append('n');
                case Bold -> strbuilder.append('b');
                case Italic -> strbuilder.append('i');
                case Unspecified -> strbuilder.append('x');
            }
            strbuilder.append(parseChars[0]);
            switch(textCommand.size) {
                case Large -> strbuilder.append('l');
                case Medium -> strbuilder.append('m');
                case Small -> strbuilder.append('s');
                case Unspecified -> strbuilder.append('x');
            }
        }
        return strbuilder.toString();
    }

    public static class Commands {
        private ButtonCommands bCommands;
        private TextFieldCommands tCommands;

        /**
         * Makes a new list of commands
         * @param bCommands button commands
         * @param tCommands text commands
         */
        public Commands(ButtonCommands bCommands, TextFieldCommands tCommands) {
            this.bCommands = bCommands;
            this.tCommands = tCommands;
        }

        /**
         * @return the button commands
         */
        public ButtonCommands getBCommands() {return bCommands;}

        /**
         * @return the text commands
         */
        public TextFieldCommands getTCommands() {return tCommands;}

    }
    public static class ButtonCommands {
        // A list of button commands to be updated
        private ArrayList<Button> buttonCommands;

        /**
         * Makes a new ButtonCommands
         */
        public ButtonCommands() {buttonCommands = new ArrayList<>();}

        /**
         * @return a list of the button commands for this command
         */
        public ArrayList<Button> getButtonCommands() {return buttonCommands;}

        /**
         * @param command the button command to add to this command list
         */
        public void addButtonCommand(Button command) {this.buttonCommands.add(command);}

        public static class Button {
            // The field of the button
            private int field;
            // Whether the button is mutually exclusive
            private boolean mutualExclusion;
            // Whether the button is responsive
            private boolean responsive;

            /**
             * Constructs a button command
             * @param field of the button
             * @param mutualExclusion if the button can be clicked at the same time
             *                        as other buttons
             * @param responsive if the button is responsive
             */
            public Button(int field, boolean mutualExclusion, boolean responsive) {
                this.field = field;
                this.mutualExclusion = mutualExclusion;
                this.responsive = responsive;
            }

            /**
             * @return the field
             */
            public int getField() {return field;}

            /**
             * @return true if the button is mutually exclusive
             */
            public boolean getMutualExclusion() {return mutualExclusion;}

            /**
             * @return true if the button should be responsive
             */
            public boolean getResponsive() {return responsive;}
        }

    }
    public static class TextFieldCommands {
        private ArrayList<TextField> textFieldCommands;

        /**
         * Makes a new text field command
         */
        public TextFieldCommands() {this.textFieldCommands = new ArrayList<>();}

        /**
         * @return the list of field commands
         */
        public ArrayList<TextField> getTextFieldCommands() {return textFieldCommands;}

        /**
         * @param command the command to be added to the list
         */
        public void addFieldCommand(TextField command) {this.textFieldCommands.add(command);}
        public static class TextField {

            /**
             * Constructor with all the parameters included
             * @param text to be displayed
             * @param field to apply
             * @param size of the text
             * @param font of the text
             * @param bg color of the background
             */
            public TextField(String text, int field, Size size, Font font, BGColor bg) {
                this.text = text;
                this.field = field;
                this.size = size;
                this.font = font;
                this.bgColor = bg;
            }

            /**
             * A more bare-bones constructor that only specifies text and field, could be useful
             * for updating text on a button that would keep the other features the same. Feel
             * free to change this as needed.
             * @param text to be displayed
             * @param field to be displayed
             */
            public TextField(String text, int field) {
                this.text = text;
                this.field = field;
                this.bgColor = BGColor.Unspecified;
                this.font = Font.Unspecified;
                this.size = Size.Unspecified;
            }

            // Information definitions

            /**
             * The different sizes
             */
            public enum Size {
                Small,
                Medium,
                Large,
                Unspecified;
            }

            /**
             * The different fonts
             */

            public enum Font {
                Normal,
                Italic,
                Bold,
                Unspecified;
            }

            /**
             * The different background colors
             */
            public enum BGColor {
                White,
                Unspecified;
            }

            // the field # 0-9
            private int field;

            // the size of the text
            private Size size;

            // the text
            private String text;

            // the font
            private Font font;

            // the background color
            private BGColor bgColor;

            /**
             * @return text
             */
            public String getText() {return text;}

            /**
             * @return field
             */
            public int getField() {return field;}

            /**
             * @return size
             */
            public Size getSize() {return size;}

            /**
             * @return font
             */
            public Font getFont() {return font;}

            /**
             * @return bgcolor
             */
            public BGColor getBGColor() {return bgColor;}

        }
    }

}
