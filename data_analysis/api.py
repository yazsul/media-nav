import json, os

from transformers import AutoTokenizer, AutoModelForSequenceClassification

from typing import Dict

from fastapi import Depends, FastAPI
from pydantic import BaseModel

from .classifiers.bert_model import BertModel,get_tonality_model,get_leaning_model


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


@app.post("/predict_tonality", response_model=SentimentResponse)
def predict(request: SentimentRequest, model: BertModel = Depends(get_tonality_model)):
    probabilities = model.predict(request.text)
    return SentimentResponse(
        probabilities=probabilities
    )


@app.post("/predict_leaning", response_model=SentimentResponse)
def predict(request: SentimentRequest, model: BertModel = Depends(get_leaning_model)):
    probabilities = model.predict(request.text)
    return SentimentResponse(
        probabilities=probabilities
    )
