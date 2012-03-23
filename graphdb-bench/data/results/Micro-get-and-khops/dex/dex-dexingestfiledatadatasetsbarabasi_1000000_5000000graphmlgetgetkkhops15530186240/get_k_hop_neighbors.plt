avg_traversal = 1358.439 + 1433.284
avg_get_neighbors = 72464.035
min_khop_neighbors = 10737
hop_plot_constant = 5

set yrange [0:1.5e+08]
set ylabel 'Time (nanoseconds)'



set datafile separator ';'
set terminal postscript color enhanced



set output 'get_k_hop_neighbors_khops.eps'
set xlabel 'Input K-Hops'
set xrange [1:5]

a = avg_traversal; b = min_khop_neighbors; c = hop_plot_constant
f(x) = a*x**c + b
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 1:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 1:2 title 'Real', \
                                             f(x) title 'Fitted'



set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Deduplicated Vertex Count (vertices)'
set xrange [0:10000]

a = avg_traversal; b = min_khop_neighbors
f(x) = a*x + b
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 3:2 via a,b

g(x) = avg_traversal*x + b

plot '<sed "1,7d" get_k_hop_neighbors' using 3:2 title 'Real', \
                                             f(x) title 'Fitted', \
                                             g(x) title 'Predicted'



set output 'get_k_hop_neighbors_realhops.eps'
set xlabel 'Actual K-Hops'
set xrange [1:5]

a = avg_traversal; b = min_khop_neighbors; c = hop_plot_constant
f(x) = a*x**c + b
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 4:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 4:2 title 'Real', \
                                             f(x) title 'Fitted'



set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set xrange [0:4000]

a = avg_get_neighbors; b = min_khop_neighbors
f(x) = a*x + b
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b

g(x) = avg_get_neighbors*x + b

plot '<sed "1,7d" get_k_hop_neighbors' using 5:2 title 'Real', \
                                             f(x) title 'Fitted', \
                                             g(x) title 'Predicted'



set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count (vertices)'
set xrange [0:10000]

a = avg_traversal; b = min_khop_neighbors
f(x) = a*x + b
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 6:2 via a,b

g(x) = avg_traversal*x + b

plot '<sed "1,7d" get_k_hop_neighbors' using 6:2 title 'Real', \
                                             f(x) title 'Fitted', \
                                             g(x) title 'Predicted'

