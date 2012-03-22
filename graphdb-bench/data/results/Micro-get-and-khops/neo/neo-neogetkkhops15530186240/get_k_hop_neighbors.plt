set terminal postscript color enhanced

set datafile separator ';'
set yrange [0:1.5e+08]



set output 'get_k_hop_neighbors_khops.eps'
set xlabel 'Input K-Hops'
set ylabel 'Time (nanoseconds)'
set xrange [1:5]

f(x) = a*x**b + c
a               = 108.378;b               = 6.32935;c               = 100.011 
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 1:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 1:2 title 'Real', f(x) title 'Fitted'



set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Deduplicated Vertex Count (vertices)'
set ylabel 'Time (nanoseconds)'
set xrange [0:10000]

f(x) = a*x + b
a = 6859.55; b = 145354
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 3:2 via a,b

g(x) = i*x + j
i = 993.837 + 3178.681; j = 145354

plot '<sed "1,7d" get_k_hop_neighbors' using 3:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_realhops.eps'
set xlabel 'Actual K-Hops'
set ylabel 'Time (nanoseconds)'
set xrange [1:5]

f(x) = a*x**b + c
a               = 21539;b               = 3.6346;c               = 363.615 
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 4:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 4:2 title 'Real', f(x) title 'Fitted'



set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set ylabel 'Time'
set xrange [0:4000]

f(x) = a*x + b
a = 19624.1; b = 63034.5
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b

g(x) = i*x + j
i = 30666.204; j = 63034.5

plot '<sed "1,7d" get_k_hop_neighbors' using 5:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count'
set ylabel 'Time'
set xrange [0:10000]

f(x) = a*x + b
a = 6841.6; b = 146270
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 6:2 via a,b

g(x) = i*x + j
i = 993.837 + 3178.681; j = 146270

plot '<sed "1,7d" get_k_hop_neighbors' using 6:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'

