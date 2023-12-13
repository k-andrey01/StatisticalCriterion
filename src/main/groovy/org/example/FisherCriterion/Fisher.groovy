package org.example.FisherCriterion

import org.apache.commons.math3.distribution.FDistribution
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.example.MyNormalDistribution

class Fisher {
    static void main(String[] args) {
        int n1 = 30
        double mean1 = 20
        double standardDeviation1 = 3
        int n2 = 30
        double mean2 = 22
        double standardDeviation2 = 3

        // Имеем две группы данных для проверки равенства дисперсий
        def group1 = MyNormalDistribution.generateNormalDistribution(n1, mean1 as double, standardDeviation1 as double)
        def group2 = MyNormalDistribution.generateNormalDistribution(n2, mean2 as double, standardDeviation2 as double)

        // Задаем уровень значимости (alpha)
        def alpha = 0.05

        // Вычисление выборочных дисперсий для каждой группы и вычисление поправленных дисперсий
        def varianceGroup1 = new DescriptiveStatistics(group1).getVariance()
        def varianceGroup2 = new DescriptiveStatistics(group2).getVariance()

        println varianceGroup1+"====="+varianceGroup2

        def variances = [varianceGroup1, varianceGroup2]

        println "Исправленные дисперсии: $variances"

        // Вычисление статистики для критерия Фишера
        def fStat = variances.max() / variances.min()

        // Определение степеней свободы
        def df1 = group1.size() - 1
        def df2 = group2.size() - 1

        // Определение критической области (двусторонний тест)
        def fDistribution = new FDistribution(df1, df2)
        def criticalValueTwoSidedLower = fDistribution.inverseCumulativeProbability(alpha / 2)
        def criticalValueTwoSidedUpper = fDistribution.inverseCumulativeProbability(1 - alpha / 2)

        // Вывод результатов
        println "F-статистика: ${fStat}"
        println "Критическая область (двусторонний тест): (${criticalValueTwoSidedLower}, ${criticalValueTwoSidedUpper})"

        // Определение статистической значимости
        if (fStat < criticalValueTwoSidedLower || fStat > criticalValueTwoSidedUpper) {
            println "Отвергаем нулевую гипотезу. Существует статистическое различие в дисперсиях двух групп."
        } else {
            println "Принимаем нулевую гипотезу. Нет статистического различия в дисперсиях двух групп."
        }
    }
}
