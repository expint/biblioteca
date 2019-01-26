package it.online.biblioteca.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Genera, se non presente, il file .csv del catalogo tramite i dati del file "elencoLibri.csv" nella forma<br><br>
 * 1;AAAA 000-01;disponibile <br><br>
 * dove il primo elemento è l'id libro a cui si riferisce, <br>
 * il secondo la classificazione del libro (le prime 4 lettere del cognome dell' autore - le ultime 3 cifre codice ISBN - ultime 2 cifre arbitrarie, in questo caso 01),<br> 
 * l'ultimo elemento è lo stato della copia, in questo caso "disponibile". <br>
 * Il numero di copie presenti è regolato dalla funzione Math.random().
 * 
 */
public class GeneraCatalogoCsvFromElencoLibri {

	public static void main(String[] args) throws IOException {
		String str = "IDLIBRO;CODICE;STATO\r\n";
		List<String[]> records = leggiRecordsFromFile();
		scriviCatalogoCsv(str, records);

	}
	
	public static List<String[]> leggiRecordsFromFile() {
		String csvFile = Costanti.RESOURCES_PATH+"elencoLibri.csv";
		String recordStr = "";
    String cvsSplitBy = ";";
    List<String[]> records = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        while ((recordStr = br.readLine()) != null) {
            String[] record = recordStr.split(cvsSplitBy);
            records.add(record);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }  
    return records;
	}
	
	public static void scriviCatalogoCsv(String str, List<String[]> records) throws IOException {
    BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(Costanti.RESOURCES_PATH+"catalogo.csv"));
			writer.write(str);
	    for (int i = 1; i < records.size(); i++) {
	    	for (int j = 0; j < Math.ceil(Math.random()*10); j++) {
	    		String[] auth = records.get(i)[1].split(" ");
	    		String codAuth = auth[auth.length-1].length() >= 4 ? auth[auth.length-1].substring(0, 4) : auth[auth.length-1].substring(0, auth[auth.length-1].length());
	    		codAuth = codAuth.toUpperCase();
	    		writer.append(i+";"+codAuth+" "+records.get(i)[4].substring(10, 13)+"-01;disponibile\r\n");									
				}
			}
	    writer.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
}

}
