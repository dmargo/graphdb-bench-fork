set xrange [0:100]
set yrange [0:3e+06]

set xlabel 'Neighborhood Size (vertices)'
set ylabel 'Time (nanoseconds)'



set datafile separator ';'
set output 'get_all_neighbors_1K.eps'
set terminal postscript color enhanced

plot '<sed "1,3d" bdb-512M-barabasi1K/get_all_neighbors' using 2:1 title 'bdb', \
     '<sed "1,3d" dex-512M-barabasi1K/get_all_neighbors' using 2:1 title 'dex', \
     '<sed "1,3d" dup-512M-barabasi1K/get_all_neighbors' using 2:1 title 'dup', \
     '<sed "1,3d" hollow-512M-barabasi1K/get_all_neighbors' using 2:1 title 'hollow', \
     '<sed "1,3d" neo-512M-barabasi1K/get_all_neighbors' using 2:1 title 'neo', \
     '<sed "1,3d" rdf-512M-barabasi1K/get_all_neighbors' using 2:1 title 'rdf'
