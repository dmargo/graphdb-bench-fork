set terminal postscript color enhanced
set output 'get_all_neighbors.eps'

set datafile separator ';'
set xlabel 'Neighborhood Count'
set ylabel 'Time'

set xrange [0:60]
set yrange [0:3e+06]


f(x) = a*x + b
a = 54418; b = 20000
fit f(x) '<sed "1,3d" get_all_neighbors' using 2:1 via a,b
plot '<sed "1,3d" get_all_neighbors' using 2:1, f(x)
