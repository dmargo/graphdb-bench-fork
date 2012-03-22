set terminal postscript color enhanced

set datafile separator ';'
set yrange [0:2e+08]



set output 'get_k_hop_neighbors_khops.eps'
set xlabel 'Actual K-Hops'
set ylabel 'Time (nanoseconds)'
set xrange [1:5]

f(x) = a*x**b + c
a = 18882 + 86691; b = 5; c = 7696
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 1:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 1:2 title 'Real'



set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Deduplicated Vertex Count (vertices)'
set ylabel 'Time (nanoseconds)'
set xrange [0:4000]

f(x) = a*x + b
a = 18882 + 86691; b = 7696
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 3:2 via a,b

g(x) = i*x + j
i = 18882 + 86691; j = -1.10377e+06

plot '<sed "1,7d" get_k_hop_neighbors' using 3:2 title 'Real', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_realhops.eps'
set xlabel 'Actual Hop-Count'
set ylabel 'Time (nanoseconds)
set xrange [1:5]

f(x) = a*x**b + c
a = 18882 + 86691; b = 5; c = 7696
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 4:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 4:2 title 'Real'



set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set ylabel 'Time (nanoseconds)'
set xrange [0:1500]

f(x) = a*x + b
a = 129536.652; b = 7696
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b

g(x) = i*x + j
i = 129536.652; j = -1.69567e+06

plot '<sed "1,7d" get_k_hop_neighbors' using 5:2 title 'Real', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count'
set ylabel 'Time'
set xrange [0:4000]

f(x) = a*x + b
a = 18882 + 86691; b = 7696
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 6:2 via a,b

g(x) = i*x + j
i = 18882 + 86691; j = -1.10174e+06

plot '<sed "1,7d" get_k_hop_neighbors' using 6:2 title 'Real', g(x) title 'Predicted'

