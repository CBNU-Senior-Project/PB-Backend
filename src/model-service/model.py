from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import LabelEncoder
from keras import models, layers
import numpy as np

def train_model(texts, labels):
    vectorizer = TfidfVectorizer(stop_words='english')
    X = vectorizer.fit_transform(texts)

    label_encoder = LabelEncoder()
    y = label_encoder.fit_transform(labels)

    model = models.Sequential([
        layers.Dense(128, activation='relu', input_shape=(X.shape[1],)),
        layers.Dropout(0.5),
        layers.Dense(64, activation='relu'),
        layers.Dropout(0.5),
        layers.Dense(1, activation='sigmoid')
    ])
    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
    model.fit(X.toarray(), y, epochs=3, batch_size=64, validation_split=0.2, class_weight={0:1, 1:10})

    return vectorizer, label_encoder, model
