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

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.tuple.Pair;

public class Du {
    private static final List<String> units = List.of("B", "KB", "MB", "GB");
    private static boolean humanReadable;
    private static short ratio;
    private static Outer outer;


    public void find(List<String> fileNames, boolean h, boolean total, boolean si, Outer out) throws IOException {
        humanReadable = h;
        outer = out;
        //система счисления
        if (si) {
            ratio = 1000;
        } else {
            ratio = 1024;
        }
        double inSum = 0.0; //переменная в случае наличия флага total (суммы вместо по-отдельности)

        //проверка наличия файлов, поданных на вход, и возврат ошибки в случае отсутствия каких-либо файлов
        StringBuilder errorStr = new StringBuilder();
        for (String i : fileNames) {
            File checker = new File(i);
            if (!checker.exists()) {
                errorStr.append(i).append(", ");
            }
        }
        if (!errorStr.isEmpty()) {
            errorStr.delete(errorStr.length() - 2, errorStr.length());
            throw new FileNotFoundException("There is no such file/files -> " + errorStr);
        }


        for (String i : fileNames) {
            File file = new File(i);
            double fileSize;

            //проверка на то, файл это или директория
            BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            if (basicFileAttributes.isRegularFile()) fileSize = (double) basicFileAttributes.size();
            else fileSize = (double )getDirSize(file);

            if (total) {
                inSum += fileSize; //для суммы
            } else {
                formatSize(fileSize, fileNames.indexOf(i), fileNames.size() - 1);
            }
        }

        if (total) {
            //форматируем полученную сумму
            formatSize(inSum, 0, 0);
        }
    }

    //метод для поиска размера директорий
    private static long getDirSize(File dir) {
        long len = 0;
        File[] files = dir.listFiles();

        if (files == null) return 0;
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
    private static void formatSize(Double size, int index, int indexOfLast) {
        int cntUnit;
        //если стоит флаг -h, уменьшаем максимально возможно и ведём подсчёт для единиц измерения
        if (humanReadable) {
            cntUnit = 0;
            while (size / ratio >= 1) {
                size /= ratio;
                cntUnit++;
            }
            //если флага -h нет, только переводим в килобайты (килобиты)
        } else {
            size /= ratio;
            cntUnit = 1;
        }
        // Повторяем отображение размеров как в системе windows в свойствах файлов
        // Если размер до 10 - 2 знака после запятой.
        // До 100 - 1 знак. Во всех остальных случаях (или если это байты) после запятой нет знаков
        String scale;
        if (cntUnit == 0) scale = "0";
        else if (size < 10) scale = "0.00";
        else if (size < 100) scale = "0.0";
        else scale = "0";

        //вывод
        DecimalFormat formatter = new DecimalFormat(scale, new DecimalFormatSymbols(Locale.ENGLISH));
        formatter.setRoundingMode(RoundingMode.DOWN);
        String s = formatter.format(size);
        if (humanReadable) s += units.get(cntUnit);
        if (index != indexOfLast) s += System.lineSeparator();
        outer.out(s);
    }


}
