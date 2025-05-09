"""Functions used in preparing Guido's gorgeous lasagna.

Learn about Guido, the creator of the Python language:
https://en.wikipedia.org/wiki/Guido_van_Rossum

This is a module docstring, used to describe the functionality
of a module and its functions and/or classes.
"""


EXPECTED_BAKE_TIME=40
PREPARATION_TIME=2


def bake_time_remaining(elapsed_bake_time):
    """Calculate the bake time remaining.

    :param elapsed_bake_time: int - baking time already elapsed.
    :return: int - remaining bake time (in minutes) derived from 'EXPECTED_BAKE_TIME'.

    Function that takes the actual minutes the lasagna has been in the oven as
    an argument and returns how many minutes the lasagna still needs to bake
    based on the `EXPECTED_BAKE_TIME`.
    """

    return EXPECTED_BAKE_TIME - elapsed_bake_time


def preparation_time_in_minutes(number_of_layers):
    """Calculate the lasagna preparation time.

    :param number_of_layers: int - number of lasagna layers.
    :return: int - preparation time

    Given the desired nubmer of layers compute the lasagna preparation time.
    """

    return PREPARATION_TIME * number_of_layers


def elapsed_time_in_minutes(number_of_layers, elapsed_bake_time):
    """Calculate total elapsed time.

    :param number_of_layers: int - number of lasagna layers.
    :param elapsed_bake_time: int - time in the owen.
    :return: int - total elapsed time.

    Given the desired nubmer of layers and elapsed baking time calculated total
    cooking time.
    """

    return preparation_time_in_minutes(number_of_layers) + elapsed_bake_time
