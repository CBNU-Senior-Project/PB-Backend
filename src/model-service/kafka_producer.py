from kafka import KafkaProducer
import json
from config import KAFKA_CONFIG

def send_model_updated_event():
    producer = KafkaProducer(
        bootstrap_servers=KAFKA_CONFIG['bootstrap_servers'],
        value_serializer=lambda v: json.dumps(v).encode('utf-8')
    )
    event = {
        "eventType": "MODEL_UPDATED",
        "timestamp": __import__('datetime').datetime.utcnow().isoformat()
    }
    producer.send(KAFKA_CONFIG['topic'], value=event)
    producer.flush()
    print("📤 ModelUpdatedEvent 발행 완료")