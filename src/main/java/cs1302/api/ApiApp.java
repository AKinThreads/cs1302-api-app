package cs1302.api;

import java.net.URLEncoder;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import cs1302.api.Dictionary;
import cs1302.api.Translation;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.application.Platform;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.io.UnsupportedEncodingException;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

/**
 * This class represents a JavaFX application that
 * provides dictionary lookup and translation functionalities.
 */
public class ApiApp extends Application {

    private final String dictionaryUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private final String translateUrl = "https://libretranslate.com/translate";
    private TextField wordInput;
    private Button searchButton;
    private TextArea resultArea;
    private TextArea translatedArea;
    private ComboBox<String> targetLanguageComboBox;
    private HBox category = new HBox(10);
    private HBox dictionaryField = new HBox(10);
    private HBox translationField = new HBox(10);
    private HttpResponse<String> transResponse;
    private HttpResponse<String> wordResponse;
    private String searchUrl;
    private String searchUrlTrans;
    private String source = "en";
    private String apiKey = "7658f0e5-fbd3-4b29-86d4-1f95e7594052";
    private String format = "text";
    private HttpRequest requestWord;
    private HttpRequest requestTrans;
    private Text statusText;
    private Label wordLabel;
    private Label definitionLabel;
    private Label translationLabel;
    private Label languageLabel;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) {
        wordInput = new TextField();
        wordInput.setPromptText("English word goes here.");
        searchButton = new Button("Search");
        resultArea = new TextArea();
        resultArea.setEditable(false);
        //resultArea.setMaxWidth();
        resultArea.setWrapText(true);
        translatedArea = new TextArea();
        translatedArea.setEditable(false);
        //translatedArea.setMaxWidth();
        translatedArea.setWrapText(true);
        targetLanguageComboBox = new ComboBox<>();
        statusText = new Text("Type an English word, select a language, and press Search");
        wordLabel = new Label("Word: ");
        definitionLabel = new Label("Definition");
        translationLabel = new Label("Translation");
        languageLabel = new Label("Translate into: ");


        targetLanguageComboBox.getItems().addAll( "Spanish", "French");
        targetLanguageComboBox.setValue("Spanish");


        searchButton.setOnAction(event -> {
            searchMethod();
        });

        category.getChildren().addAll(wordLabel, wordInput, languageLabel,
                                      targetLanguageComboBox, searchButton);
        dictionaryField.getChildren().addAll(resultArea);
        translationField.getChildren().addAll(translatedArea);

        VBox root = new VBox(10);
        root.getChildren().addAll(category, definitionLabel, resultArea,
                                  translationLabel, translatedArea, statusText);

        Scene scene = new Scene(root, 1000, 450);
        stage.setTitle("Multilingual Dictionary App");
        stage.setScene(scene);
        stage.show();
        stage.sizeToScene();
        stage.setResizable(false);
    }

    /**
     * Performs the search operation when the search button is clicked.
     */
    private void searchMethod() {

        searchButton.setDisable(true);
        wordInput.setDisable(true);
        targetLanguageComboBox.setDisable(true);
        statusText.setText("Getting definition and translation...");
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {

            try {
                String word = wordInput.getText().trim();
                String definition = getDefinition(word);
                if (wordResponse.statusCode() == 200) {
                    resultArea.setText(definition);
                    String translation = getTranslation(definition);
                    if (transResponse.statusCode() == 200) {
                        translatedArea.setText(translation);
                        if (definition.length() > 2000) {
                            statusText.setText("Definition and Translation found. Translation is" +
                                               " limited to 2000 characters" +
                                               " due to character limit.");
                        } else {
                            statusText.setText("Definition and Translation found.");
                        }
                    }
                }
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
                statusText.setText("There was an error.");
            }
            wordInput.setDisable(false);
            searchButton.setDisable(false);
            targetLanguageComboBox.setDisable(false);
        }));
        timeline.play();
    }

    /**
     * Parses the JSON response for dictionary definitions.
     *
     * @param jsonResponse The JSON response string.
     * @return A {@code Dictionary} object representing the parsed response.
     */
    private Dictionary parseJsonResponse(String jsonResponse) {
        Gson gson = new Gson();
        Dictionary[] dictionaries = gson.fromJson(jsonResponse, Dictionary[].class);
        if (dictionaries != null && dictionaries.length > 0) {
            return dictionaries[0]; // Assuming the API returns a single result
        } else {
            return null;
        }
    }

    /**
     * Retrieves the definition of a word from the dictionary API.
     *
     * @param word The word to look up.
     * @return The definition of the word.
     */
    public String getDefinition(String word) {
        String result = "";

        try {
            word = URLEncoder.encode(word, "UTF-8");
            String jsonResponse = fetchWordData(word);
            Dictionary dictionary = parseJsonResponse(jsonResponse);
            if (dictionary != null) {
                StringBuilder resultBuilder = new StringBuilder();
                resultBuilder.append("Word: ").append(dictionary.getWord()).append("\n");
                for (Dictionary.Meaning meaning : dictionary.getMeanings()) {
                    resultBuilder.append("Part of Speech: ").
                        append(meaning.getPartOfSpeech()).append("\n");
                    List<Dictionary.Definition> definitions = meaning.getDefinitions();
                    for (int i = 0; i < definitions.size(); i++) {
                        Dictionary.Definition definition = definitions.get(i);
                        resultBuilder.append("Definition ").append(i + 1).
                            append(": ").append(definition.getDefinition()).append("\n");
                        if (definition.getExample() != null) {
                            resultBuilder.append("Example: ").
                                append(definition.getExample()).append("\n");
                        }
                        if (!definition.getSynonyms().isEmpty()) {
                            resultBuilder.append("Synonyms: ").
                                append(String.join(", ", definition.getSynonyms())).append("\n");
                        }
                        if (!definition.getAntonyms().isEmpty()) {
                            resultBuilder.append("Antonyms: ").
                                append(String.join(", ", definition.getAntonyms())).append("\n\n");
                        }
                    }
                }
                result = resultBuilder.toString();
            } else {
                result = "Word not found.";
            }
        } catch (UnsupportedEncodingException e) {
            showAlert("Error", "Unsupported encoding: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            showAlert("Error", "Failed to get definition: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Fetches translation data from the translation API.
     *
     * @param q       The text to translate.
     * @param source  The source language.
     * @param target  The target language.
     * @param format  The format of the translated text.
     * @param apiKey  The API key for accessing translation services.
     * @return The translated text.
     * @throws IOException          If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     */
    private String fetchTranslationData(String q, String source, String target, String format,
                                        String apiKey) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = new Gson().toJson(new Translation(q, source, target, format, apiKey));
        requestTrans = HttpRequest.newBuilder()
            .uri(URI.create(translateUrl))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();
        try {
            transResponse = client.send(requestTrans, HttpResponse.BodyHandlers.ofString());
            return transResponse.body();
        } catch (IOException | InterruptedException e) {
            showAlert("Error", "Failed to fetch translation data: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetches word data from the dictionary API.
     *
     * @param word The word to look up.
     * @return The JSON response containing the word data.
     * @throws IOException          If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     */
    private String fetchWordData(String word) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        requestWord = HttpRequest.newBuilder()
            .uri(URI.create(dictionaryUrl + word))
            .build();
        try {
            wordResponse = client.send(requestWord, HttpResponse.BodyHandlers.ofString());
            return wordResponse.body();
        } catch (IOException | InterruptedException e) {
            showAlert("Error", "Failed to fetch dictionary data: " + e.getMessage());
            return null;
        }
    }

    /**
     * Parses the JSON response for translation data.
     *
     * @param jsonResponse The JSON response string.
     * @return A {@code Translation} object representing the parsed response.
     */
    private Translation parseTranslationResponse(String jsonResponse) {
        return new Gson().fromJson(jsonResponse, Translation.class);
    }

    /**
     * Retrieves the translation of a word or phrase.
     *
     * @param wordInput The word or phrase to translate.
     * @return The translated text.
     */
    public String getTranslation(String wordInput) {
        String q = wordInput.substring(0, Math.min(wordInput.length(), 2000));;
        String modifier = targetLanguageComboBox.getValue();
        if (modifier == "Spanish") {
            modifier = "es";
        } else {
            modifier = "fr";
        }
        String target = modifier;
        source = "en";
        apiKey = "7658f0e5-fbd3-4b29-86d4-1f95e7594052";
        format = "text";
        String translationResult = null;

        try {
            String jsonResponse = fetchTranslationData(q, source, target, format, apiKey);
            Translation translation = parseTranslationResponse(jsonResponse);
            if (translation != null) {
                translationResult = translation.getTranslatedText();

            } else {
                showAlert("Translation Not Found", "Translation not found for the given input.");
                translationResult = null;
            }
        } catch (IOException | InterruptedException e) {
            showAlert("Error", "Failed to get translation: " + e.getMessage());
            e.printStackTrace();
            translationResult = "Could not get translation";
        }
        return translationResult;
    }

    /**
     * Displays an alert dialog with the given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message to display.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.show();
    }

}
