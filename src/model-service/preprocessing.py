from konlpy.tag import Okt

def preprocess_texts(texts):
    okt = Okt()
    return [' '.join(okt.morphs(text)) for text in texts]