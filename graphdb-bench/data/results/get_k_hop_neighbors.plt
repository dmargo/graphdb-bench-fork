set terminal postscript color enhanced

set datafile separator ';'

set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count'
set ylabel 'Time'
plot '<sed "1,3d" get_k_hop_neighbors | sort -t ";" -k 2 -n' using 2:1

set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set ylabel 'Time'
plot '<sed "1,3d" get_k_hop_neighbors | sort -t ";" -k 3 -n' using 3:1
