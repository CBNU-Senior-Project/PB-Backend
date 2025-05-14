import os

BASE_DIR = os.path.dirname(os.path.abspath(__file__))

DATA_PATHS = {
    os.path.join(BASE_DIR, "data", "type1"): 1,
    os.path.join(BASE_DIR, "data", "type2"): 1,
    os.path.join(BASE_DIR, "data", "normal"): 0
}

MODEL_OUTPUT_DIR = os.path.join(BASE_DIR, "output")

# MySQL 연결 정보
MYSQL_CONFIG = {
    'host': 'localhost',
    'user': 'your_user',
    'password': 'your_password',
    'database': 'phishing_db',
    'port': 3306
}

# Kafka 설정
KAFKA_CONFIG = {
    'bootstrap_servers': 'localhost:9092',
    'topic': 'model-updated'
}