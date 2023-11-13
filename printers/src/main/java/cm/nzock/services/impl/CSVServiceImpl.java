package cm.nzock.services.impl;

import cm.nzock.services.CSVService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.ParticipantModel;
import cm.printers.config.ImprimanteModel;
import cm.printers.usages.PrintUsageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CSVServiceImpl implements CSVService {

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM yyyy hh:mm:ss");
    private static final Logger LOG = LoggerFactory.getLogger(CSVServiceImpl.class);

    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private ModelService modelService;

    @Override
    public <T> List<T> parseCsv(InputStream is, final String[] headers, T type) throws UnsupportedEncodingException {
      assert Objects.nonNull(headers): "Headers can't be null";

        final BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        final List<T> datas = new ArrayList<>();

        reader.lines().skip(1L)
                .forEach(line ->{
                    try {
                        populateObject(headers, type, line)
                                .ifPresent(instance ->datas.add(instance));
                    } catch (Exception e) {
                        LOG.error(String.format("Unable to process line %s with error [%s]", line, e.getMessage()));
                        //e.printStackTrace();
                    }

                });
        return datas ;
    }

    @Override
    public <T> List<T> parceCsv(List<String> data, String[] headers, T type) {
        final List<T> datas = new ArrayList<>();
        data.stream()
                .skip(1)
                .forEach(line -> {
                    try {
                        populateObject(headers, type, line)
                                .ifPresent(instance ->datas.add(instance));
                    } catch (Exception e) {
                        LOG.error(String.format("Unable to process line %s with error [%s]", line, e.getMessage()));
                        //e.printStackTrace();
                    }
                });
        return datas;
    }

    private <T> Optional<T> populateObject(String[] headers, T type, String line) throws Exception {
        final String[] cols = line.split(";");

        if (cols.length != headers.length) {
            return  Optional.empty();
        }

        final T instance = (T) type.getClass().newInstance();

        for(int i = 0; i < headers.length; i++ ) {
            populateObjectField(headers, type, cols, instance, i);
        }
        return Optional.ofNullable(instance);
    }

    private <T> void populateObjectField(String[] headers, T type, String[] cols, T instance, int i) throws NoSuchFieldException, IllegalAccessException {
        final Field field = type.getClass().getDeclaredField(headers[i]);
        field.setAccessible(Boolean.TRUE);
        field.set(instance, cols[i]);
    }

    @Override
    public void processCsv(final ImprimanteModel printer, List<String> data) {
        data.stream().skip(1).forEach(line -> {
            processLine(printer, FORMATTER, line);
        });
    }

   /** @Override
    public void processCsv(ImportBean data) throws IOException {
       final Imprimante printer = printerService.getPrinterById(data.getPrinter());
        if (CollectionUtils.isEmpty(data.getFiles())) {
            return;
        }

        for (MultipartFile mpf : data.getFiles()) {
            processMultipartFile(printer, FORMATTER, mpf);
        }
    }
**/


    /**
     *
     * @param printer
     * @param formatter
     * @param mpf
     * @throws IOException
     */
    private void processMultipartFile(ImprimanteModel printer, SimpleDateFormat formatter, MultipartFile mpf) throws IOException {
        if (Objects.isNull(mpf)) {
            return;
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(mpf.getInputStream(), "UTF-8"));

        reader.lines().skip(1).forEach(line -> {
            processLine(printer, formatter, line);
        });
    }

    private void processLine(ImprimanteModel printer, SimpleDateFormat formatter, String line) {
        String[] cols = line.split(",");

        if (cols.length == 10) {
            final PrintUsageModel usage = new PrintUsageModel();
            usage.setPrinter(printer);
            usage.setPrinterId(printer.getPK());
            usage.setJobId(cols[0]);
            usage.setResult(clean(cols[1]));
            try {
                usage.setStartTime(formatter.parse(clean(cols[2])));
                usage.setEndTime(formatter.parse(clean(cols[3])));
            } catch (ParseException e) {
                ;
            }
            usage.setDepartmentId(clean(cols[4]));
            usage.setUserName(clean(cols[5]));
            //usage.setUser(usage);
            usage.setOriginalPages(Integer.valueOf(clean(cols[6])));
            usage.setOutputPages(Integer.valueOf(clean(cols[7])));
            usage.setSheetCopies(clean(cols[8]));
            usage.setEndCode(clean(cols[9]));
            //System.out.println("Usage before save : "+usage+" output : "+clean(cols[9])+" value ::: "+Integer.valueOf(clean(cols[9])));
            try{
                modelService.save(usage);
            }catch (Exception ex) {
                LOG.error(String.format("Erreur pendant la sauvegarde de l'item : %s", usage));
            }
        }

        if (cols.length == 14) {

            final PrintUsageModel usage = new PrintUsageModel();
            usage.setPrinter(printer);
            usage.setPrinterId(printer.getPK());
            usage.setJobId(cols[0]);
            usage.setResult(clean(cols[1]));
            try {
                usage.setStartTime(formatter.parse(clean(cols[2])));
                usage.setEndTime(formatter.parse(clean(cols[3])));
            } catch (ParseException e) {
               ;
            }
            usage.setDepartmentId(clean(cols[4]));
            usage.setJobType(clean(cols[5]));
            usage.setFileName(clean(cols[6]));
            usage.setUserName(clean(cols[7]));
            usage.setOriginalPages(Integer.valueOf(clean(cols[8])));
            usage.setOutputPages(Integer.valueOf(clean(cols[9])));
            usage.setSheetCopies(clean(cols[10]));
            usage.setEndCode(clean(cols[11]));
            usage.setJobType(clean(cols[12]));
            usage.setDetails(clean(cols[13]));

            //System.out.println("Usage before save : "+usage+" output : "+clean(cols[9])+" value ::: "+Integer.valueOf(clean(cols[9])));
            try{
                setUser(usage);
                modelService.save(usage);
            }catch (Exception ex) {
                LOG.error(String.format("Erreur pendant la sauvegarde de l'item : %s", usage));
            }
        }

    }


    private void setUser(PrintUsageModel usage) throws ModelServiceException {
        ParticipantModel user = (ParticipantModel) flexibleSearchService.find(usage.getUserName(), "code", ParticipantModel._TYPECODE)
                .orElse(null);
        if (Objects.isNull(user)) {
            user = new ParticipantModel();
            user.setCode(usage.getUserName());
            modelService.save(usage);
        }
        usage.setUser(user);
        if( Objects.isNull(user.getPK())) {
            flexibleSearchService.find(usage.getUserName(), "code", ParticipantModel._TYPECODE)
                    .ifPresent(us -> usage.setUser((ParticipantModel) us));
        }

    }

    private String clean(final String value) {
        return  value. replaceAll("^\"|\"$", "");
    }
}
