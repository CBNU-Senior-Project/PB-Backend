from config import DATA_PATHS, MODEL_OUTPUT_DIR
from data_loader import load_all_data
from preprocessing import preprocess_texts
from model import train_model
from exporter import export_model
from kafka_producer import send_model_updated_event

def main():
    texts, labels = load_all_data(DATA_PATHS)
    processed_texts = preprocess_texts(texts)
    vectorizer, label_encoder, model = train_model(processed_texts, labels)
    export_model(vectorizer, label_encoder, model, MODEL_OUTPUT_DIR)
    send_model_updated_event()
    print("✅ 모델 학습 및 저장 + 이벤트 발행 완료")

if __name__ == "__main__":
    main()
