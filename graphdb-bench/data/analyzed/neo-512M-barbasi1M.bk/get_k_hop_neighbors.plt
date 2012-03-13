set terminal postscript color enhanced

set datafile separator ';'
set yrange [0:1.5e+08]



set output 'get_k_hop_neighbors_khops.eps'
set xlabel 'Input K-Hops'
set ylabel 'Time (nanoseconds)'
set xrange [1:5]

f(x) = a*x**b + c
a               = 4115.14; b               = 6.30528; c               = 14999.9 
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 1:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 1:2 title 'Real'



set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Deduplicated Vertex Count (vertices)'
set ylabel 'Time (nanoseconds)'
set xrange [0:10000]

f(x) = a*x + b
a               = 15233.3; b               = -250420
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 3:2 via a,b

g(x) = i*x + j
i = 993.837 + 3178.681; j = 15000

plot '<sed "1,7d" get_k_hop_neighbors' using 3:2 title 'Real', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_realhops.eps'
set xlabel 'Actual K-Hops'
set ylabel 'Time (nanoseconds)'
set xrange [1:5]

f(x) = a*x**b + c
a               = 4169.09; b               = 6.92141; c               = 15000 
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 4:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 4:2 title 'Real'



set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set ylabel 'Time'
set xrange [0:4000]

f(x) = a*x + b
a               = 43493.7; b               = -481785
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b

g(x) = i*x + j
i = 30666.204; j = 15000

plot '<sed "1,7d" get_k_hop_neighbors' using 5:2 title 'Real', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count'
set ylabel 'Time'
set xrange [0:10000]

f(x) = a*x + b
a               = 15185.6; b               = -247467
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 6:2 via a,b

g(x) = i*x + j
i = 993.837 + 3178.681; j = 15000

plot '<sed "1,7d" get_k_hop_neighbors' using 6:2 title 'Real', g(x) title 'Predicted'

