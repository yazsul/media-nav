import json, os, base64

from transformers import AutoTokenizer, AutoModelForSequenceClassification

from typing import Dict

from fastapi import Depends, FastAPI, HTTPException
from pydantic import BaseModel

from .classifiers.bert_model import BertModel,get_tonality_model,get_leaning_model
from .wcloud import WordCloudImplementation, get_wordcloud_instance

from io import BytesIO

def download_models():

    with open("data_analysis/configs/config.json") as json_file:
        config = json.load(json_file)

        for model in config:
            conf = config[model]
            if not os.path.isdir(conf['TOKENIZER_LOCAL_PATH']):
                AutoTokenizer\
                    .from_pretrained(conf['TOKENIZER'])\
                    .save_pretrained(conf['TOKENIZER_LOCAL_PATH'])

            if not os.path.isdir(conf['MODEL_LOCAL_PATH']):
                AutoModelForSequenceClassification\
                    .from_pretrained(conf['MODEL'])\
                    .save_pretrained(conf['MODEL_LOCAL_PATH'])


download_models()
app = FastAPI()


# Example request body, may be subject to change
class SentimentRequest(BaseModel):
    text: str


# Example response body, may be subject to change
class SentimentResponse(BaseModel):
    probabilities: Dict[str, float]

class WordCloudRequest(BaseModel):
    text: str

class WordCloudResponse(BaseModel):
    b64Img: str

@app.post("/predict_tonality", response_model=SentimentResponse)
def predict(request: SentimentRequest, model: BertModel = Depends(get_tonality_model)):
    if len(request.text) == 0:
        raise HTTPException(status_code=422, detail="Nothing to process(no request body).")
    probabilities = model.predict(request.text)
    return SentimentResponse(
        probabilities=probabilities
    )


@app.post("/predict_leaning", response_model=SentimentResponse)
def predict(request: SentimentRequest, model: BertModel = Depends(get_leaning_model)):
    if len(request.text) == 0:
        raise HTTPException(status_code=422, detail="Nothing to process(no request body).")
    probabilities = model.predict(request.text)
    return SentimentResponse(
        probabilities=probabilities
    )
@app.post("/wordcloud")
def createWordCloud(request: WordCloudRequest, wc: WordCloudImplementation = Depends(get_wordcloud_instance)):
    if len(request.text) == 0:
        raise HTTPException(status_code=422, detail="Nothing to process(no request body).")
    wcObject = wc.generate_word_cloud(request.text)
    imgData = BytesIO()
    wc.save_word_cloud_as_image(wcObject, imgData)
    imgData.seek(0)
    return WordCloudResponse(
        b64Img=base64.b64encode(imgData.read())
    )