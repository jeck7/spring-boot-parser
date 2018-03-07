package ef.parser;

import ef.parser.models.BlockedIps;
import ef.parser.models.RowsLog;
import ef.parser.repository.BlockedIpsRepository;
import ef.parser.repository.RowsLogRepository;
import ef.parser.utils.ParserFileReader;
import ef.parser.utils.ParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ParserApplication implements ApplicationRunner {

    @Autowired
    private RowsLogRepository rowsLogRepository;

    @Autowired
    private BlockedIpsRepository blockedIpsRepository;

    public static void main(String[] args) {
        SpringApplication.run(ParserApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        if (args.getSourceArgs().length != 0) {
            File logFile = null;
            String startDateString = null;
            String duration = null;
            int threshold = 0;
            if (args.containsOption("accesslog") && args.containsOption("startDate") && args.containsOption("duration") && args.containsOption("threshold")) {
                logFile = new File(args.getOptionValues("accesslog").get(0));
                startDateString = args.getOptionValues("startDate").get(0);
                duration = args.getOptionValues("duration").get(0);
                threshold = Integer.parseInt(args.getOptionValues("threshold").get(0));
            } else {
                System.out.println("Please, provide all parameters: --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100");
                return;
            }
            Date startDate;
            try {
                startDate = ParserUtil.INPUT_DATE_FORMAT.parse(startDateString);
            } catch (ParseException e) {
                System.out.println("Invalid startDate, accepted format is: " + ParserUtil.INPUT_DATE);
                return;
            }

            rowsLogRepository.deleteAllInBatch();
            blockedIpsRepository.deleteAllInBatch();

            if (logFile != null) {
                List<RowsLog> rowsLogs = null;
                try {
                    rowsLogs = ParserFileReader.readCsvFile(logFile.getAbsolutePath());
                } catch (Exception e) {
                    System.out.println("Please, provide a valid log file --accesslog=/path/to/file");
                    return;
                }
                System.out.println("Loaded " + rowsLogs.size() + " records from " + logFile.getName());
                System.out.println("Starting bulk save of " + rowsLogs.size() + " log records. It will take a while...");
                rowsLogRepository.saveAll(rowsLogs);
            }

            int hours = duration.equals("daily") ? 24 : 1;
            Date endDate = ParserUtil.addHours(startDate, hours);

            List<Object[]> results = rowsLogRepository.calculateBlockedIpsByPeriod(startDate, endDate);

            List<BlockedIps> blockedIPAddresses = new ArrayList<BlockedIps>();

            for (Object[] result : results) {

                String ip = (String) result[0];
                int count = ((Number) result[1]).intValue();

                if (count > threshold) {

                    System.out.println("Blocked IP: " + ip + " number of requests : " + count + " , for duration \"" + duration + "\" and threshold " + threshold);
                    BlockedIps blockedIPAddress = new BlockedIps();
                    blockedIPAddress.setIp(ip);
                    blockedIPAddress.setComment("Blocked IP , " + count + " requests were received in the selected period " + hours + " hours." + " Threshold " + threshold +
                            " has been exceeded.");
                    blockedIPAddresses.add(blockedIPAddress);
                }
            }
            blockedIpsRepository.saveAll(blockedIPAddresses);
            System.out.println("Operation Completed!");

        }


    }
}
