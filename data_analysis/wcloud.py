import json
from wordcloud import WordCloud
import matplotlib.pyplot as plt
import base64
import io
import re
import nltk
from PIL import Image

from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from nltk.stem import WordNetLemmatizer


class WordCloudImplementation:

    def __init__(self):
        resources = ['omw-1.4', 'punkt', 'stopwords', 'wordnet']
        for resource in resources:
            print(resource)
            try:
                nltk.data.find(resource)
            except LookupError:
                nltk.download(resource)

    # Convert text to lowercase, remove punctuation, tokenize, remove stop words, and lemmatize.
    def preprocess_text(self, text):
        """
        Preprocesses the input text by converting it to lowercase, removing punctuation,
        tokenizing the text, removing stop words, and lemmatizing the tokens.

        Args:
            text (str): The input article content to be preprocessed.

        Returns:
            str: The preprocessed text as a string.
        """

        # Convert to lowercase
        text = text.lower()

        # Remove punctuation and special characters
        text = re.sub(r'[^\w\s]', '', text)

        # Tokenization
        tokens = word_tokenize(text)

        # Remove stop words
        stop_words = set(stopwords.words('english'))
        tokens = [token for token in tokens if token not in stop_words]

        # Lemmatization
        lemmatizer = WordNetLemmatizer()
        tokens = [lemmatizer.lemmatize(token) for token in tokens]

        return ' '.join(tokens)


    def generate_word_cloud(self, text):
        """
       Generates a word cloud from the input text by first preprocessing the text,
       and then creating a word cloud object with specified parameters.

       Args:
           text (str): The input text from which the word cloud will be generated.

       Returns:
           WordCloud: The generated word cloud object.
       """

        preprocessed_text = self.preprocess_text(text)
        wordcloud = WordCloud(background_color='white',
                              width=3000, height=2000, max_words=500).generate(preprocessed_text)
        return wordcloud


    def save_word_cloud_as_image(self, wordcloud, image_path):
        """
        Saves the word cloud as an image file.

        Args:
            wordcloud (WordCloud): The word cloud object to be saved as an image.
            image_path (str): The file path where the image will be saved.

        Returns:
            None
        """
        # Set up the figure size and dpi
        plt.figure(figsize=(8, 8), dpi=100)

        # Display the word cloud image
        plt.imshow(wordcloud, interpolation='bilinear')

        # Turn off the axis labels
        plt.axis('off')

        # Save the image to the specified file path in PNG format
        plt.savefig(image_path, format='png')


    def encode_image_as_base64(self, image_path):
        """
        Encodes an image file as base64.

        Args:
            image_path (str): The file path of the image file to be encoded.

        Returns:
            str: The base64-encoded representation of the image.
        """
        # Open the image file in binary mode
        with open(image_path, 'rb') as file:
            # Read the image bytes
            image_bytes = file.read()

        # Encode the image bytes as base64 and decode to UTF-8
        image_base64 = base64.b64encode(image_bytes).decode('utf-8')

        # Return the base64-encoded image as a string
        return image_base64


    def export_word_cloud_as_json(self, image_base64, json_path):
        """
        Exports a word cloud image as JSON.

        Args:
            image_base64 (str): The base64-encoded representation of the word cloud image.
            json_path (str): The file path where the JSON file will be saved.

        Returns:
            None
        """
        # Create a dictionary containing the image base64 data
        output_json = {
            'image_base64': image_base64
        }

        # Open the JSON file in write mode
        with open(json_path, 'w') as file:
            # Write the JSON data to the file
            json.dump(output_json, file)


    def import_json_as_image_base64(self, json_file):
        """
        Imports the image base64 data from a JSON file.

        Args:
            json_file (str): The file path of the JSON file.

        Returns:
            str: The image base64 data.
        """
        # Open the JSON file in read mode
        with open(json_file, 'r') as file:
            # Load the JSON data
            data = json.load(file)

        # Get the image base64 data from the JSON
        image_base64 = data['image_base64']

        # Return the image base64 data
        return image_base64


    def convert_image_base64_to_image(self, image_base64):
        """
        Converts an image base64 string to a PIL Image object.

        Args:
            image_base64 (str): The base64-encoded image string.

        Returns:
            PIL.Image.Image: The image object decoded from the base64 string.
        """
        # Decode the base64 string to bytes
        image_bytes = base64.b64decode(image_base64)

        # Create a BytesIO object from the image bytes
        image_io = io.BytesIO(image_bytes)

        # Open the image using PIL
        image = Image.open(image_io)

        # Return the PIL Image object
        return image


    def display_image(self, image):
        """
        Display the given image using matplotlib.

        Args:
            image (PIL.Image.Image): The image to display.

        Returns:
            None
        """
        # Display the image using matplotlib
        plt.imshow(image)
        plt.axis('off')
        plt.show()

    def testrun(self):
        # random file paths
        text_file = 'article.txt'  # It assumes the article content is saved locally
        image_file = 'wordcloud.png'  # the path for saving the image
        json_file = 'wordcloud.json'  # The path for saving a json file of the image

        # Read text from file
        with open(text_file, 'r', encoding='utf-8') as file:
            text = file.read()

        # Generate word cloud
        wordcloud = self.generate_word_cloud(text)

        # Save word cloud as image
        self.save_word_cloud_as_image(wordcloud, image_file)

        # Encode image as base64
        image_base64 = self.encode_image_as_base64(image_file)

        # Export word cloud as JSON
        self.export_word_cloud_as_json(image_base64, json_file)

        # Import Json as an Imagebase64
        image_base64 = self.import_json_as_image_base64(json_file)

        # Convert Imagebase64 to Image:
        image = self.convert_image_base64_to_image(image_base64)

        # Display Image:

        self.display_image(image)


instance = WordCloudImplementation()


def get_wordcloud_instance():
    return instance