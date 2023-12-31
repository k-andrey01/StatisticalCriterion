import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest

class Wilcoxon {
    static void main(String[] args) {
        // Генерация результатов для двух групп бегунов (время в секундах на бег 100 м)
        def rubber = [10.2, 10.4, 10.3, 10.5, 10.1, 10.6, 10.8, 10.7, 10.2, 10.9]
        def ground = [10.5, 11.2, 10.8, 11.0, 10.6, 10.9, 11.3, 10.7, 10.5, 11.1]

        // Применение критерия Вилкоксона
        WilcoxonSignedRankTest wilcoxonTest = new WilcoxonSignedRankTest()
        // параметр exact (который в данном случае установлен в false) указывает, должен ли критерий Вилкоксона
        // использовать точное или приближенное (асимптотическое) вычисление p-значения
        double pValue = wilcoxonTest.wilcoxonSignedRankTest(ground as double[], rubber as double[], false)

        // Вывод результатов
        println "p-значение: ${pValue}"

        // Определение статистической значимости
        def alpha = 0.05
        if (pValue < alpha) {
            println "Отвергаем нулевую гипотезу. Существует статистическое различие во времени бега между группами."
        } else {
            println "Принимаем нулевую гипотезу. Нет статистического различия во времени бега между группами."
        }
    }
}
