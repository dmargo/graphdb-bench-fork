set terminal postscript color enhanced

set datafile separator ';'
set yrange [0:1.5e+08]



set output 'get_k_hop_neighbors_khops.eps'
set xlabel 'Input K-Hops'
set ylabel 'Time (nanoseconds)'
set xrange [1:5]

f(x) = a*x**b + c
a = 3543.964 + 8958.070; b = 5; c = 10647
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 1:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 1:2 title 'Real', f(x) title 'Fitted'



set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Deduplicated Vertex Count (vertices)'
set ylabel 'Time (nanoseconds)'
set xrange [0:10000]

f(x) = a*x + b
a = 3543.964 + 8958.070; b = 10647
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 3:2 via a,b

g(x) = i*x + j
i = 3543.964 + 8958.070; j = 19363.9

plot '<sed "1,7d" get_k_hop_neighbors' using 3:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_realhops.eps'
set xlabel 'Actual Hop-Count'
set ylabel 'Time (nanoseconds)
set xrange [1:5]

f(x) = a*x**b + c
a = 44809.362; b = 5; c = 10647
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 4:2 via a,b,c

plot '<sed "1,7d" get_k_hop_neighbors' using 4:2 title 'Real', f(x) title 'Predicted'



set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set ylabel 'Time (nanoseconds)'
set xrange [0:4000]

f(x) = a*x + b
a = 3543.964 + 8958.070; b = 10647
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 5:2 via a,b

g(x) = i*x + j
i = 3543.964 + 8958.070; j = -184720

plot '<sed "1,7d" get_k_hop_neighbors' using 5:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'



set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count (vertices)'
set ylabel 'Time (nanoseconds)'
set xrange [0:10000]

f(x) = a*x + b
a = 3543.964 + 8958.070; b = 10647
fit f(x) '<sed "1,7d" get_k_hop_neighbors' using 6:2 via a,b

g(x) = i*x + j
i = 3543.964 + 8958.070; j = 20523.9

plot '<sed "1,7d" get_k_hop_neighbors' using 6:2 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'


