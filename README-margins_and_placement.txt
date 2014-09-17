Legends (when on the right):

|---|---|---|---|---|...|---|
A   B   C   D   E   F   G   H

A - right edge of plot
B - left edge of legend 1
C - left edge of legend 1 key
D - right edge of legend 1 key
E - right edge of legend 1
F - left edge of legend 2
etc.
G - right edge of legend X
H - right edge of screen

A-B = 0.5 lines + 0.5*legend.margin
B-C = 2mt
C-D = legend.key.width (or legend.key.size)
D-E = Required width plus some margin...
E-F = 0.5 lines
G-H = 0.5 lines + 0.5*legend.margin + plot.margin[2]

Y-axis:

|---|---|---|---|---|
A   B   C   D   E   F

A - left edge of screen
B - left edge of y-axis title
C - left edge of y-axis ticks.text
D - right edge of y-axis ticks.text
E - left edge of y-axis ticks
F - left edge of plot

A-B = plot.margin[4] + 0.5 lines -- only way to make 0 is labs(y=NULL)
B-C = Required width
C-D = Required width
D-E = axis.ticks.margin
E-F = axis.ticks.length