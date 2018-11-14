import numpy


def getCoeffFor(sortName):
    file = open(sortName + ".txt", "r")
    lines = file.readlines()
    file.close()
    x = []
    y = []
    for line in lines:
        tab = line.split()
        if int(tab[0]) >= 256:
            x.append(int(tab[0]))
            y.append(float(tab[1]))
    covariance = numpy.cov(numpy.log(x), numpy.log(y))
    return covariance[1][0] / covariance[0][0]


for graph in ["execTimeNaive", "execTimeQuadTree", "FPSNaive", "FPSQuadTree"]:
    print(graph + " : " + str(getCoeffFor(graph)))
