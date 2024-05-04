# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice-looking HTML.

## Part 1.1: App Description

> Please provide a friendly description of your app, including
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

This app allows you to retrieve the definition of a word in English and
translate it to another language. The languages to choose from are Spanish
and French.
The way you use it is by first typing your word in the "Word" text box, then
selecting between Spanish or French in the "Language" combo box, then clicking
the "Search" button.
The application calls the FreeDictionary API to retrieve the definition of the
word in English and display it in the "Definition" textbox. The application
then takes that definition and sends it to the LibreTranslate API and
retrieves the resulting translation to display it in the "Translation"
textbox. This means that the LibreTranslate API uses the result of the
FreeDictionary API to carry out the output for the LibreTranslate API.

## Part 1.2: APIs

> For each RESTful JSON API that your app uses (at least two are required),
> include an example URL for a typical request made by your app. If you
> need to include additional notes (e.g., regarding API keys or rate
> limits), then you can do that below the URL/URI. Placeholders for this
> information are provided below. If your app uses more than two RESTful
> JSON APIs, then include them with similar formatting.

### API 1

```
https://api.dictionaryapi.dev/api/v2/entries/en/ is the default URL.
https://api.dictionaryapi.dev/api/v2/entries/en/hello is a sample search for the definition of Hello.
```

This API accepts GET or POST requests.
This API has a limit of 450 requests per 5 minutes per IP address.

### API 2

```
https://libretranslate.com/translate is the default URL.
https://libretranslate.com/translate?q=hello&source=en&target=es&format=text&api_key=7658f0e5-fbd3-4b29-86d4-1f95e7594052 is a sample translation of Hello from English to Spanish.
```

The API "Translate" method only accepts POST requests.
The API requires an API key which is included in the code.
The API key has a limit of up to 80 translations / minute.

## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

I learned more on making the JSON for the API. Additionally, using an API key
was much simpler than I thought. I now have a better understanding of APIs and
feel very inspired to work on more projects using them in the future.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

I would choose to not overcomplicate by trying to add dependencies which I
did not have to do as well as not mess around with the module-info folder when
trying to use the APIs. Additionally, I would have researched more API options
before deciding on the final ones to use rather than try immediately
implenting the first APIs I thought of work which wasted a large amount of
time.