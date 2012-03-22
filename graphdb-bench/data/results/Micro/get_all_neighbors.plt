set terminal postscript color enhanced
set output 'get_all_neighbors.eps'

set datafile separator ';'
set xlabel 'Neighborhood Size (vertices)'
set ylabel 'Time (nanoseconds)'

set xrange [0:100]
set yrange [0:400000]

# Fitted by least squares to data.
f(x) = a*x + b
a = 94.557 + 396.417; b = 1344
fit f(x) '<sed "1,3d" get_all_neighbors' using 2:1 via a,b

# Predicted by get_vertex + get_edge per neighbor.
g(x) = i*x + j
i = 94.557 + 396.417; j = 2424.11

plot '<sed "1,3d" get_all_neighbors' using 2:1 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'
