package util;

public class MarkdownConstants {

    // Separate fields, separate command types, end command message, separate command instances
    public static final char[] parseChars = new char[]{':', ';','.','/'};

    /**
     * The different text field sizes
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
}
