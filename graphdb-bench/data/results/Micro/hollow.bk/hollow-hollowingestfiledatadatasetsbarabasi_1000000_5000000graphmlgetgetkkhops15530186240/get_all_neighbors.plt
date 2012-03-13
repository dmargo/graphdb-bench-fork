set terminal postscript color enhanced
set output 'get_all_neighbors.eps'

set datafile separator ';'
set xlabel 'Neighborhood Count'
set ylabel 'Time'

f(x) = a*x + b
a = 215; b = 2670;
fit f(x) '<sed "1,3d" get_all_neighbors' using 2:1 via a,b
plot '<sed "1,3d" get_all_neighbors' using 2:1, f(x)
