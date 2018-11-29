package edu.trial.itcompany;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InformationRecipientImpl implements InformationRecipient {
    private StringBuilder sb;
    private List<String> buf;
    private List<String> end;
    private Pattern pn;

    InformationRecipientImpl(File f) {
        sb = new StringBuilder();
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                this.sb.append(sc.nextLine().trim());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getListOfEmployeeOnProject(String project) {
        end = new ArrayList<>();
        end.add("no employees without projects");
        buf = new LinkedList<>();
        String pattern = "\\{\"personName\": \"([^\"]*)\"," +
                "\"department\": \"[a-zA-Z0-9]*\",\"projects\":" +
                " \\[((\\{\"projectName\": \"[^\"]*\",\"customer\": \"[^\"]*\"," +
                "\"manager\": \"[^\"]*\"},?)+)]},?";
        pn = Pattern.compile(pattern);
        Matcher m = pn.matcher(sb);

        while (m.find()) {
            buf.add(m.group(1) + m.group(2));
        }
        if (buf.size() == 0) {
            return end;
        }
        for (int i = 0; i < buf.size(); i++) {
            if (buf.get(i).contains("\"projectName\": \"" + project + '"')) {
                buf.set(i, buf.get(i).substring(0, buf.get(i).indexOf('{')));
            }
        }
        buf.removeIf(s -> s.contains("{"));

        return buf;
    }

    @Override
    public List<String> getListOfManagersForEmployee(String empName) {
        end = new ArrayList<>();
        end.add("Employee has no managers");
        buf = new ArrayList<>();
        String pattern = "\\{\"personName\": \"(" + empName + ")\"," +
                "\"department\": \"[a-zA-Z0-9]*\",\"projects\": \\[((\\{\"projectName\": \"[^\"]*\",\"customer\": \"[^\"]*\"," +
                "\"manager\": \"[^\"]*\"},?)+)[]},]?";
        pn = Pattern.compile(pattern);
        try {
            Matcher m = pn.matcher(sb);
            while (m.find()) {
                buf.add(m.group(2));
            }
            String[] f = buf.get(0).split("},");
            buf.clear();
            pattern = "(.*?)\"manager\": \"([^\"]*)\"";
            pn = Pattern.compile(pattern);
            for (String aF : f) {
                m = pn.matcher(aF);
                if (m.find() && !empName.equals(m.group(2))) {
                    buf.add(m.group(2));
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return end;
        }
        if (buf.size() == 0)
            return end;
        else
            return buf;
    }

    @Override
    public List<String> getListOfNotBusyEmployees() {
        buf = new ArrayList<>();
        String pattern = "\\{(\"personName\": \"([a-zA-Z0-9]*)\")," +
                "(\"department\": \"[a-zA-Z0-9]*\"),(\"projects\": \\[])";
        pn = Pattern.compile(pattern);
        Matcher m = pn.matcher(sb);

        while (m.find()) {
            buf.add(m.group(2));
        }

        return buf;
    }

    @Override
    public List<String> getListOfProjectsForCustomer(String customerName) {
        end = new ArrayList<>();
        end.add("No projects for customer");
        buf = new ArrayList<>();
        String pattern = "(\"projectName\": \"([^\"]*)\",\"customer\": \"" + customerName + "\",)+";
        pn = Pattern.compile(pattern);

        Matcher m = pn.matcher(sb);
        while (m.find())
            buf.add(m.group(2));
        if (buf.size() == 0)
            return end;
        return buf;
    }
}

