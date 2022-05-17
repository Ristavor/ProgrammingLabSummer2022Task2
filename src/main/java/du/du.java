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

import org.apache.commons.lang3.tuple.Pair;


public class du {

    public short filesSize(List<String> fileNames, boolean format, boolean sum, boolean inBits) {
        ArrayList<Pair<BigDecimal, String>> result = new ArrayList<>();
        List<String> units;
        short Ratio;

        if (inBits) {
            units = List.of("Bit", "KBit", "MBit", "GBit");
            Ratio = 1000;
        } else {
            units = List.of("B", "KB", "MB", "GB");
            Ratio = 1024;
        }


        for (String i : fileNames) {
            File file = new File(i);
            int cntUnit;
            BigDecimal size;

            if (file.isFile()) size = BigDecimal.valueOf(file.length());
            else size = BigDecimal.valueOf(getDirSize(file));

            if (inBits) size = size.multiply(BigDecimal.valueOf(8));

            if (sum) {
                cntUnit = 0;
            } else {
                Pair<BigDecimal, Integer> miniResult = formatSize(size, format, Ratio);
                size = miniResult.getLeft();
                cntUnit = miniResult.getRight();
            }
            result.add(Pair.of(size, units.get(cntUnit)));
        }

        if (sum) {
            BigDecimal size = BigDecimal.valueOf(0);
            for (Pair<BigDecimal, String> i : result) {
                size = size.add(i.getLeft());
            }
            result.clear();
            int cntUnit;
            Pair<BigDecimal, Integer> miniResult = formatSize(size, format, Ratio);
            size = miniResult.getLeft();
            cntUnit = miniResult.getRight();
            result.add(Pair.of(size, units.get(cntUnit)));
        }

        System.out.print(result);
        return 0;
    }

    static private long getDirSize(File dir) {
        long length = 0;
        File[] files = dir.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += getDirSize(file);
            }
        }
        return length;
    }

    static private Pair<BigDecimal, Integer> formatSize(BigDecimal size, boolean format, short Ratio) {
        int cntUnit;
        if (format) {
            cntUnit = 0;
            while (Double.parseDouble(String.valueOf(size.divide(BigDecimal.valueOf(Ratio)))) >= 1) {
                cntUnit++;
                size = size.divide(BigDecimal.valueOf(Ratio));
            }
        } else {
            size = size.divide(BigDecimal.valueOf(Ratio));
            cntUnit = 1;
        }
        return Pair.of(size.setScale(2, RoundingMode.DOWN), cntUnit);
    }


}
