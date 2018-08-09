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
from rasa_core.interpreter import RasaNLUInterpreter

logger = logging.getLogger(__name__)


# definiert Parameter

def create_argument_parser():
    parser = argparse.ArgumentParser(description='Startet einen Chatbot')

    parser.add_argument('-c','--core', type=str,required=True,help="coreModel folder")
    parser.add_argument('-n','--nlu',type=str,required=True,help="nluModelFolder")


    utils.add_logging_option_arguments(parser)
    return parser


#startet Bot und wartet auf Input
if __name__ == '__main__':

    logging.basicConfig(level='INFO')
    arg_parser = create_argument_parser()
    cmdline_args = arg_parser.parse_args()
    print(cmdline_args.nlu)
    print(cmdline_args.core)




    interpreter = RasaNLUInterpreter(cmdline_args.nlu)
    agent = Agent.load(cmdline_args.core,interpreter= interpreter)
    print("Bot geladen")

    if True:
        agent.handle_channel(ConsoleInputChannel())

