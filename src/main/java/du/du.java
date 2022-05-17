//  Вариант 7 -- du
//        Вывод на консоль размера указанного файла(-ов) или каталога(-ов) из файловой
//        системы:
//        ● fileN задаёт имя файла, размер которого нужно вывести. Таких параметров
//        может быть несколько.
//        ● Флаг -h указывает, что размер следует выдавать в человеко-читаемом формате:
//        в зависимости от размера файла результат выдается в байтах, килобайтах,
//        мегабайтах или гигабайтах и дополняется соответствующей единицей
//        измерения (B, KB, MB, GB). Если данный флаг не указан, размер должен
//        печататься в килобайтах и без единицы измерения.
//        ● Флаг -c означает, что для всех переданных на вход файлов нужно вывести
//        суммарный размер.
//        ● Флаг --si означает, что для всех используемых единиц измерения следует брать
//        основание 1000, а не 1024.
//        Command line: du [-h] [-c] [--si] file1 file2 file3 …
//        На вход может быть передано любое количество имён файлов. Все флаги совместимы
//        друг с другом. Размер каталога равен сумме размеров всех входящих в него файлов и
//        каталогов. Если на вход передаётся имя, не соответствующее существующему файлу,
//        следует вместо результата выдать ошибку. Возвращаемое значение программы в
//        случае успеха равняется 0, в случае ошибки 1.
//        Кроме самой программы, следует написать автоматические тесты к ней.

package du;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.lang3.tuple.Pair;


public class du {

    public void filesSize(List<String> fileNames, boolean humanReadable, boolean total, boolean si){
        ArrayList<Pair<BigDecimal, String>> sizes = new ArrayList<>(); //храним данные на вывод
        BigDecimal inSum = BigDecimal.valueOf(0); //переменная в случае наличия флага total
        List<String> units; //хранение единиц измерения
        short ratio; //система счисления


        //настраиваем единицы измерения в зависимости от флагов si и h
        if (si) {
            units = List.of("Bit", "KBit", "MBit", "GBit");
            ratio = 1000;
        } else {
            units = List.of("B", "KB", "MB", "GB");
            ratio = 1024;
        }
        if (!humanReadable) {
            units = List.of("", "", "" , "");
        }

        for (String i : fileNames) {
            File file = new File(i);

            int cntUnit; //счётчик для выбора отображения единиц измерения
            BigDecimal fileSize;


            //проверка на то, файл это или директория
            if (file.isFile()) fileSize = BigDecimal.valueOf(file.length());
            else fileSize = BigDecimal.valueOf(getDirSize(file));

            if (si) fileSize = fileSize.multiply(BigDecimal.valueOf(8)); //в формате битовом нет байтов

            if (total) {
                inSum = inSum.add(fileSize); //для суммы не нужно заполнять sizes
            } else {
                //заполняем sizes
                Pair<BigDecimal, Integer> formattedResult = formatSize(fileSize, humanReadable, ratio);
                fileSize = formattedResult.getLeft();
                cntUnit = formattedResult.getRight();
                sizes.add(Pair.of(fileSize, units.get(cntUnit)));
            }
        }

        if (total) {
            int cntUnit;
            //форматируем полученную сумму и заносим её в sizes
            Pair<BigDecimal, Integer> formattedResult = formatSize(inSum, humanReadable, ratio);
            inSum = formattedResult.getLeft();
            cntUnit = formattedResult.getRight();
            sizes.add(Pair.of(inSum, units.get(cntUnit)));
        }

        //выводим данные на консоль
        StringBuilder str = new StringBuilder();
        for (Pair<BigDecimal, String> i : sizes) {
            str.append(String.format("%s%s\n", i.getLeft().toString(), i.getRight()));
        }
        str.delete(str.length() - 1, str.length());
        System.out.print(str);
    }

    //метод для поиска размера директорий
    static private long getDirSize(File dir) {
        long len = 0;
        File[] files = dir.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                len += file.length();
            } else {
                len += getDirSize(file);
            }
        }
        return len;
    }

    //метод для форматирования размера файла в зависимости от флагов (+единиц измерения)
    static private Pair<BigDecimal, Integer> formatSize(BigDecimal size, boolean format, short ratio) {
        int cntUnit;
        //если стоит флаг -h, уменьшаем максимально возможно и ведём подсчёт для единиц измерения
        if (format) {
            cntUnit = 0;
            while (size.divide(BigDecimal.valueOf(ratio), RoundingMode.HALF_EVEN).compareTo(BigDecimal.valueOf(1)) >= 0) {
                size = size.divide(BigDecimal.valueOf(ratio), RoundingMode.HALF_EVEN);
                cntUnit++;
            }
            //если флага -h нет, только переводим в килобайты (килобиты)
        } else {
            size = size.divide(BigDecimal.valueOf(ratio), RoundingMode.HALF_EVEN);
            cntUnit = 1;
        }
        // Повторяем отображение размеров как в системе windows в свойствах файлов
        // Если размер до 10 - 2 знака после запятой.
        // До 100 - 1 знак. Во всех остальных случаях после запятой нет знаков
        if (size.compareTo(BigDecimal.valueOf(10)) < 0) size = size.setScale(2, RoundingMode.DOWN);
        else if (size.compareTo(BigDecimal.valueOf(100)) < 0) size = size.setScale(1, RoundingMode.DOWN);
        else size = size.setScale(0, RoundingMode.DOWN);
        return Pair.of(size, cntUnit);
    }


}
