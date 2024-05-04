package cs1302.api;

import java.util.List;

/**
 * Represents a dictionary entry containing information about a word.
 */
public class Dictionary {
    String word;
    List<Meaning> meanings;

    /**
     * Retrieves the word for which the dictionary entry provides information.
     *
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * Retrieves the list of meanings associated with the word.
     *
     * @return the list of meanings
     */
    public List<Meaning> getMeanings() {
        return meanings;
    }

    /**
     * Represents a meaning of the word.
     */
    static class Meaning {
        String partOfSpeech;
        List<Definition> definitions;

        /**
         * Retrieves the part of speech of the word.
         *
         * @return the part of speech
         */
        public String getPartOfSpeech() {
            return partOfSpeech;
        }

        /**
         * Retrieves the list of definitions associated with the part of speech.
         *
         * @return the list of definitions
         */
        public List<Definition> getDefinitions() {
            return definitions;
        }
    }

    /**
     * Retrieves the list of definitions associated with the part of speech.
     *
     * @return the list of definitions
     */
    static class Definition {
        String definition;
        String example;
        List<String> synonyms;
        List<String> antonyms;

        /**
         * Retrieves the definition of the word.
         *
         * @return the definition
         */
        public String getDefinition() {
            return definition;
        }

        /**
         * Retrieves an example usage of the word.
         *
         * @return the example
         */
        public String getExample() {
            return example;
        }

        /**
         * Retrieves the list of synonyms for the word.
         *
         * @return the list of synonyms
         */
        public List<String> getSynonyms() {
            return synonyms;
        }

        /**
         * Retrieves the list of antonyms for the word.
         *
         * @return the list of antonyms
         */
        public List<String> getAntonyms() {
            return antonyms;
        }
    }
}
