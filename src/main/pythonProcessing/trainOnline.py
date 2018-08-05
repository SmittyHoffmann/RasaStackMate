from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
from __future__ import unicode_literals

import logging
import argparse


from rasa_core import utils
from rasa_core.agent import Agent
from rasa_core.channels.console import ConsoleInputChannel
from rasa_core.policies.keras_policy import KerasPolicy
from rasa_core.policies.memoization import MemoizationPolicy
from rasa_core.interpreter import RasaNLUInterpreter




def create_argument_parser():
    parser = argparse.ArgumentParser(description='Startet Online-Training')

    parser.add_argument('-s','--stories', type=str,required=True,help="storiesFile")
    parser.add_argument('-d','--domain',type=str,required=True,help="domainFile")
    parser.add_argument('-n','--nlu',type=str,required=True,help="NLUModel")


    utils.add_logging_option_arguments(parser)
    return parser



if __name__ == '__main__':


    arg_parser = create_argument_parser()
    cmdline_args = arg_parser.parse_args()

    interpreter = RasaNLUInterpreter(cmdline_args.nlu)
    agent = Agent(cmdline_args.domain,
                  policies=[MemoizationPolicy(max_history=2), KerasPolicy()],
                  interpreter=interpreter)

    training_data = agent.load_data(cmdline_args.stories)

    print("Bot geladen")
    agent.train_online(training_data,
                       input_channel=ConsoleInputChannel(),
                       batch_size=50,
                       epochs=200,
                       max_training_samples=300)




