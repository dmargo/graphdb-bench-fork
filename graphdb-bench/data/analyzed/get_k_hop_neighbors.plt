set terminal postscript color enhanced

set datafile separator ';'
set yrange [0:2e+08]



set output 'get_k_hop_neighbors_dedup.eps'
set xlabel 'Deduplicated Vertex Count (vertices)'
set ylabel 'Time (nanoseconds)'
set xrange [0:10000]

f(x) = a*x + b
a = 1358.439 + 1433.284; b = 100264

g(x) = i*x + j
i = 993.837 + 3178.681; j = -250433 

plot '<sed "1,7d" bdb-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'bdb', \
     '<sed "1,7d" dex-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'dex', \
     '<sed "1,7d" dup-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'dup', \
     '<sed "1,7d" neo-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'neo', \
     '<sed "1,7d" rdf-512M-barabasi1M/get_k_hop_neighbors' using 3:2 title 'rdf'



set output 'get_k_hop_neighbors_getcount.eps'
set xlabel 'GetNeighborsOp Count'
set ylabel 'Time'
set xrange [0:4000]

plot '<sed "1,7d" bdb-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'bdb', \
     '<sed "1,7d" dex-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'dex', \
     '<sed "1,7d" dup-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'dup', \
     '<sed "1,7d" neo-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'neo', \
     '<sed "1,7d" rdf-512M-barabasi1M/get_k_hop_neighbors' using 5:2 title 'rdf'



set output 'get_k_hop_neighbors_nodecount.eps'
set xlabel 'Vertex Count'
set ylabel 'Time'
set xrange [0:10000]

plot '<sed "1,7d" bdb-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'bdb', \
     '<sed "1,7d" dex-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'dex', \
     '<sed "1,7d" dup-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'dup', \
     '<sed "1,7d" neo-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'neo', \
     '<sed "1,7d" rdf-512M-barabasi1M/get_k_hop_neighbors' using 6:2 title 'rdf'

