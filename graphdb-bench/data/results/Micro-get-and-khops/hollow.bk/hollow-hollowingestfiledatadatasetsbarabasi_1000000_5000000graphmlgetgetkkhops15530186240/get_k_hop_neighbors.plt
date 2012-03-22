set terminal postscript color enhanced

set datafile separator ';'

set output 'get_k_hop_neighbors_khops.eps'
set xlabel 'K Hops'
set ylabel 'Time'

#f(x) = a*x + b
#a = 3000; b = 10000
#fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b
plot '<sed "1,7d" get_k_hop_neighbors' using 1:2

set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Dedup Vertex Count'
set ylabel 'Time'

#f(x) = a*x + b
#a = 3000; b = 10000
#fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b
plot '<sed "1,7d" get_k_hop_neighbors' using 3:2

set output 'get_k_hop_neighbors_realhops.eps'
set xlabel 'Real Hop Count'
set ylabel 'Time'

#f(x) = a*x + b
#a = 3000; b = 10000
#fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b
plot '<sed "1,7d" get_k_hop_neighbors' using 4:2

set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set ylabel 'Time'

f(x) = a*x + b
a = 3000; b = 10000
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b
plot '<sed "1,7d" get_k_hop_neighbors' using 5:2, f(x)

set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count'
set ylabel 'Time'

f(x) = a*x + b
a = 400; b = 10000
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 6:2 via a,b
plot '<sed "1,7d" get_k_hop_neighbors' using 6:2, f(x)


