#!/bin/bash

gnuplot -p -e "files='$(ls execTime*.txt)'" plot.gnu > outputExecTime.svg
gnuplot -p -e "files='$(ls FPS*.txt)'" plot.gnu > outputFPS.svg
