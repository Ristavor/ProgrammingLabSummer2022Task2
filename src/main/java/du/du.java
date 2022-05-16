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

public class du {
    public ArrayList<BigDecimal> size(List<String> fileNames, boolean format, boolean sum, boolean si) {
        ArrayList<BigDecimal> list = new ArrayList<>();
        BigDecimal x = BigDecimal.valueOf(1024);
        if (si) x = BigDecimal.valueOf(1000);
        for (String i : fileNames) {
            File file = new File(i);
            BigDecimal s = BigDecimal.valueOf(file.length());
            if (format) {
                while (Integer.parseInt(String.valueOf(s.divide(x))) > 1) {
                    s = s.divide(x).setScale(3, RoundingMode.HALF_EVEN);
                }
            }
            list.add(s);
        }
        BigDecimal h = new BigDecimal(0);
        if (sum) {
            for (BigDecimal i : list) {
                h = h.add(i);
            }
            list.clear();
            list.add(h);
        }
        System.out.print(list);
        return list;

    }
}
