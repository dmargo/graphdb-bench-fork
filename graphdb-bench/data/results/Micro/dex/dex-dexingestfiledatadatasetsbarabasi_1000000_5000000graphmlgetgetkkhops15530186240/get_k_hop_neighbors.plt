set terminal postscript color enhanced

set datafile separator ';'
set yrange [0:1.5e+08]

set output 'get_k_hop_neighbors_khops.eps'
set xlabel 'K Hops'
set ylabel 'Time'
set xrange [1:5]

f(x) = a*x**b + c
a = 100; b = 5; c = 100
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 1:2 via a,b,c
plot '<sed "1,7d" get_k_hop_neighbors' using 1:2, f(x)

set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Dedup Vertex Count'
set ylabel 'Time'
set xrange [0:10000]

f(x) = a*x + b
a = 5000; b = 100
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 3:2 via a,b
plot '<sed "1,7d" get_k_hop_neighbors' using 3:2, f(x)

set output 'get_k_hop_neighbors_realhops.eps'
set xlabel 'Real Hop Count'
set ylabel 'Time'
set xrange [1:5]

f(x) = a*x**b + c
a = 100; b = 5; c = 100
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 4:2 via a,b,c
plot '<sed "1,7d" get_k_hop_neighbors' using 4:2, f(x)

set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set ylabel 'Time'
set xrange [0:4000]

f(x) = a*x + b
a = 10000; b = 100
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b
plot '<sed "1,7d" get_k_hop_neighbors' using 5:2, f(x)

set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count'
set ylabel 'Time'
set xrange [0:10000]

f(x) = a*x + b
a = 5000; b = 100
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 6:2 via a,b
plot '<sed "1,7d" get_k_hop_neighbors' using 6:2, f(x)


