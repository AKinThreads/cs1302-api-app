package cs1302.api;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a translation of a text from one language to another.
 */
public class Translation {

    String q;
    String source;
    String target;
    String translatedText;
    String format = "text";
    @SerializedName("api_key")
    String apiKey = "7658f0e5-fbd3-4b29-86d4-1f95e7594052";

    /**
     * Retrieves the text to be translated.
     *
     * @return the text to be translated
     */
    public String getQ() {
        return q;
    }

    /**
     * Retrieves the source language of the text.
     *
     * @return the source language
     */
    public String getSource() {
        return source;
    }

    /**
     * Retrieves the target language for the translation.
     *
     * @return the target language
     */
    public String getTarget() {
        return target;
    }

    /**
     * Retrieves the translated text.
     *
     * @return the translated text
     */
    public String getTranslatedText() {
        return translatedText;
    }

    /**
     * Retrieves the API key used for translation.
     *
     * @return the API key
     */
    public String getApi() {
        return apiKey;
    }

    /**
     * Retrieves the format of the translation.
     *
     * @return the format of the translation
     */
    public String getFormat() {
        return format;
    }

    /**
     * Constructs a new Translation object with the specified parameters.
     *
     * @param q the text to be translated
     * @param source the source language of the text
     * @param target the target language for the translation
     * @param format the format of the translation
     * @param apiKey the API key used for translation
     */
    public Translation(String q, String source, String target, String format, String apiKey) {
        this.q = q;
        this.source = source;
        this.target = target;
        this.format = format;
        this.apiKey = apiKey;
    }
}
