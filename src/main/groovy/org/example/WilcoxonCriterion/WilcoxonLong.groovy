import org.apache.commons.math3.distribution.NormalDistribution
import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest
import org.apache.commons.math3.util.Precision

class WilcoxonLong {
    static void main(String[] args) {
        double[] ground = [10.5, 11.2, 10.8, 11.0, 10.6, 10.9, 11.3, 10.7, 10.5, 11.1]
        double[] rubber = [10.2, 10.4, 10.3, 10.5, 10.1, 10.6, 10.8, 10.7, 10.2, 10.9]

        def result = wilcoxonSignedRankTest(rubber, ground)

        println(result)
    }

    static def wilcoxonSignedRankTest(double[] data1, double[] data2) {
        // Слияние массивов и сортировка
        def mergedArray = ((data1 as ArrayList) + (data2 as ArrayList)).sort()

        // Индексы для элементов из первого массива, учитывая среднее арифметическое для одинаковых элементов
        def indicesArray1 = data1.collect { element ->
            def indices = mergedArray.indices.findAll { mergedArray[it] == element }
            indices.sum() / indices.size() + 1
        }

        // Сумма индексов
        def sumOfIndices = indicesArray1.sum()

        println "Сумма рангов для первого массива: $sumOfIndices"

        def p = getPValue(sumOfIndices as int, data1.size() as int, data2.size() as int)

        println "p-значение: $p"

        // Делаем вывод о статистической значимости различий
        double alpha = 0.05
        if (p < alpha) {
            return "Существуют статистически значимые различия в результатах двух групп бегунов"
        } else {
            return "Существуют незначимые различия в результатах двух групп бегунов"
        }
    }

    // Для нахождения p воспользуемся асимптотическим распределением статистики Вилкоксона.
    // Эта статистика асимптотически нормальна со средним и дисперсией (определяются по введенным формулам)
    // По итогу значение для p берем по значению ф-ии стандартного нормального распределения Ф((W-Mu)/Qu)
    static def getPValue(int rangeSum, int n1, int n2){
        NormalDistribution normalDistribution = new NormalDistribution(0.0, 1.0)

        def Mu = ((n1 * (n1 + n2 + 1)) / 2) + 0.5
        def Qu = n1 * n2 * (n1 + n2 + 1) / 12

        // Задаем точку, в которой мы хотим получить значение функции нормального распределения
        double x = (rangeSum - Mu) / Math.sqrt(Qu)

        // Получаем искомое значение
        double cumulativeProbability = normalDistribution.cumulativeProbability(x)

        return cumulativeProbability
    }
}
