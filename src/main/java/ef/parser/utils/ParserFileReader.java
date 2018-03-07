package ef.parser.utils;


import ef.parser.models.RowsLog;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class ParserFileReader {

    private static final char PIPE_DELIMITER = '|';

    public static List<RowsLog> readCsvFile(String filePath) throws Exception {

        CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter(PIPE_DELIMITER);

        List<RowsLog> logRows = new ArrayList<RowsLog>();
        try (
                FileReader fileReader = new FileReader(filePath);
                CSVParser csvFileParser = new CSVParser(fileReader, csvFormat)) {

            List<CSVRecord> csvRecords = csvFileParser.getRecords();
            for (int i = 0; i < csvRecords.size(); i++) {
                CSVRecord record = csvRecords.get(i);
                RowsLog rowsLog = new RowsLog();
                rowsLog.setDateCreated(ParserUtil.LOG_DATE_FORMAT.parse(record.get(0)));
                rowsLog.setIp(record.get(1));
                rowsLog.setRequest(record.get(2));
                rowsLog.setStatus(Integer.parseInt(record.get(3)));
                rowsLog.setUserAgent(record.get(4));
                logRows.add(rowsLog);

            }
        } catch (Exception e) {
            System.out.println("Error in Parser FileReader ! : " + e.getMessage());
            throw new Exception();
        }
        return logRows;
    }

}
