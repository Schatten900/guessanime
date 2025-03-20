import torch
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM
import sys

tokenizer = AutoTokenizer.from_pretrained("t5-small", force_download=True)
model = AutoModelForSeq2SeqLM.from_pretrained("t5-small", return_dict=True)

def summarizer_text_with_ai(text):
    inputs = tokenizer.encode("summarize: " + text, return_tensors="pt", max_length=512, truncation=True)

    output = model.generate(
        inputs,
        max_length=50,
        min_length=20,
        length_penalty=5.0,
        num_beams=2,
        early_stopping=True
    )

    summary = tokenizer.decode(output[0], skip_special_tokens=True)
    return summary

if __name__ == "__main__":
    if len(sys.argv) > 1:
        text = " ".join(sys.argv[1:])
        try:
            result = summarizer_text_with_ai(text)
            print(result)
        except Exception as e:
            print(f"Erro: {str(e)}")
    else:
        print("Erro: Nenhum texto fornecido")