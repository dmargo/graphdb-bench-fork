avg_traversal = 13443.923 + 17396.858
min_get_neighbors = 6042.0

set xrange [0:60]
set yrange [0:3e+06]

set xlabel 'Neighborhood Size (vertices)'
set ylabel 'Time (nanoseconds)'



set datafile separator ';'
set output 'get_all_neighbors.eps'
set terminal postscript color enhanced

#Fitted least-squares data.
a = avg_traversal
b = min_get_neighbors
f(x) = a*x + b
fit f(x) '<sed "1,3d" get_all_neighbors' using 2:1 via a,b

#Predicted by get_vertex + get_neighbor.
g(x) = avg_traversal*x + b

plot '<sed "1,3d" get_all_neighbors' using 2:1 title 'Real', \
                                           f(x) title 'Fitted', \
                                           g(x) title 'Predicted'
