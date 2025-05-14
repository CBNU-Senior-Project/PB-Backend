import os
import random
import mysql.connector
from config import MYSQL_CONFIG

MAX_SAMPLES_PER_LABEL = {
    0: 5000,  # 정상 대화는 최대 5,000개까지 사용
    1: None   # 보이스피싱은 전부 사용
}

def load_dataset(folder_path, label):
    texts, labels = [], []
    all_files = [f for f in os.listdir(folder_path) if f.endswith(".txt")]
    max_samples = MAX_SAMPLES_PER_LABEL.get(label)

    if max_samples and len(all_files) > max_samples:
        all_files = random.sample(all_files, max_samples)

    for file in all_files:
        file_path = os.path.join(folder_path, file)
        with open(file_path, "r", encoding="utf-8") as f:
            texts.append(f.read())
            labels.append(label)
    return texts, labels

def load_data_from_db():
    conn = mysql.connector.connect(**MYSQL_CONFIG)
    cursor = conn.cursor()
    cursor.execute("""
        SELECT detection_id, text, is_phishing FROM detection_log
        WHERE used_in_train = false
    """)
    rows = cursor.fetchall()

    detection_ids = [row[0] for row in rows]
    texts = [row[1] for row in rows]
    labels = [1 if row[2] else 0 for row in rows]

    # 학습에 사용한 데이터 표시
    if detection_ids:
        format_strings = ','.join(['%s'] * len(detection_ids))
        update_query = f"""
            UPDATE detection_log SET used_in_train = true
            WHERE detection_id IN ({format_strings})
        """
        cursor.execute(update_query, tuple(detection_ids))
        conn.commit()

    cursor.close()
    conn.close()

    return texts, labels

def load_all_data(paths):
    all_texts, all_labels = [], []
    for folder, label in paths.items():
        texts, labels = load_dataset(folder, label)
        all_texts.extend(texts)
        all_labels.extend(labels)

    db_texts, db_labels = load_data_from_db()
    all_texts.extend(db_texts)
    all_labels.extend(db_labels)
    return all_texts, all_labels