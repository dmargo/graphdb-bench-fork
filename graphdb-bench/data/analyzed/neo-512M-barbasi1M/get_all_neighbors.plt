set terminal postscript color enhanced
set output 'get_all_neighbors.eps'

set datafile separator ';'
set xlabel 'Neighborhood Size (vertices)'
set ylabel 'Time (nanoseconds)'

set xrange [0:100]
set yrange [0:400000]

# Fitted by least squares to data.
f(x) = a*x + b
a = 993.837 + 3178.681; b = 18878.0
fit f(x) '<sed "1,3d" get_all_neighbors' using 2:1 via a,b

# Predicted by get_vertex + get_edge per neighbor.
g(x) = i*x + j
i = 993.837 + 3178.681; j = 20727

plot '<sed "1,3d" get_all_neighbors' using 2:1 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'
