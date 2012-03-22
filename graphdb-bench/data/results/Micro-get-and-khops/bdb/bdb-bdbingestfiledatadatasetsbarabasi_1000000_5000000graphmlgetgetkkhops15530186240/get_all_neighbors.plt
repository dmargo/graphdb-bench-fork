set terminal postscript color enhanced
set output 'get_all_neighbors.eps'

set datafile separator ';'
set xlabel 'Neighborhood Size (vertices)'
set ylabel 'Time (nanoseconds)'

set xrange [0:60]
set yrange [0:3e+06]

#Fitted least-squares data.
f(x) = a*x + b
a = 55858.6; b = 21143
fit f(x) '<sed "1,3d" get_all_neighbors' using 2:1 via a,b

#Predicted by get_vertex + get_neighbor.
g(x) = i*x + j
i = 26230.435 + 31636.655; j = 21143

plot '<sed "1,3d" get_all_neighbors' using 2:1 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'
