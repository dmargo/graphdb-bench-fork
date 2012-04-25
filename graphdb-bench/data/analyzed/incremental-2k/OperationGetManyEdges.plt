set autoscale
unset log
unset label
set xtic auto
set ytic auto
set xlabel 'Database Size'
set ylabel 'Time (ms)'
set key top left

set datafile separator ';'
set output 'OperationGetManyEdges.eps'
set terminal postscript enhanced eps color 'Helvetica' 20 size 5,3.5 dl 3

plot\
  '<sed "1,2d" OperationGetManyEdges.csv' using 1:2 title 'bdb' with lines lt 1 lw 2, \
  '<sed "1,2d" OperationGetManyEdges.csv' using 1:3 title 'dex' with lines lt 2 lw 2, \
  '<sed "1,2d" OperationGetManyEdges.csv' using 1:4 title 'dup' with lines lt 3 lw 2, \
  '<sed "1,2d" OperationGetManyEdges.csv' using 1:5 title 'neo' with lines lt 4 lw 2, \
  '<sed "1,2d" OperationGetManyEdges.csv' using 1:6 title 'sql' with lines lt 7 lw 2
