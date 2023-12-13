package org.example

import org.apache.commons.math3.distribution.NormalDistribution

class MyNormalDistribution {
    static def generateNormalDistribution(Integer n, Double mean, Double standardDeviation) {
        def result = new double[n]
        def normalDistribution = new NormalDistribution(mean, standardDeviation)
        (0..<n).each { i ->
            result[i] = Math.round(normalDistribution.sample())
        }
        result
    }
}
