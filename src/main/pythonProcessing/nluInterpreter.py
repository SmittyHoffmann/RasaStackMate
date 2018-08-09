import sys
from rasa_nlu.components import ComponentBuilder
from rasa_nlu.model import Metadata, Interpreter

builder = ComponentBuilder(use_cache = True)

#laedt NLU-Interpreter und wartet auf Input
if __name__ == '__main__':
    print(sys.argv)
    interpreter = Interpreter.load(sys.argv[1],builder);
    print("Model geladen")
    while(True):
        sentence = input()
        print(interpreter.parse(sentence))




