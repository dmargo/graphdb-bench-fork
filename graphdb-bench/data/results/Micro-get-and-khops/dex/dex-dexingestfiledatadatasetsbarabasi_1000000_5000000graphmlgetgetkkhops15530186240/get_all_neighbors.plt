set terminal postscript color enhanced
set output 'get_all_neighbors.eps'

set datafile separator ';'
set xlabel 'Neighborhood Size (vertices)'
set xrange [0:100]
set ylabel 'Time (nanoseconds)'
set yrange [0:400000]

f(x) = a*x + b
i = 1358.439 + 1433.284; b = 11819.0
fit f(x) '<sed "1,3d" get_all_neighbors' using 2:1 via a,b

g(x) = i*x + j
i = 1358.439 + 1433.284; j = 11819.0
fit g(x) '<sed "1,3d" get_all_neighbors' using 2:1 via j

plot '<sed "1,3d" get_all_neighbors' using 2:1 title 'Real', g(x) title 'Predicted'
