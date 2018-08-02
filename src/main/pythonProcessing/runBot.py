from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
from __future__ import unicode_literals

import logging
import argparse
import os

from rasa_core.agent import Agent
from rasa_core import utils
from rasa_core.channels.console import ConsoleInputChannel
from rasa_core.interpreter import RegexInterpreter
from rasa_core.policies.keras_policy import KerasPolicy
from rasa_core.policies.memoization import MemoizationPolicy
from rasa_core.interpreter import RasaNLUInterpreter

logger = logging.getLogger(__name__)




def create_argument_parser():
    parser = argparse.ArgumentParser(description='runs a bot or starts onlinetraining')

    parser.add_argument('-c','--core', type=str,required=True,help="coreModel folder")
    parser.add_argument('-n','--nlu',type=str,required=True,help="nluModelFolder")


    utils.add_logging_option_arguments(parser)
    return parser



if __name__ == '__main__':

    logging.basicConfig(level='INFO')
    arg_parser = create_argument_parser()
    cmdline_args = arg_parser.parse_args()
    print(cmdline_args.nlu)
    print(cmdline_args.core)



    os.chdir("C:\\Users\\Chris\\Desktop\\WeatherBot")
    interpreter = RasaNLUInterpreter(cmdline_args.nlu)
    agent = Agent.load(cmdline_args.core,interpreter= interpreter)
    print("Bot geladen")

    if True:
        agent.handle_channel(ConsoleInputChannel())

