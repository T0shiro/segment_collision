set grid lw 3
set logscale xy 2
set xlabel "Number of segments"
set ylabel "FPS"
set terminal svg
plot for [file in files] file using 1:2 title file[:strstrt(file, '.txt')-1] with lines smooth unique