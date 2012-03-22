set terminal postscript color enhanced

set datafile separator ';'
set yrange [0:1.5e+08]



set output 'get_k_hop_neighbors_khops.eps'
set xlabel 'Input K-Hops'
set ylabel 'Time (nanoseconds)'
set xrange [1:5]

f(x) = a*x**b + c
a = 94.557 + 396.417; b = 5; c = 488.0
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 1:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 1:2 title 'Real', f(x) title 'Fitted'



set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Deduplicated Vertex Count (vertices)'
set ylabel 'Time (nanoseconds)'
set xrange [0:10000]

f(x) = a*x + b
a = 94.557 + 396.417; b = 488.0
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 3:2 via a,b

g(x) = i*x + j
i = 94.557 + 396.417; j = 488.0

plot '<sed "1,7d" get_k_hop_neighbors' using 3:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_realhops.eps'
set xlabel 'Actual K-Hops'
set ylabel 'Time (nanoseconds)'
set xrange [1:5]

f(x) = a*x**b + c
a = 94.557 + 396.417; b = 5; c = 488.0
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 4:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 4:2 title 'Real', f(x) title 'Fitted'



set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set ylabel 'Time'
set xrange [0:4000]

f(x) = a*x + b
a = 4124.719; b = 488.0
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b

g(x) = i*x + j
i = 4124.719; j = 488.0

plot '<sed "1,7d" get_k_hop_neighbors' using 5:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count'
set ylabel 'Time'
set xrange [0:10000]

f(x) = a*x + b
a = 94.557 + 396.417; b = 488.0
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 6:2 via a,b

g(x) = i*x + j
i = 94.557 + 396.417; j = 488.0

plot '<sed "1,7d" get_k_hop_neighbors' using 6:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'

