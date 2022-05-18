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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.lang3.tuple.Pair;

public class du {

    public void finder(List<String> fileNames, boolean humanReadable, boolean total, boolean si, Outer outer) throws IOException {
        BigDecimal inSum = BigDecimal.valueOf(0); //переменная в случае наличия флага total
        List<String> units; //хранение единиц измерения
        short ratio; //система счисления

        //проверка наличия файлов, поданных на вход
        ArrayList<String> errorList = new ArrayList<>();
        for (String i : fileNames) {
            File checker = new File(i);
            if (!checker.exists()) {
                errorList.add(i);
            }
        }

        if (!errorList.isEmpty()) {
            StringBuilder str = new StringBuilder();
            for (String i : errorList) str.append(i).append(", ");
            str.delete(str.length() - 2, str.length());
            throw new FileNotFoundException("There is no such file/files -> " + str);
        }



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
                inSum = inSum.add(fileSize); //для суммы
            } else {
                Pair<BigDecimal, Integer> formattedResult = formatSize(fileSize, humanReadable, ratio);
                fileSize = formattedResult.getLeft();
                cntUnit = formattedResult.getRight();
                String s = fileSize.toString() + units.get(cntUnit);
                if (fileNames.indexOf(i) != fileNames.size() - 1) s += System.lineSeparator();
                outer.out(s);
            }
        }

        if (total) {
            int cntUnit;
            //форматируем полученную сумму
            Pair<BigDecimal, Integer> formattedResult = formatSize(inSum, humanReadable, ratio);
            inSum = formattedResult.getLeft();
            cntUnit = formattedResult.getRight();
            outer.out(inSum.toString() + units.get(cntUnit));
        }
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
            while (size.divide(BigDecimal.valueOf(ratio)).compareTo(BigDecimal.valueOf(1)) >= 0) {
                size = size.divide(BigDecimal.valueOf(ratio));
                cntUnit++;
            }
            //если флага -h нет, только переводим в килобайты (килобиты)
        } else {
            size = size.divide(BigDecimal.valueOf(ratio));
            cntUnit = 1;
        }
        // Повторяем отображение размеров как в системе windows в свойствах файлов
        // Если размер до 10 - 2 знака после запятой.
        // До 100 - 1 знак. Во всех остальных случаях (или если это байты) после запятой нет знаков
        if (cntUnit == 0) size = size.setScale(0, RoundingMode.DOWN);
        else if (size.compareTo(BigDecimal.valueOf(10)) < 0) size = size.setScale(2, RoundingMode.DOWN);
        else if (size.compareTo(BigDecimal.valueOf(100)) < 0) size = size.setScale(1, RoundingMode.DOWN);
        else size = size.setScale(0, RoundingMode.DOWN);
        return Pair.of(size, cntUnit);
    }


}
