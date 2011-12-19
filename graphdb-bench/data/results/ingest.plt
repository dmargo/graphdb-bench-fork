set terminal postscript color enhanced
set output 'ingest.eps'

set datafile separator ';'
set style data histograms
set style fill border -1 solid 1
set style histogram cluster gap 2
set xtics rotate by -90
set yrange [0:]

plot 'benchmark_micro_summary.csv' every ::5::5 using 2:xtic(1) title 'bdb', \
'' every ::5::5 using 6 title 'dex', \
'' every ::5::5 using 10 title 'dup', \
'' every ::5::5 using 14 title 'neo', \
'' every ::5::5 using 18 title 'rdf', \
'' every ::5::5 using 22 title 'sql'
