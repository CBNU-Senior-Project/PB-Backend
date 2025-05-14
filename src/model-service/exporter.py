import os
import joblib
import tf2onnx
import tensorflow as tf
from keras.models import load_model

def export_model(vectorizer, label_encoder, model, output_dir):
    os.makedirs(output_dir, exist_ok=True)
    joblib.dump(vectorizer, os.path.join(output_dir, "vectorizer.pkl"))
    joblib.dump(label_encoder, os.path.join(output_dir, "label_encoder.pkl"))
    model_path = os.path.join(output_dir, "model.h5")
    model.save(model_path)

    # Convert to ONNX
    spec = (tf.TensorSpec((None, model.input_shape[1]), tf.float32, name="input"),)
    onnx_model, _ = tf2onnx.convert.from_keras(model, input_signature=spec, opset=13)

    onnx_path = os.path.join(output_dir, "model.onnx")
    with open(onnx_path, "wb") as f:
        f.write(onnx_model.SerializeToString())