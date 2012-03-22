set terminal postscript color enhanced
set output 'get_all_neighbors.eps'

set datafile separator ';'
set xlabel 'Neighborhood Count'
set xrange [0:100]
set ylabel 'Time'
set yrange [0:400000]

f(x) = a*x + b
a = 3000; b = 25000
fit f(x) '<sed "1,3d" get_all_neighbors' using 2:1 via a,b
plot '<sed "1,3d" get_all_neighbors' using 2:1, f(x)
