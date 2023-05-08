import json,os

import torch
import torch.nn.functional as F
from transformers import AutoTokenizer,AutoModelForSequenceClassification

with open("data_analysis/configs/config.json") as json_file:
    config = json.load(json_file)


class BertModel:
    def __init__(self, model_name):

        for model in config:
            conf = config[model]
            if not os.path.isdir(conf['TOKENIZER_LOCAL_PATH']):
                AutoTokenizer \
                    .from_pretrained(conf['TOKENIZER']) \
                    .save_pretrained(conf['TOKENIZER_LOCAL_PATH'])

            if not os.path.isdir(conf['MODEL_LOCAL_PATH']):
                AutoModelForSequenceClassification \
                    .from_pretrained(conf['MODEL']) \
                    .save_pretrained(conf['MODEL_LOCAL_PATH'])

        self.model_config = config[model_name]
        self.device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
        self.tokenizer = AutoTokenizer.from_pretrained(
            pretrained_model_name_or_path=self.model_config["TOKENIZER_LOCAL_PATH"],
            config=f"{self.model_config['TOKENIZER']}/vocab.json"
        )

        classifier = AutoModelForSequenceClassification.from_pretrained(
            pretrained_model_name_or_path=self.model_config["MODEL_LOCAL_PATH"],
            local_files_only=True
        )
        classifier.load_state_dict(
            torch.load(self.model_config["PRETRAINED_MODEL_PATH"], map_location=self.device)
        )
        classifier = classifier.eval()
        self.classifier = classifier.to(self.device)

    def predict(self, text):
        encoded_text = self.tokenizer.encode_plus(
            text,
            max_length=self.model_config["MAX_SEQUENCE_LEN"],
            truncation=True,
            # add_special_tokens=True,
            # return_token_type_ids=False,
            # pad_to_max_length=True,
            # return_attention_mask=True,
            return_tensors="pt",
        )
        input_ids = encoded_text["input_ids"].to(self.device)

        with torch.no_grad():
            output = self.classifier(input_ids)

        scores = F.softmax(output.logits[0], dim=0)
        probabilities = scores.flatten().cpu().numpy().tolist()
        zipped = dict(zip(self.model_config["CLASS_NAMES"], probabilities))
        print(zipped)
        return (
            dict(zip(self.model_config["CLASS_NAMES"], probabilities))
        )


tonality_model = BertModel("BERT_TONALITY_MODEL")
leaning_model = BertModel("BERT_POLITICAL_MODEL")


def get_tonality_model():
    return tonality_model


def get_leaning_model():
    return leaning_model
