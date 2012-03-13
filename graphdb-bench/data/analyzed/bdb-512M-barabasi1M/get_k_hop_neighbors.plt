set terminal postscript color enhanced

set datafile separator ';'
set yrange [0:2e+08]



set output 'get_k_hop_neighbors_khops.eps'
set xlabel 'Input K-Hops'
set ylabel 'Time (nanoseconds)'
set xrange [1:5]

f(x) = a*x**b + c
a               = 57864.4; b               = 4.70097; c               = 14956
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 1:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 1:2 title 'Real', f(x) title 'Fitted'



set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Deduplicated Vertex Count (vertices)'
set ylabel 'Time (nanoseconds)'
set xrange [0:4000]

f(x) = a*x + b
a               = 53011.8; b               = 19002.8  
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 3:2 via a,b

g(x) = i*x + j
i = 26230.435 + 31636.655; j = 19002.8  

plot '<sed "1,7d" get_k_hop_neighbors' using 3:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_realhops.eps'
set xlabel 'Actual K-Hops'
set ylabel 'Time (nanoseconds)'
set xrange [1:5]

f(x) = a*x**b + c
a               = 57848.8; b               = 5.30028; c               = 14955.9
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 4:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 4:2 title 'Real', f(x) title 'Fitted'



set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set ylabel 'Time (nanoseconds)'
set xrange [0:1500]

f(x) = a*x + b
a               = 139706; b               = -396968 
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b

g(x) = i*x + j
i = 279321.213; j = -396968 

plot '<sed "1,7d" get_k_hop_neighbors' using 5:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count (vertices)'
set ylabel 'Time (nanoseconds)'
set xrange [0:4000]

f(x) = a*x + b
a               = 52955.2; b               = 20910.5
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 6:2 via a,b

g(x) = i*x + j
i = 26230.435 + 31636.655; j               = 20910.5

plot '<sed "1,7d" get_k_hop_neighbors' using 6:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'

