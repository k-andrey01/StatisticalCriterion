package org.example.FisherCriterion

import org.apache.commons.math3.distribution.FDistribution
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.example.MyNormalDistribution

class Fisher {
    static void main(String[] args) {
        // Имеем две группы данных для проверки равенства дисперсий
        def group1 = MyNormalDistribution.generateNormalDistribution(15, 20 as double, 3 as double)
        def group2 = MyNormalDistribution.generateNormalDistribution(15, 20 as double, 3 as double)

        // Задаем уровень значимости (alpha)
        def alpha = 0.05

        // 1. Вычисление выборочных дисперсий для каждой группы и вычисление поправленных дисперсий
        def varianceGroup1 = new DescriptiveStatistics(group1).getVariance()
        def varianceGroup2 = new DescriptiveStatistics(group2).getVariance()

        def correctedVarianceGroup1 = (group1.size() * varianceGroup1) / (group1.size() - 1)
        def correctedVarianceGroup2 = (group1.size() * varianceGroup2) / (group1.size() - 1)


        def variances = [correctedVarianceGroup1, correctedVarianceGroup2]

        println "Исправленные дисперсии: $variances"

        // 2. Вычисление статистики для критерия Фишера
        def fStat = variances.max() / variances.min()

        // 3. Определение степеней свободы
        def df1 = group1.size() - 1
        def df2 = group2.size() - 1

        // 4. Определение критической области (двусторонний тест)
        def fDistribution = new FDistribution(df1, df2)
        def criticalValueTwoSidedLower = fDistribution.inverseCumulativeProbability(alpha / 2)
        def criticalValueTwoSidedUpper = fDistribution.inverseCumulativeProbability(1 - alpha / 2)

        // Вывод результатов
        println "F-статистика: ${fStat}"
        println "Критическая область (двусторонний тест): (${criticalValueTwoSidedLower}, ${criticalValueTwoSidedUpper})"

        // 5. Определение статистической значимости
        if (fStat < criticalValueTwoSidedLower || fStat > criticalValueTwoSidedUpper) {
            println "Отвергаем нулевую гипотезу. Существует статистическое различие в дисперсиях двух групп."
        } else {
            println "Принимаем нулевую гипотезу. Нет статистического различия в дисперсиях двух групп."
        }
    }
}
