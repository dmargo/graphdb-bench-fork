set yrange [0:2e+08]
set ylabel 'Time (nanoseconds)'



set datafile separator ';'
set terminal postscript color enhanced



set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Deduplicated Vertex Count (vertices)'
set xrange [0:10000]

plot '<sed "1,7d" bdb-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'bdb', \
     '<sed "1,7d" dex-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'dex', \
     '<sed "1,7d" dup-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'dup', \
     '<sed "1,7d" hollow-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'hollow', \
     '<sed "1,7d" neo-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'neo', \
     '<sed "1,7d" rdf-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'rdf'



set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set xrange [0:4000]

plot '<sed "1,7d" bdb-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'bdb', \
     '<sed "1,7d" dex-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'dex', \
     '<sed "1,7d" dup-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'dup', \
     '<sed "1,7d" hollow-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'hollow', \
     '<sed "1,7d" neo-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'neo', \
     '<sed "1,7d" rdf-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'rdf'



set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count (vertices)'
set xrange [0:10000]

plot '<sed "1,7d" bdb-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'bdb', \
     '<sed "1,7d" dex-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'dex', \
     '<sed "1,7d" dup-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'dup', \
     '<sed "1,7d" hollow-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'hollow', \
     '<sed "1,7d" neo-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'neo', \
     '<sed "1,7d" rdf-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'rdf'

