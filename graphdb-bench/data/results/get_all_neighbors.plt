set terminal postscript color enhanced
set output 'get_all_neighbors.eps'

set datafile separator ';'
set xlabel 'Neighborhood Count'
set ylabel 'Time'
plot '<sed "1,3d" get_all_neighbors | sort -t ";" -k 2 -n' using 2:1
