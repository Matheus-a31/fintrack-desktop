package fintrack.utils;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Formatador {
    private static final NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private static final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatarMoeda(double valor) {
        return formatoMoeda.format(valor);
    }

    public static String formatarData(LocalDate data) {
        return data.format(formatoData);
    }
}