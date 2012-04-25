set autoscale
unset log
unset label
set logscale x 2
set xtic auto
set ytic auto
set xlabel 'Number of Threads'
set ylabel 'Time (ms)'
set key top left

set datafile separator ';'
set output 'OperationGetShortestPath.eps'
set terminal postscript enhanced eps color 'Helvetica' 20 size 5,3.5 dl 3

plot\
  '<sed "1,1d" OperationGetShortestPath.csv' using 1:2 title 'neo' with lines lt 1 lw 2
