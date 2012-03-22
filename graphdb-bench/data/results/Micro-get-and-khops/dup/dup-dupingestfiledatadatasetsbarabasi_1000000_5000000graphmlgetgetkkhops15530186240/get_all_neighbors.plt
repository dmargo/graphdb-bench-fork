set terminal postscript color enhanced
set output 'get_all_neighbors.eps'

set datafile separator ';'
set xlabel 'Neighborhood Size (vertices)'
set ylabel 'Time (nanoseconds)'

set xrange [0:100]
set yrange [0:400000]

f(x) = a*x + b
a = 3543.964 + 8958.070; b = 11349
fit f(x) '<sed "1,3d" get_all_neighbors' using 2:1 via a,b

g(x) = i*x + j
i = 3543.964 + 8958.070; j = 19992.1

plot '<sed "1,3d" get_all_neighbors' using 2:1 title 'Real', f(x) title 'Fitted', g(x) title 'Predicted'
