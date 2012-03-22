set terminal postscript color enhanced
set output 'get_all_neighbors.eps'

set datafile separator ';'
set xlabel 'Neighborhood Size (vertices)'
set ylabel 'Time (nanoseconds)'

set xrange [0:60]
set yrange [0:3e+06]


f(x) = a*x + b
a = 18882 + 86691; b = 11583
fit f(x) '<sed "1,3d" get_all_neighbors' using 2:1 via a,b

g(x) = i*x + j
i = 18882 + 86691; j = 23590.5

plot '<sed "1,3d" get_all_neighbors' using 2:1 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'
