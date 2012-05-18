set yrange [0:2e+08]
set ylabel 'Time (nanoseconds)'



set datafile separator ';'
set terminal postscript color enhanced



set output 'get_shortest_path_dedup_1K.eps'
set xlabel 'Path Length'
set xrange [0:10]

plot '<sed "1,3d" bdb-512M-barabasi1K/get_shortest_path' using 2:1 title 'bdb', \
     '<sed "1,3d" dex-512M-barabasi1K/get_shortest_path' using 2:1 title 'dex', \
     '<sed "1,3d" dup-512M-barabasi1K/get_shortest_path' using 2:1 title 'dup', \
     '<sed "1,3d" hollow-512M-barabasi1K/get_shortest_path' using 2:1 title 'hollow', \
     '<sed "1,3d" neo-512M-barabasi1K/get_shortest_path' using 2:1 title 'neo', \
     '<sed "1,3d" rdf-512M-barabasi1K/get_shortest_path' using 2:1 title 'rdf'



set output 'get_shortest_path_getcount_1K.eps'
set xlabel 'GetNeighborsOp Count'
set xrange [0:1000]

plot '<sed "1,3d" bdb-512M-barabasi1K/get_shortest_path' using 3:1 title 'bdb', \
     '<sed "1,3d" dex-512M-barabasi1K/get_shortest_path' using 3:1 title 'dex', \
     '<sed "1,3d" dup-512M-barabasi1K/get_shortest_path' using 3:1 title 'dup', \
     '<sed "1,3d" hollow-512M-barabasi1K/get_shortest_path' using 3:1 title 'hollow', \
     '<sed "1,3d" neo-512M-barabasi1K/get_shortest_path' using 3:1 title 'neo', \
     '<sed "1,3d" rdf-512M-barabasi1K/get_shortest_path' using 3:1 title 'rdf'



set output 'get_shortest_path_nodecount_1K.eps'
set xlabel 'Vertex Count (vertices)'
set xrange [0:5000]

plot '<sed "1,3d" bdb-512M-barabasi1K/get_shortest_path' using 4:1 title 'bdb', \
     '<sed "1,3d" dex-512M-barabasi1K/get_shortest_path' using 4:1 title 'dex', \
     '<sed "1,3d" dup-512M-barabasi1K/get_shortest_path' using 4:1 title 'dup', \
     '<sed "1,3d" hollow-512M-barabasi1K/get_shortest_path' using 4:1 title 'hollow', \
     '<sed "1,3d" neo-512M-barabasi1K/get_shortest_path' using 4:1 title 'neo', \
     '<sed "1,3d" rdf-512M-barabasi1K/get_shortest_path' using 4:1 title 'rdf'
