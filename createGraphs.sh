#!/bin/bash

interactive=
output="graph.svg"
title="Objective value log"
logScale="y"
path=$(pwd)
scale=1
barsEvery=20

while [ "$1" != "" ]; do
    case "$1" in
	-logfileNames)
	    shift
	    logfileNames=$1
	    ;;
	-legendNames)
	    shift
	    legendNames=$1
	    ;;
	-output)
	    shift
	    output=$1
	    ;;
	-title)
	    shift
	    title=$1
	    ;;
	-logScale)
	    shift
	    logScale=$1
	    ;;
	-path)
	    shift
	    path=$1
	    ;;
	-scale)
	    shift
	    scale=$1
	    ;;
	-barsEvery)
	    shift
	    barsEvery=$1
	    ;;
	-limit)
	    shift
	    limit=$1
	    ;;
    esac
    shift
done

plot="set term svg solid lw 1\n\
set output \"$output\"\n\
set logscale $logScale\n\
set grid\n\
set title \"$title\"\n\
set xlabel \"Function evaluations (/$scale)\"\n\
set ylabel \"Objective value\"\n\
plot [:$limit]"

logFileNames=`echo $logfileNames | tr ',' ' '`

color=0
for logName in $logFileNames ; do
    ((color++))
    name=`echo $legendNames | cut -d',' -f $color`

    plot=${plot}"\"${path}/${logName}.objective_stats\" using (\$1/${scale}):4 w l title \"$name\" ls 1 lc $color, \"${path}/${logName}.objective_stats\" every $barsEvery using (\$1/${scale}):4:3:5 with errorbars notitle ls 1 lc $color,"
done

plot=`echo $plot | sed 's/.$//'`

plot="$plot \n\
set output\n\
set term wxt\n"

echo -e $plot | gnuplot
