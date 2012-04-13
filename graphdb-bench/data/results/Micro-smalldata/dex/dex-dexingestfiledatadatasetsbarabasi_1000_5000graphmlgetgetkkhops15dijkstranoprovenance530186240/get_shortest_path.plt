avg_traversal = 680.151 + 873.402
avg_get_neighbors = 21818.421
min_shortest_path = 10529

set yrange [0:1e+08]
set ylabel 'Time (nanoseconds)'



set datafile separator ';'
set terminal postscript color enhanced



set output 'get_shortest_path_pathlen.eps'
set xlabel 'Path Length'
set xrange [1:10]

plot '<sed "1,3d" get_shortest_path' using 2:1 title 'Real'



set output 'get_shortest_path_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set xrange [0:1000]

a = avg_get_neighbors; b = min_shortest_path
f(x) = a*x + b
fit f(x) '<sed "1,3d" get_shortest_path' using 3:1 via a,b

g(x) = avg_get_neighbors*x + b

plot '<sed "1,3d" get_shortest_path' using 3:1 title 'Real', \
                                          f(x) title 'Fitted', \
                                          g(x) title 'Predicted'



set output 'get_shortest_path_nodecount.eps'
set xlabel 'Vertex Count (vertices)'
set xrange [0:5000]

a = avg_traversal; b = min_shortest_path
f(x) = a*x + b
fit f(x) '<sed "1,3d" get_shortest_path' using 4:1 via a,b

g(x) = avg_traversal*x + b

plot '<sed "1,3d" get_shortest_path' using 4:1 title 'Real', \
                                          f(x) title 'Fitted', \
                                          g(x) title 'Predicted'

